package view.PaineisAdmin;

import model.CampeonatoModel.Campeonato;
import model.CampeonatoModel.Partida;
import model.PessoaModel.Aposta;
import view.MainFrame;

import javax.swing.*;
import java.awt.*;

public class ResultadosPanel extends JPanel {

    private JComboBox<String> comboCampeonato;
    private JComboBox<String> comboPartida;
    private JTextField campoGolsMandante, campoGolsVisitante;

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
        for (int i = 0; i < MainFrame.totalCampeonatos; i++) {
            comboCampeonato.addItem(MainFrame.campeonatos[i].getNome());
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
        Campeonato camp = MainFrame.campeonatos[idxCamp];

        int encontradas = 0;
        for (int i = 0; i < MainFrame.totalPartidas; i++) {
            Partida p = MainFrame.partidas[i];
            if (p.getCampeonato().equals(camp) && !p.isEncerrada()) {
                comboPartida.addItem(i + " — " + p.toString());
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

            MainFrame.partidas[idx].registrarResultado(gM, gV);

            for (int i = 0; i < MainFrame.totalApostas; i++) {
                Aposta a = MainFrame.apostas[i];
                if (a.getPartida() == MainFrame.partidas[idx]) {
                    int pontos = a.calcularPontos(a.getPartida());
                    a.getParticipante().setPontuacao(pontos);
                }
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