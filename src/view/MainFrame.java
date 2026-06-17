package view;

import model.PessoaModel.*;
import util.ConexaoBD;
import view.PaineisAdmin.*;
import view.PainelAmbos.*;
import view.PainelUser.*;

import javax.swing.*;

public class MainFrame extends JFrame {

    public static Pessoa usuarioLogado = null;

    private JTabbedPane abas;

    public MainFrame() {
        setTitle("Sistema de Apostas — Campeonato de Futebol");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        abas = new JTabbedPane();
        abas.addTab("Login",           new LoginPanel(this));
        abas.addTab("Clubes",          new ClubesPanel());
        abas.addTab("Campeonato",      new CampeonatoPanel());
        abas.addTab("Grupos",          new GrupoPanel());
        abas.addTab("Usuários",        new UsuariosPanel());
        abas.addTab("Meus Grupos",     new MeusGruposPanel());
        abas.addTab("Apostas",         new ApostasPanel());
        abas.addTab("Resultados",      new ResultadosPanel());
        abas.addTab("Classificação",   new ClassificacaoPanel());

        for (int i = 1; i < abas.getTabCount(); i++) {
            abas.setEnabledAt(i, false);
        }

        add(abas);
        setVisible(true);
    }

    public void onLogin(Pessoa pessoa) {
        usuarioLogado = pessoa;

        for (int i = 1; i < abas.getTabCount(); i++) {
            abas.setEnabledAt(i, false);
        }

        if (pessoa.isAdministrador()) {
            abas.setEnabledAt(1, true);
            abas.setEnabledAt(2, true);
            abas.setEnabledAt(3, true);
            abas.setEnabledAt(4, true);
            abas.setEnabledAt(7, true);
            abas.setEnabledAt(8, true);
            abas.setSelectedIndex(1);
        } else {
            abas.setEnabledAt(3, true);
            abas.setEnabledAt(5, true);
            abas.setEnabledAt(6, true);
            abas.setEnabledAt(8, true);
            abas.setSelectedIndex(5);
        }
    }

    public static void main(String[] args) {
        ConexaoBD.inicializarBanco();
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
