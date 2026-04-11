package view.PaineisAdmin;

import model.CampeonatoModel.Campeonato;
import model.CampeonatoModel.Clube;
import model.CampeonatoModel.Partida;
import view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class CampeonatoPanel extends JPanel {

    private JTextField campoNome;
    private JComboBox<String> comboCampeonato;
    private JComboBox<String> comboMandante, comboVisitante;
    private JTextField campoData, campoHora;
    private DefaultListModel<String> listaModel;

    public CampeonatoPanel() {
        setLayout(new BorderLayout(10, 10));

        // Criar campeonato
        JPanel formCamp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formCamp.setBorder(BorderFactory.createTitledBorder("Campeonato"));
        campoNome = new JTextField(20);
        JButton btnCriar = new JButton("Criar campeonato");
        formCamp.add(new JLabel("Nome:")); formCamp.add(campoNome);
        formCamp.add(btnCriar);

        // Selecionar campeonato para cadastrar partida
        JPanel formPartida = new JPanel(new GridBagLayout());
        formPartida.setBorder(BorderFactory.createTitledBorder("Cadastrar Partida"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        comboCampeonato = new JComboBox<>();
        comboMandante   = new JComboBox<>();
        comboVisitante  = new JComboBox<>();
        campoData       = new JTextField("dd/MM/yyyy", 10);
        campoHora       = new JTextField("HH:mm", 5);

        JButton btnCarregarClubes = new JButton("Carregar clubes");

        g.gridx=0;g.gridy=0; formPartida.add(new JLabel("Campeonato:"), g);
        g.gridx=1;           formPartida.add(comboCampeonato, g);
        g.gridx=2;           formPartida.add(btnCarregarClubes, g);

        g.gridx=0;g.gridy=1; formPartida.add(new JLabel("Mandante:"), g);
        g.gridx=1;g.gridwidth=2; formPartida.add(comboMandante, g);
        g.gridwidth=1;

        g.gridx=0;g.gridy=2; formPartida.add(new JLabel("Visitante:"), g);
        g.gridx=1;g.gridwidth=2; formPartida.add(comboVisitante, g);
        g.gridwidth=1;

        g.gridx=0;g.gridy=3; formPartida.add(new JLabel("Data (dd/MM/yyyy):"), g);
        g.gridx=1;g.gridwidth=2; formPartida.add(campoData, g);
        g.gridwidth=1;

        g.gridx=0;g.gridy=4; formPartida.add(new JLabel("Hora (HH:mm):"), g);
        g.gridx=1;g.gridwidth=2; formPartida.add(campoHora, g);
        g.gridwidth=1;

        JButton btnPartida = new JButton("Cadastrar Partida");
        g.gridx=0;g.gridy=5;g.gridwidth=3; formPartida.add(btnPartida, g);

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
        btnCarregarClubes.addActionListener(e -> carregarClubes());
        btnPartida.addActionListener(e -> cadastrarPartida());
    }

    private void criarCampeonato() {
        String nome = campoNome.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome."); return;
        }
        if (MainFrame.totalCampeonatos >= 10) {
            JOptionPane.showMessageDialog(this, "Limite de campeonatos atingido."); return;
        }
        Campeonato c = new Campeonato(nome);
        MainFrame.campeonatos[MainFrame.totalCampeonatos++] = c;
        comboCampeonato.addItem(nome);
        campoNome.setText("");
        JOptionPane.showMessageDialog(this, "Campeonato '" + nome + "' criado!");
    }

    private void carregarClubes() {
        comboMandante.removeAllItems();
        comboVisitante.removeAllItems();
        for (int i = 0; i < MainFrame.totalClubes; i++) {
            comboMandante.addItem(MainFrame.clubes[i].getSigla());
            comboVisitante.addItem(MainFrame.clubes[i].getSigla());
        }
    }

    private void cadastrarPartida() {
        if (comboCampeonato.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um campeonato."); return;
        }
        if (comboMandante.getSelectedIndex() == comboVisitante.getSelectedIndex()) {
            JOptionPane.showMessageDialog(this, "Escolha clubes diferentes."); return;
        }
        try {
            int idxCamp     = comboCampeonato.getSelectedIndex();
            Campeonato camp = MainFrame.campeonatos[idxCamp];
            Clube mandante  = MainFrame.clubes[comboMandante.getSelectedIndex()];
            Clube visitante = MainFrame.clubes[comboVisitante.getSelectedIndex()];

            String[] partesData = campoData.getText().split("/");
            String[] partesHora = campoHora.getText().split(":");
            LocalDateTime dt = LocalDateTime.of(
                    Integer.parseInt(partesData[2]), Integer.parseInt(partesData[1]),
                    Integer.parseInt(partesData[0]), Integer.parseInt(partesHora[0]),
                    Integer.parseInt(partesHora[1])
            );

            Partida p = new Partida(mandante, visitante, dt, camp);
            MainFrame.partidas[MainFrame.totalPartidas++] = p;
            listaModel.addElement("[" + camp.getNome() + "] " + p.toString()
                    + " — " + campoData.getText() + " " + campoHora.getText());

            campoData.setText("dd/MM/yyyy");
            campoHora.setText("HH:mm");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Data/hora inválida. Use dd/MM/yyyy e HH:mm.");
        }
    }
}