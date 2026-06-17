package view.PainelUser;

import dao.ApostaDAO;
import dao.CampeonatoDAO;
import dao.PartidaDAO;
import model.CampeonatoModel.*;
import model.PessoaModel.*;
import view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ApostasPanel extends JPanel {

    private JComboBox<String> comboCampeonato;
    private JComboBox<String> comboPartida;
    private JTextField campoGolsMandante, campoGolsVisitante;
    private DefaultListModel<String> listaModel;

    private final CampeonatoDAO campeonatoDAO = new CampeonatoDAO();
    private final PartidaDAO partidaDAO = new PartidaDAO();
    private final ApostaDAO apostaDAO = new ApostaDAO();

    private List<Campeonato> campeonatosCarregados;

    private List<Partida> partidasCarregadas;

    public ApostasPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Registrar Aposta"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        comboCampeonato    = new JComboBox<>();
        comboPartida       = new JComboBox<>();
        campoGolsMandante  = new JTextField(4);
        campoGolsVisitante = new JTextField(4);

        JButton btnCarregarCamp    = new JButton("Carregar campeonatos");
        JButton btnCarregarPartidas= new JButton("Carregar partidas");
        JButton btnApostar         = new JButton("Registrar aposta");

        g.gridx=0;g.gridy=0; form.add(new JLabel("Campeonato:"), g);
        g.gridx=1;           form.add(comboCampeonato, g);
        g.gridx=2;           form.add(btnCarregarCamp, g);

        g.gridx=0;g.gridy=1; form.add(new JLabel("Partida:"), g);
        g.gridx=1;           form.add(comboPartida, g);
        g.gridx=2;           form.add(btnCarregarPartidas, g);

        g.gridx=0;g.gridy=2; form.add(new JLabel("Gols mandante:"), g);
        g.gridx=1;           form.add(campoGolsMandante, g);

        g.gridx=0;g.gridy=3; form.add(new JLabel("Gols visitante:"), g);
        g.gridx=1;           form.add(campoGolsVisitante, g);

        g.gridx=0;g.gridy=4;g.gridwidth=3; form.add(btnApostar, g);

        listaModel = new DefaultListModel<>();
        JList<String> lista = new JList<>(listaModel);
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createTitledBorder("Minhas apostas"));

        add(form, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnCarregarCamp.addActionListener(e -> carregarCampeonatos());
        btnCarregarPartidas.addActionListener(e -> carregarPartidas());
        btnApostar.addActionListener(e -> registrarAposta());
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
            if (p.getCampeonato().equals(camp) && p.aceitaAposta()) {
                comboPartida.addItem(partidasCarregadas.size() + " — " + p.toString());
                partidasCarregadas.add(p);
                encontradas++;
            }
        }
        if (encontradas == 0) {
            JOptionPane.showMessageDialog(this,
                    "Nenhuma partida disponível para aposta neste campeonato.");
        }
    }

    private void registrarAposta() {
        if (comboPartida.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma partida."); return;
        }
        try {
            int idx = Integer.parseInt(
                    comboPartida.getSelectedItem().toString().split(" — ")[0]);
            int gM = Integer.parseInt(campoGolsMandante.getText().trim());
            int gV = Integer.parseInt(campoGolsVisitante.getText().trim());

            Partida partida = partidasCarregadas.get(idx);

            if (!partida.aceitaAposta()) {
                JOptionPane.showMessageDialog(this,
                        "Prazo encerrado (menos de 20 min para a partida)."); return;
            }

            Participante p = (Participante) MainFrame.usuarioLogado;
            Aposta aposta = new Aposta(p, partida, gM, gV);
            apostaDAO.inserir(aposta);
            listaModel.addElement(aposta.toString());

            campoGolsMandante.setText("");
            campoGolsVisitante.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite números válidos para os gols.");
        }
    }
}
