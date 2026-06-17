package view.PaineisAdmin;

import dao.ApostaDAO;
import dao.CampeonatoDAO;
import dao.ParticipanteDAO;
import dao.PartidaDAO;
import model.CampeonatoModel.Campeonato;
import model.CampeonatoModel.Partida;
import model.PessoaModel.Aposta;
import model.PessoaModel.Participante;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ResultadosPanel extends JPanel {

    private JComboBox<String> comboCampeonato;
    private JComboBox<String> comboPartida;
    private JTextField campoGolsMandante, campoGolsVisitante;

    private final CampeonatoDAO campeonatoDAO = new CampeonatoDAO();
    private final PartidaDAO partidaDAO = new PartidaDAO();
    private final ApostaDAO apostaDAO = new ApostaDAO();
    private final ParticipanteDAO participanteDAO = new ParticipanteDAO();

    private List<Campeonato> campeonatosCarregados;
    private List<Partida> partidasCarregadas;

    public ResultadosPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Registrar Resultado (Admin)"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        comboCampeonato    = new JComboBox<>();
        comboPartida       = new JComboBox<>();
        campoGolsMandante  = new JTextField(4);
        campoGolsVisitante = new JTextField(4);

        JButton btnCarregarCamp     = new JButton("Carregar campeonatos");
        JButton btnCarregarPartidas = new JButton("Carregar partidas");
        JButton btnRegistrar        = new JButton("Registrar resultado");

        g.gridx=0;g.gridy=0; form.add(new JLabel("Campeonato:"), g);
        g.gridx=1;           form.add(comboCampeonato, g);
        g.gridx=2;           form.add(btnCarregarCamp, g);

        g.gridx=0;g.gridy=1; form.add(new JLabel("Partida:"), g);
        g.gridx=1;           form.add(comboPartida, g);
        g.gridx=2;           form.add(btnCarregarPartidas, g);

        g.gridx=0;g.gridy=2; form.add(new JLabel("Gols mandante:"), g);
        g.gridx=1;g.gridwidth=2; form.add(campoGolsMandante, g);
        g.gridwidth=1;

        g.gridx=0;g.gridy=3; form.add(new JLabel("Gols visitante:"), g);
        g.gridx=1;g.gridwidth=2; form.add(campoGolsVisitante, g);
        g.gridwidth=1;

        g.gridx=0;g.gridy=4;g.gridwidth=3; form.add(btnRegistrar, g);

        add(form, BorderLayout.NORTH);

        btnCarregarCamp.addActionListener(e -> carregarCampeonatos());
        btnCarregarPartidas.addActionListener(e -> carregarPartidas());
        btnRegistrar.addActionListener(e -> registrarResultado());
    }

    private void carregarCampeonatos() {
        comboCampeonato.removeAllItems();
        campeonatosCarregados = campeonatoDAO.listarTodos();
        for (Campeonato c : campeonatosCarregados) {
            comboCampeonato.addItem(c.getNome());
        }
        if (comboCampeonato.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "Nenhum campeonato cadastrado.");
        }
    }

    private void carregarPartidas() {
        comboPartida.removeAllItems();
        if (comboCampeonato.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um campeonato primeiro."); return;
        }
        int idxCamp = comboCampeonato.getSelectedIndex();
        Campeonato camp = campeonatosCarregados.get(idxCamp);

        partidasCarregadas = new ArrayList<>();
        List<Partida> todasPartidas = partidaDAO.listarTodas();

        int encontradas = 0;
        for (Partida p : todasPartidas) {
            if (p.getCampeonato().equals(camp) && !p.isEncerrada()) {
                comboPartida.addItem(partidasCarregadas.size() + " — " + p.toString());
                partidasCarregadas.add(p);
                encontradas++;
            }
        }
        if (encontradas == 0) {
            JOptionPane.showMessageDialog(this,
                    "Nenhuma partida pendente neste campeonato.");
        }
    }

    private void registrarResultado() {
        if (comboPartida.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma partida."); return;
        }
        try {
            int idx = Integer.parseInt(
                    comboPartida.getSelectedItem().toString().split(" — ")[0]);
            int gM = Integer.parseInt(campoGolsMandante.getText().trim());
            int gV = Integer.parseInt(campoGolsVisitante.getText().trim());

            Partida partida = partidasCarregadas.get(idx);
            partida.registrarResultado(gM, gV);
            partidaDAO.atualizarResultado(partida);

            List<Aposta> apostasDaPartida = apostaDAO.listarPorPartida(partida.getId());
            for (Aposta a : apostasDaPartida) {
                int pontos = a.calcularPontos(partida);
                Participante participante = a.getParticipante();
                participante.setPontuacao(pontos);
                participanteDAO.atualizarPontuacao(participante);
            }

            JOptionPane.showMessageDialog(this,
                    "Resultado registrado e pontos calculados!");
            campoGolsMandante.setText("");
            campoGolsVisitante.setText("");
            carregarPartidas();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite números válidos.");
        }
    }
}
