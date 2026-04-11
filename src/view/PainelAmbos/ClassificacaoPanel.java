package view.PainelAmbos;

import model.CampeonatoModel.Classificacao;
import model.PessoaModel.Grupo;
import view.MainFrame;

import javax.swing.*;
import java.awt.*;

public class ClassificacaoPanel extends JPanel {

    private JComboBox<String> comboGrupos;
    private JTextArea areaRanking;

    public ClassificacaoPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboGrupos = new JComboBox<>();
        JButton btnCarregar   = new JButton("Carregar grupos");
        JButton btnAtualizar  = new JButton("Ver classificação");
        topo.add(new JLabel("Grupo:"));
        topo.add(comboGrupos);
        topo.add(btnCarregar);
        topo.add(btnAtualizar);

        areaRanking = new JTextArea();
        areaRanking.setEditable(false);
        areaRanking.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(areaRanking), BorderLayout.CENTER);

        btnCarregar.addActionListener(e -> carregarGrupos());
        btnAtualizar.addActionListener(e -> verClassificacao());
    }

    private void carregarGrupos() {
        comboGrupos.removeAllItems();
        for (int i = 0; i < MainFrame.totalGrupos; i++) {
            comboGrupos.addItem(MainFrame.grupos[i].getNome());
        }
    }

    private void verClassificacao() {
        if (comboGrupos.getSelectedItem() == null) {
            areaRanking.setText("Nenhum grupo disponível."); return;
        }
        int idx = comboGrupos.getSelectedIndex();
        Grupo grupo = MainFrame.grupos[idx];
        Classificacao c = new Classificacao(
                grupo, MainFrame.partidas, MainFrame.totalPartidas);
        areaRanking.setText(c.getRankingFormatado());
    }
}