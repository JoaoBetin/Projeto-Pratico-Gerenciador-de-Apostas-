package view.PaineisAdmin;

import javax.swing.*;
import java.awt.*;
import model.CampeonatoModel.*;
import model.PessoaModel.*;
import view.MainFrame;
import view.PainelAmbos.*;
import view.PainelUser.*;

public class UsuariosPanel extends JPanel {

    private JTextField campoNome, campoEmail, campoSenha;
    private DefaultListModel<String> listaModel;

    public UsuariosPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Cadastrar Usuário"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        campoNome  = new JTextField(20);
        campoEmail = new JTextField(20);
        campoSenha = new JTextField(20);

        g.gridx=0;g.gridy=0; form.add(new JLabel("Nome:"), g);
        g.gridx=1;            form.add(campoNome, g);

        g.gridx=0;g.gridy=1; form.add(new JLabel("Email:"), g);
        g.gridx=1;            form.add(campoEmail, g);

        g.gridx=0;g.gridy=2; form.add(new JLabel("Senha:"), g);
        g.gridx=1;            form.add(campoSenha, g);

        JButton btnCadastrar = new JButton("Cadastrar usuário");
        g.gridx=0;g.gridy=3;g.gridwidth=2; form.add(btnCadastrar, g);

        listaModel = new DefaultListModel<>();
        JList<String> lista = new JList<>(listaModel);
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createTitledBorder("Usuários cadastrados"));

        add(form, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnCadastrar.addActionListener(e -> cadastrar());
    }

    private void cadastrar() {
        String nome  = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String senha = campoSenha.getText().trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos."); return;
        }
        if (MainFrame.totalParticipantes >= 50) {
            JOptionPane.showMessageDialog(this, "Limite de usuários atingido."); return;
        }

        for (int i = 0; i < MainFrame.totalParticipantes; i++) {
            if (MainFrame.participantes[i].getEmail().equalsIgnoreCase(email)) {
                JOptionPane.showMessageDialog(this, "Email já cadastrado."); return;
            }
        }

        Participante p = new Participante(nome, email, senha);
        MainFrame.participantes[MainFrame.totalParticipantes++] = p;
        listaModel.addElement(p.getNome() + " — " + p.getEmail());

        campoNome.setText("");
        campoEmail.setText("");
        campoSenha.setText("");
        JOptionPane.showMessageDialog(this, "Usuário '" + nome + "' cadastrado!");
    }
}