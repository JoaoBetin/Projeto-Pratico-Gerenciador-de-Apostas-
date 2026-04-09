package view;

import model.*;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private MainFrame frame;
    private JTextField campoEmail;
    private JPasswordField campoSenha;

    // Usuários pré-cadastrados para teste
    private Pessoa[] usuarios = {
            new Administrador("Admin", "admin@admin.com", "1234"),
            new Participante("João",   "joao@email.com",  "1234"),
            new Participante("Maria",  "maria@email.com", "1234"),
            new Participante("Pedro",  "pedro@email.com", "1234"),
            new Participante("Ana",    "ana@email.com",   "1234")
    };

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
        g.gridx = 1;               campoEmail = new JTextField(20); add(campoEmail, g);

        g.gridx = 0; g.gridy = 2; add(new JLabel("Senha:"), g);
        g.gridx = 1;               campoSenha = new JPasswordField(20); add(campoSenha, g);

        JButton btnEntrar = new JButton("Entrar");
        g.gridx = 0; g.gridy = 3; g.gridwidth = 2;
        add(btnEntrar, g);

        btnEntrar.addActionListener(e -> fazerLogin());

        // Permite login com Enter
        campoSenha.addActionListener(e -> fazerLogin());
    }

    private void fazerLogin() {
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword());

        for (Pessoa p : usuarios) {
            if (p.autenticar(email, senha)) {
                // Popula participantes no grupo global
                if (!p.isAdministrador()) {
                    if (MainFrame.grupo == null) {
                        MainFrame.grupo = new Grupo("Grupo Principal");
                    }
                    MainFrame.grupo.adicionarParticipante((Participante) p);
                }
                frame.onLogin(p);
                return;
            }
        }
        JOptionPane.showMessageDialog(this,
                "Email ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}