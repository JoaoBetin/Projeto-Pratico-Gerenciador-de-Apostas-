package dao;

import model.CampeonatoModel.Campeonato;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CampeonatoDAO {

    public Campeonato inserir(Campeonato campeonato) {
        String sql = "INSERT INTO campeonato (nome) VALUES (?)";
        try (PreparedStatement ps = ConexaoBD.getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, campeonato.getNome());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    campeonato.setId(rs.getInt(1));
                }
            }
            return campeonato;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir campeonato no banco.", e);
        }
    }

    public List<Campeonato> listarTodos() {
        String sql = "SELECT id, nome FROM campeonato ORDER BY id";
        List<Campeonato> campeonatos = new ArrayList<>();
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                campeonatos.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar campeonatos.", e);
        }
        return campeonatos;
    }

    public Campeonato buscarPorId(int id) {
        String sql = "SELECT id, nome FROM campeonato WHERE id = ?";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar campeonato por id.", e);
        }
        return null;
    }

    public int contarTotal() {
        String sql = "SELECT COUNT(*) FROM campeonato";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar campeonatos.", e);
        }
        return 0;
    }

    private Campeonato mapear(ResultSet rs) throws SQLException {
        Campeonato c = new Campeonato(rs.getString("nome"));
        c.setId(rs.getInt("id"));
        return c;
    }
}
