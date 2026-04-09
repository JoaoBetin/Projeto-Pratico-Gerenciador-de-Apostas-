package view;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class CampeonatoPanel extends JPanel {

    private JTextField campoNome;
    private JComboBox<String> comboMandante, comboVisitante;
    private JTextField campoData, campoHora;
    private DefaultListModel<String> listaModel;

    public CampeonatoPanel() {
        setLayout(new BorderLayout(10, 10));

        // Painel superior: nome do campeonato
        JPanel formCamp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formCamp.setBorder(BorderFactory.createTitledBorder("Campeonato"));
        campoNome = new JTextField(20);
        JButton btnCriar = new JButton("Criar campeonato");
        formCamp.add(new JLabel("Nome:")); formCamp.add(campoNome);
        formCamp.add(btnCriar);

        // Painel central: cadastro de partidas
        JPanel formPartida = new JPanel(new GridBagLayout());
        formPartida.setBorder(BorderFactory.createTitledBorder("Cadastrar Partida"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        comboMandante  = new JComboBox<>();
        comboVisitante = new JComboBox<>();
        campoData = new JTextField("dd/MM/yyyy", 10);
        campoHora = new JTextField("HH:mm", 5);

        g.gridx=0;g.gridy=0; formPartida.add(new JLabel("Mandante:"),g);
        g.gridx=1;           formPartida.add(comboMandante,g);
        g.gridx=0;g.gridy=1; formPartida.add(new JLabel("Visitante:"),g);
        g.gridx=1;           formPartida.add(comboVisitante,g);
        g.gridx=0;g.gridy=2; formPartida.add(new JLabel("Data:"),g);
        g.gridx=1;           formPartida.add(campoData,g);
        g.gridx=0;g.gridy=3; formPartida.add(new JLabel("Hora:"),g);
        g.gridx=1;           formPartida.add(campoHora,g);

        JButton btnPartida = new JButton("Cadastrar Partida");
        g.gridx=0;g.gridy=4;g.gridwidth=2; formPartida.add(btnPartida,g);

        // Lista de partidas
        listaModel = new DefaultListModel<>();
        JList<String> lista = new JList<>(listaModel);
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createTitledBorder("Partidas cadastradas"));

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(formPartida, BorderLayout.NORTH);
        centro.add(scroll, BorderLayout.CENTER);

        add(formCamp, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);

        btnCriar.addActionListener(e -> criarCampeonato());
        btnPartida.addActionListener(e -> cadastrarPartida());
    }

    private void criarCampeonato() {
        String nome = campoNome.getText().trim();
        if (nome.isEmpty()) { JOptionPane.showMessageDialog(this,"Digite o nome."); return; }
        MainFrame.campeonato = new Campeonato(nome);
        // Popula os combos com os clubes já cadastrados
        comboMandante.removeAllItems();
        comboVisitante.removeAllItems();
        for (int i = 0; i < MainFrame.totalClubes; i++) {
            comboMandante.addItem(MainFrame.clubes[i].getSigla());
            comboVisitante.addItem(MainFrame.clubes[i].getSigla());
        }
        JOptionPane.showMessageDialog(this, "Campeonato '" + nome + "' criado!");
    }

    private void cadastrarPartida() {
        if (MainFrame.campeonato == null) {
            JOptionPane.showMessageDialog(this, "Crie o campeonato primeiro."); return;
        }
        if (comboMandante.getSelectedIndex() == comboVisitante.getSelectedIndex()) {
            JOptionPane.showMessageDialog(this, "Escolha clubes diferentes."); return;
        }
        try {
            Clube mandante  = MainFrame.clubes[comboMandante.getSelectedIndex()];
            Clube visitante = MainFrame.clubes[comboVisitante.getSelectedIndex()];

            String[] partesData = campoData.getText().split("/");
            String[] partesHora = campoHora.getText().split(":");
            LocalDateTime dt = LocalDateTime.of(
                    Integer.parseInt(partesData[2]), Integer.parseInt(partesData[1]),
                    Integer.parseInt(partesData[0]), Integer.parseInt(partesHora[0]),
                    Integer.parseInt(partesHora[1])
            );

            Partida p = new Partida(mandante, visitante, dt);
            MainFrame.partidas[MainFrame.totalPartidas++] = p;
            listaModel.addElement(p.toString() + " — " + campoData.getText() + " " + campoHora.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Data/hora inválida. Use dd/MM/yyyy e HH:mm.");
        }
    }
}