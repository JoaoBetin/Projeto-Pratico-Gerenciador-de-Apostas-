package dao;

import model.CampeonatoModel.Clube;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClubeDAO {

    public Clube inserir(Clube clube) {
        String sql = "INSERT INTO clube (nome, sigla, cidade) VALUES (?, ?, ?)";
        try (PreparedStatement ps = ConexaoBD.getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, clube.getNome());
            ps.setString(2, clube.getSigla());
            ps.setString(3, clube.getCidade());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    clube.setId(rs.getInt(1));
                }
            }
            return clube;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir clube no banco.", e);
        }
    }

    public List<Clube> listarTodos() {
        String sql = "SELECT id, nome, sigla, cidade FROM clube ORDER BY id";
        List<Clube> clubes = new ArrayList<>();
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                clubes.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clubes.", e);
        }
        return clubes;
    }

    public Clube buscarPorId(int id) {
        String sql = "SELECT id, nome, sigla, cidade FROM clube WHERE id = ?";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar clube por id.", e);
        }
        return null;
    }

    public int contarTotal() {
        String sql = "SELECT COUNT(*) FROM clube";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar clubes.", e);
        }
        return 0;
    }

    private Clube mapear(ResultSet rs) throws SQLException {
        Clube c = new Clube(rs.getString("nome"), rs.getString("sigla"), rs.getString("cidade"));
        c.setId(rs.getInt("id"));
        return c;
    }
}
