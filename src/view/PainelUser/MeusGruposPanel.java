package view.PainelUser;

import javax.swing.*;
import java.awt.*;
import model.CampeonatoModel.*;
import model.PessoaModel.*;
import view.MainFrame;
import view.PaineisAdmin.*;
import view.PainelAmbos.*;


public class MeusGruposPanel extends JPanel {

    private DefaultListModel<String> listaTodosModel;
    private DefaultListModel<String> listaMeusModel;
    private JList<String> listaTodos;

    public MeusGruposPanel() {
        setLayout(new BorderLayout(10, 10));

        listaTodosModel = new DefaultListModel<>();
        listaTodos = new JList<>(listaTodosModel);
        listaTodos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollTodos = new JScrollPane(listaTodos);
        scrollTodos.setBorder(BorderFactory.createTitledBorder("Grupos disponíveis"));

        listaMeusModel = new DefaultListModel<>();
        JList<String> listaMeus = new JList<>(listaMeusModel);
        JScrollPane scrollMeus = new JScrollPane(listaMeus);
        scrollMeus.setBorder(BorderFactory.createTitledBorder("Grupos que participo"));

        JButton btnCarregar = new JButton("Carregar grupos");
        JButton btnEntrar   = new JButton("Entrar no grupo selecionado");

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.add(btnCarregar);
        botoes.add(btnEntrar);

        JPanel centro = new JPanel(new GridLayout(1, 2, 10, 0));
        centro.add(scrollTodos);
        centro.add(scrollMeus);

        add(botoes, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);

        btnCarregar.addActionListener(e -> carregarGrupos());
        btnEntrar.addActionListener(e -> entrarNoGrupo());
    }

    private void carregarGrupos() {
        listaTodosModel.clear();
        for (int i = 0; i < MainFrame.totalGrupos; i++) {
            Grupo g = MainFrame.grupos[i];
            listaTodosModel.addElement(i + " — " + g.getNome()
                    + " (" + g.getTotalParticipantes() + "/5)");
        }
        if (listaTodosModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum grupo criado ainda.");
        }
    }

    private void entrarNoGrupo() {
        if (listaTodos.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um grupo da lista."); return;
        }

        String item = listaTodosModel.getElementAt(listaTodos.getSelectedIndex());
        int idx = Integer.parseInt(item.split(" — ")[0]);
        Grupo grupo = MainFrame.grupos[idx];
        Participante p = (Participante) MainFrame.usuarioLogado;

        if (grupo.estaLotado()) {
            JOptionPane.showMessageDialog(this, "Grupo cheio (máx. 5 participantes)."); return;
        }

        boolean entrou = grupo.adicionarParticipante(p);

        if (!entrou) {
            JOptionPane.showMessageDialog(this, "Você já está neste grupo."); return;
        }

        listaMeusModel.addElement(grupo.getNome());
        // Atualiza o item na lista para refletir o novo total
        listaTodosModel.set(listaTodos.getSelectedIndex(),
                idx + " — " + grupo.getNome()
                        + " (" + grupo.getTotalParticipantes() + "/5)");

        JOptionPane.showMessageDialog(this,
                "Você entrou no grupo '" + grupo.getNome() + "'!");
    }
}