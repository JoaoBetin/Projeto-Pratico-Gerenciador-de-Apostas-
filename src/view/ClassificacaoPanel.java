package view;

import model.*;
import javax.swing.*;
import java.awt.*;

public class ClassificacaoPanel extends JPanel {

    private JTextArea areaRanking;

    public ClassificacaoPanel() {
        setLayout(new BorderLayout(10, 10));

        JButton btnAtualizar = new JButton("Atualizar classificação");
        areaRanking = new JTextArea();
        areaRanking.setEditable(false);
        areaRanking.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        add(btnAtualizar, BorderLayout.NORTH);
        add(new JScrollPane(areaRanking), BorderLayout.CENTER);

        btnAtualizar.addActionListener(e -> atualizar());
    }

    private void atualizar() {
        if (MainFrame.grupo == null) {
            areaRanking.setText("Nenhum grupo criado ainda.");
            return;
        }
        Classificacao c = new Classificacao(
                MainFrame.grupo, MainFrame.partidas, MainFrame.totalPartidas);
        areaRanking.setText(c.getRankingFormatado());
    }
}