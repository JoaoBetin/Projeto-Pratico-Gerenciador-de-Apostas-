package view.PainelAmbos;

import dao.AdministradorDAO;
import dao.ParticipanteDAO;
import model.PessoaModel.*;
import view.MainFrame;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private MainFrame frame;
    private JTextField campoEmail;
    private JPasswordField campoSenha;

    private final AdministradorDAO administradorDAO = new AdministradorDAO();
    private final ParticipanteDAO participanteDAO = new ParticipanteDAO();

    public LoginPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Sistema de Apostas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        add(titulo, g);

        g.gridwidth = 1;
        g.gridx = 0; g.gridy = 1; add(new JLabel("Email:"), g);
        g.gridx = 1; campoEmail = new JTextField(20); add(campoEmail, g);

        g.gridx = 0; g.gridy = 2; add(new JLabel("Senha:"), g);
        g.gridx = 1; campoSenha = new JPasswordField(20); add(campoSenha, g);

        JButton btnEntrar = new JButton("Entrar");
        g.gridx = 0; g.gridy = 3; g.gridwidth = 2;
        add(btnEntrar, g);

        btnEntrar.addActionListener(e -> fazerLogin());
        campoSenha.addActionListener(e -> fazerLogin());
    }

    private void fazerLogin() {
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword());

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha email e senha.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Administrador admin = administradorDAO.buscarPorEmailSenha(email, senha);
        if (admin != null) {
            frame.onLogin(admin);
            return;
        }

        Participante participante = participanteDAO.buscarPorEmailSenha(email, senha);
        if (participante != null) {
            frame.onLogin(participante);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Email ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
