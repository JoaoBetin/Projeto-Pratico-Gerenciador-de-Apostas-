package view.PainelAmbos;

import model.CampeonatoModel.*;
import model.PessoaModel.*;
import view.MainFrame;
import view.PaineisAdmin.*;
import view.PainelUser.*;

import javax.swing.*;
import java.awt.*;

public class GrupoPanel extends JPanel {

    private JTextField campoNomeGrupo;
    private DefaultListModel<String> listaModel;

    public GrupoPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.setBorder(BorderFactory.createTitledBorder("Criar Grupo"));
        campoNomeGrupo = new JTextField(20);
        JButton btnCriar = new JButton("Criar grupo");
        form.add(new JLabel("Nome do grupo:"));
        form.add(campoNomeGrupo);
        form.add(btnCriar);

        listaModel = new DefaultListModel<>();
        JList<String> lista = new JList<>(listaModel);
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createTitledBorder("Grupos criados"));

        add(form, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnCriar.addActionListener(e -> criarGrupo());
    }

    private void criarGrupo() {
        String nome = campoNomeGrupo.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome do grupo."); return;
        }
        if (MainFrame.totalGrupos >= 5) {
            JOptionPane.showMessageDialog(this, "Limite de 5 grupos atingido."); return;
        }
        Grupo novo = new Grupo(nome);
        MainFrame.grupos[MainFrame.totalGrupos++] = novo;
        listaModel.addElement(novo.toString());
        campoNomeGrupo.setText("");
        JOptionPane.showMessageDialog(this, "Grupo '" + nome + "' criado!");
    }
}