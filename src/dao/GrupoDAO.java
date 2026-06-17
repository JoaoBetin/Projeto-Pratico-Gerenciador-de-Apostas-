package dao;

import model.PessoaModel.Grupo;
import model.PessoaModel.Participante;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoDAO {

    private final ParticipanteDAO participanteDAO = new ParticipanteDAO();

    public Grupo inserir(Grupo grupo) {
        String sql = "INSERT INTO grupo (nome) VALUES (?)";
        try (PreparedStatement ps = ConexaoBD.getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, grupo.getNome());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    grupo.setId(rs.getInt(1));
                }
            }
            return grupo;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir grupo no banco.", e);
        }
    }

    public boolean adicionarParticipante(int grupoId, int participanteId) {
        if (jaParticipa(grupoId, participanteId)) return false;
        if (contarParticipantes(grupoId) >= 5) return false;

        String sql = "INSERT INTO grupo_participante (grupo_id, participante_id) VALUES (?, ?)";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql)) {
            ps.setInt(1, grupoId);
            ps.setInt(2, participanteId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar participante ao grupo.", e);
        }
    }

    public boolean jaParticipa(int grupoId, int participanteId) {
        String sql = "SELECT 1 FROM grupo_participante WHERE grupo_id = ? AND participante_id = ?";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql)) {
            ps.setInt(1, grupoId);
            ps.setInt(2, participanteId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar participação no grupo.", e);
        }
    }

    public int contarParticipantes(int grupoId) {
        String sql = "SELECT COUNT(*) FROM grupo_participante WHERE grupo_id = ?";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql)) {
            ps.setInt(1, grupoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar participantes do grupo.", e);
        }
        return 0;
    }

    public Grupo buscarPorId(int id) {
        String sql = "SELECT id, nome FROM grupo WHERE id = ?";
        Grupo grupo;
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                grupo = new Grupo(rs.getString("nome"));
                grupo.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar grupo por id.", e);
        }
        carregarParticipantes(grupo);
        return grupo;
    }

    public List<Grupo> listarTodos() {
        String sql = "SELECT id, nome FROM grupo ORDER BY id";
        List<Grupo> grupos = new ArrayList<>();
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Grupo g = new Grupo(rs.getString("nome"));
                g.setId(rs.getInt("id"));
                carregarParticipantes(g);
                grupos.add(g);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar grupos.", e);
        }
        return grupos;
    }

    public int contarTotal() {
        String sql = "SELECT COUNT(*) FROM grupo";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar grupos.", e);
        }
        return 0;
    }

    private void carregarParticipantes(Grupo grupo) {
        String sql = "SELECT participante_id FROM grupo_participante " +
                "WHERE grupo_id = ? ORDER BY participante_id";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql)) {
            ps.setInt(1, grupo.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Participante p = participanteDAO.buscarPorId(rs.getInt("participante_id"));
                    if (p != null) {
                        grupo.adicionarParticipante(p);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao carregar participantes do grupo.", e);
        }
    }
}
