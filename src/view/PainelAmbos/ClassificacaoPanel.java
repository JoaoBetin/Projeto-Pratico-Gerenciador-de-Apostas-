package view.PainelAmbos;

import dao.GrupoDAO;
import dao.PartidaDAO;
import model.CampeonatoModel.Classificacao;
import model.CampeonatoModel.Partida;
import model.PessoaModel.Grupo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClassificacaoPanel extends JPanel {

    private JComboBox<String> comboGrupos;
    private JTextArea areaRanking;

    private final GrupoDAO grupoDAO = new GrupoDAO();
    private final PartidaDAO partidaDAO = new PartidaDAO();

    private List<Grupo> gruposCarregados;

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
        gruposCarregados = grupoDAO.listarTodos();
        for (Grupo g : gruposCarregados) {
            comboGrupos.addItem(g.getNome());
        }
    }

    private void verClassificacao() {
        if (comboGrupos.getSelectedItem() == null) {
            areaRanking.setText("Nenhum grupo disponível."); return;
        }
        int idx = comboGrupos.getSelectedIndex();
        Grupo grupo = gruposCarregados.get(idx);

        List<Partida> partidas = partidaDAO.listarTodas();
        Partida[] partidasArray = partidas.toArray(new Partida[0]);

        Classificacao c = new Classificacao(
                grupo, partidasArray, partidasArray.length);
        areaRanking.setText(c.getRankingFormatado());
    }
}
