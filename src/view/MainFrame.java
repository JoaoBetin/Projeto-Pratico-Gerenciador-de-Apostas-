package view;

import model.*;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    // Dados globais compartilhados entre todas as telas
    public static Clube[]       clubes       = new Clube[8];
    public static int           totalClubes  = 0;
    public static Campeonato    campeonato   = null;
    public static Partida[]     partidas     = new Partida[28];
    public static int           totalPartidas= 0;
    public static Grupo         grupo        = null;
    public static Aposta[]      apostas      = new Aposta[100];
    public static int           totalApostas = 0;
    public static Pessoa        usuarioLogado= null;

    private JTabbedPane abas;

    public MainFrame() {
        setTitle("Sistema de Apostas — Campeonato de Futebol");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centraliza na tela

        abas = new JTabbedPane();

        // Adiciona todas as abas
        abas.addTab("Login",        new LoginPanel(this));
        abas.addTab("Clubes",       new ClubesPanel());
        abas.addTab("Campeonato",   new CampeonatoPanel());
        abas.addTab("Grupo",        new GrupoPanel());
        abas.addTab("Apostas",      new ApostasPanel());
        abas.addTab("Resultados",   new ResultadosPanel());
        abas.addTab("Classificação",new ClassificacaoPanel());

        // Desabilita tudo exceto Login no início
        for (int i = 1; i < abas.getTabCount(); i++) {
            abas.setEnabledAt(i, false);
        }

        add(abas);
        setVisible(true);
    }

    // Chamado pelo LoginPanel após autenticação bem-sucedida
    public void onLogin(Pessoa pessoa) {
        usuarioLogado = pessoa;

        // Desabilita TODAS as abas protegidas antes de qualquer coisa
        for (int i = 1; i < abas.getTabCount(); i++) {
            abas.setEnabledAt(i, false);
        }

        // Habilita abas comuns a todos
        abas.setEnabledAt(1, true); // Clubes
        abas.setEnabledAt(2, true); // Campeonato
        abas.setEnabledAt(3, true); // Grupo
        abas.setEnabledAt(6, true); // Classificação

        if (pessoa.isAdministrador()) {
            abas.setEnabledAt(5, true); // Resultados — só admin
        } else {
            abas.setEnabledAt(4, true); // Apostas — só usuário
        }

        abas.setSelectedIndex(1);
    }

    public static void main(String[] args) {
        // Garante que o Swing rode na thread correta
        SwingUtilities.invokeLater(MainFrame::new);
    }
}