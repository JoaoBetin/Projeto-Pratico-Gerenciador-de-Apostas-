package dao;

import model.PessoaModel.Participante;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipanteDAO {

    public Participante inserir(Participante participante) {
        String sql = "INSERT INTO participante (nome, email, senha, pontuacao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = ConexaoBD.getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, participante.getNome());
            ps.setString(2, participante.getEmail());
            ps.setString(3, participante.getSenha());
            ps.setInt(4, participante.getPontuacao());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    participante.setId(rs.getInt(1));
                }
            }
            return participante;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir participante no banco.", e);
        }
    }

    public void atualizarPontuacao(Participante participante) {
        String sql = "UPDATE participante SET pontuacao = ? WHERE id = ?";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql)) {
            ps.setInt(1, participante.getPontuacao());
            ps.setInt(2, participante.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pontuação do participante.", e);
        }
    }

    public List<Participante> listarTodos() {
        String sql = "SELECT id, nome, email, senha, pontuacao FROM participante ORDER BY id";
        List<Participante> participantes = new ArrayList<>();
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                participantes.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar participantes.", e);
        }
        return participantes;
    }

    public Participante buscarPorId(int id) {
        String sql = "SELECT id, nome, email, senha, pontuacao FROM participante WHERE id = ?";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar participante por id.", e);
        }
        return null;
    }

    public Participante buscarPorEmail(String email) {
        String sql = "SELECT id, nome, email, senha, pontuacao FROM participante WHERE LOWER(email) = LOWER(?)";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar participante por email.", e);
        }
        return null;
    }

    public Participante buscarPorEmailSenha(String email, String senha) {
        String sql = "SELECT id, nome, email, senha, pontuacao FROM participante WHERE email = ? AND senha = ?";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar participante.", e);
        }
        return null;
    }

    public int contarTotal() {
        String sql = "SELECT COUNT(*) FROM participante";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar participantes.", e);
        }
        return 0;
    }

    private Participante mapear(ResultSet rs) throws SQLException {
        Participante p = new Participante(
                rs.getString("nome"), rs.getString("email"), rs.getString("senha"));
        p.setId(rs.getInt("id"));
        p.setPontuacaoAbsoluta(rs.getInt("pontuacao"));
        return p;
    }
}
