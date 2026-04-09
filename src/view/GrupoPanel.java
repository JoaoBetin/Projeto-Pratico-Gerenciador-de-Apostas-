package view;

import model.*;
import javax.swing.*;
import java.awt.*;

public class GrupoPanel extends JPanel {

    private DefaultListModel<String> listaModel;

    public GrupoPanel() {
        setLayout(new BorderLayout(10, 10));

        JButton btnAtualizar = new JButton("Atualizar lista");
        listaModel = new DefaultListModel<>();
        JList<String> lista = new JList<>(listaModel);
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createTitledBorder("Participantes do grupo"));

        add(btnAtualizar, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnAtualizar.addActionListener(e -> atualizar());
    }

    private void atualizar() {
        listaModel.clear();
        if (MainFrame.grupo == null) {
            listaModel.addElement("Nenhum grupo criado ainda.");
            return;
        }
        for (Participante p : MainFrame.grupo.getParticipantes()) {
            listaModel.addElement(p.toString());
        }
    }
}