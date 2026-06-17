package dao;

import model.PessoaModel.Administrador;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {

    public List<Administrador> listarTodos() {
        String sql = "SELECT id, nome, email, senha FROM administrador ORDER BY id";
        List<Administrador> administradores = new ArrayList<>();
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                administradores.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar administradores.", e);
        }
        return administradores;
    }

    public Administrador buscarPorEmailSenha(String email, String senha) {
        String sql = "SELECT id, nome, email, senha FROM administrador WHERE email = ? AND senha = ?";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar administrador.", e);
        }
        return null;
    }

    private Administrador mapear(ResultSet rs) throws SQLException {
        Administrador a = new Administrador(
                rs.getString("nome"), rs.getString("email"), rs.getString("senha"));
        a.setId(rs.getInt("id"));
        return a;
    }
}
