package view;

import model.CampeonatoModel.*;
import model.PessoaModel.*;
import view.PaineisAdmin.*;
import view.PainelAmbos.*;
import view.PainelUser.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static Clube[]         clubes           = new Clube[8];
    public static int             totalClubes      = 0;
    public static Campeonato[]    campeonatos      = new Campeonato[10];
    public static int             totalCampeonatos = 0;
    public static Partida[]       partidas         = new Partida[100];
    public static int             totalPartidas    = 0;
    public static Grupo[]         grupos           = new Grupo[5];
    public static int             totalGrupos      = 0;
    public static Aposta[]        apostas          = new Aposta[200];
    public static int             totalApostas     = 0;
    public static Pessoa          usuarioLogado    = null;
    public static Participante[]  participantes    = new Participante[50];
    public static int             totalParticipantes = 0;
    public static Administrador[] administradores  = {
            new Administrador("Admin", "admin@admin.com", "1234")
    };

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
        SwingUtilities.invokeLater(MainFrame::new);
    }
}