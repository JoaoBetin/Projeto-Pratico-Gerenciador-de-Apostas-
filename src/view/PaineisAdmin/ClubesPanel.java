package view.PaineisAdmin;

import dao.ClubeDAO;
import model.CampeonatoModel.Clube;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClubesPanel extends JPanel {

    private JTextField campoNome, campoSigla, campoCidade;
    private DefaultListModel<String> listaModel;
    private final ClubeDAO clubeDAO = new ClubeDAO();

    public ClubesPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Cadastrar Clube"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; form.add(new JLabel("Nome:"), g);
        g.gridx = 1; campoNome = new JTextField(15); form.add(campoNome, g);

        g.gridx = 0; g.gridy = 1; form.add(new JLabel("Sigla:"), g);
        g.gridx = 1; campoSigla = new JTextField(5); form.add(campoSigla, g);

        g.gridx = 0; g.gridy = 2; form.add(new JLabel("Cidade:"), g);
        g.gridx = 1; campoCidade = new JTextField(15); form.add(campoCidade, g);

        JButton btnCadastrar = new JButton("Cadastrar");
        g.gridx = 0; g.gridy = 3; g.gridwidth = 2;
        form.add(btnCadastrar, g);

        listaModel = new DefaultListModel<>();
        JList<String> lista = new JList<>(listaModel);
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createTitledBorder("Clubes cadastrados"));

        add(form, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnCadastrar.addActionListener(e -> cadastrarClube());

        carregarClubesExistentes();
    }

    private void carregarClubesExistentes() {
        List<Clube> clubes = clubeDAO.listarTodos();
        for (Clube c : clubes) {
            listaModel.addElement(c.toString());
        }
    }

    private void cadastrarClube() {
        String nome   = campoNome.getText().trim();
        String sigla  = campoSigla.getText().trim();
        String cidade = campoCidade.getText().trim();

        if (nome.isEmpty() || sigla.isEmpty() || cidade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }
        if (clubeDAO.contarTotal() >= 8) {
            JOptionPane.showMessageDialog(this, "Limite de 8 clubes atingido.");
            return;
        }

        Clube c = new Clube(nome, sigla, cidade);
        clubeDAO.inserir(c);
        listaModel.addElement(c.toString());

        campoNome.setText("");
        campoSigla.setText("");
        campoCidade.setText("");
    }
}
