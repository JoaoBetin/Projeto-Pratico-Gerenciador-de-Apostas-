package view;

import model.*;
import javax.swing.*;
import java.awt.*;

public class ResultadosPanel extends JPanel {

    private JComboBox<String> comboPartida;
    private JTextField campoGolsMandante, campoGolsVisitante;

    public ResultadosPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Registrar Resultado (Admin)"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        comboPartida = new JComboBox<>();
        campoGolsMandante  = new JTextField(4);
        campoGolsVisitante = new JTextField(4);

        g.gridx=0;g.gridy=0; form.add(new JLabel("Partida:"),g);
        g.gridx=1;g.gridwidth=3; form.add(comboPartida,g);
        g.gridwidth=1;
        g.gridx=0;g.gridy=1; form.add(new JLabel("Gols mandante:"),g);
        g.gridx=1;            form.add(campoGolsMandante,g);
        g.gridx=2;            form.add(new JLabel("Gols visitante:"),g);
        g.gridx=3;            form.add(campoGolsVisitante,g);

        JButton btnCarregar  = new JButton("Carregar partidas");
        JButton btnRegistrar = new JButton("Registrar resultado");
        g.gridx=0;g.gridy=2;g.gridwidth=2; form.add(btnCarregar,g);
        g.gridx=2;g.gridwidth=2;            form.add(btnRegistrar,g);

        add(form, BorderLayout.NORTH);

        btnCarregar.addActionListener(e -> carregarPartidas());
        btnRegistrar.addActionListener(e -> registrarResultado());
    }

    private void carregarPartidas() {
        comboPartida.removeAllItems();
        for (int i = 0; i < MainFrame.totalPartidas; i++) {
            Partida p = MainFrame.partidas[i];
            if (!p.isEncerrada()) {
                comboPartida.addItem(i + " — " + p.toString());
            }
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

            // Calcula pontos automaticamente para todas as apostas dessa partida
            for (int i = 0; i < MainFrame.totalApostas; i++) {
                Aposta a = MainFrame.apostas[i];
                if (a.getPartida() == MainFrame.partidas[idx]) {
                    int pontos = a.calcularPontos(a.getPartida());
                    a.getParticipante().setPontuacao(pontos);
                }
            }

            JOptionPane.showMessageDialog(this, "Resultado registrado e pontos calculados!");
            campoGolsMandante.setText("");
            campoGolsVisitante.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite números válidos.");
        }
    }
}