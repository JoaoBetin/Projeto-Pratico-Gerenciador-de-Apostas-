package view.PainelAmbos;

import dao.GrupoDAO;
import model.PessoaModel.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GrupoPanel extends JPanel {

    private JTextField campoNomeGrupo;
    private DefaultListModel<String> listaModel;
    private final GrupoDAO grupoDAO = new GrupoDAO();

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

        carregarGruposExistentes();
    }

    private void carregarGruposExistentes() {
        List<Grupo> grupos = grupoDAO.listarTodos();
        for (Grupo g : grupos) {
            listaModel.addElement(g.toString());
        }
    }

    private void criarGrupo() {
        String nome = campoNomeGrupo.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome do grupo."); return;
        }
        if (grupoDAO.contarTotal() >= 5) {
            JOptionPane.showMessageDialog(this, "Limite de 5 grupos atingido."); return;
        }
        Grupo novo = new Grupo(nome);
        grupoDAO.inserir(novo);
        listaModel.addElement(novo.toString());
        campoNomeGrupo.setText("");
        JOptionPane.showMessageDialog(this, "Grupo '" + nome + "' criado!");
    }
}
