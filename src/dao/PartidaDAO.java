package dao;

import model.CampeonatoModel.Campeonato;
import model.CampeonatoModel.Clube;
import model.CampeonatoModel.Partida;
import util.ConexaoBD;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO {

    private final ClubeDAO clubeDAO = new ClubeDAO();
    private final CampeonatoDAO campeonatoDAO = new CampeonatoDAO();

    public Partida inserir(Partida partida) {
        String sql = "INSERT INTO partida " +
                "(clube_mandante_id, clube_visitante_id, campeonato_id, data_hora, " +
                "gols_mandante, gols_visitante, encerrada) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConexaoBD.getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, partida.getClubeMandante().getId());
            ps.setInt(2, partida.getClubeVisitante().getId());
            ps.setInt(3, partida.getCampeonato().getId());
            ps.setTimestamp(4, Timestamp.valueOf(partida.getDataHora()));
            ps.setInt(5, partida.getGolsMandante());
            ps.setInt(6, partida.getGolsVisitante());
            ps.setBoolean(7, partida.isEncerrada());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    partida.setId(rs.getInt(1));
                }
            }
            return partida;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir partida no banco.", e);
        }
    }

    public void atualizarResultado(Partida partida) {
        String sql = "UPDATE partida SET gols_mandante = ?, gols_visitante = ?, " +
                "encerrada = ? WHERE id = ?";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql)) {
            ps.setInt(1, partida.getGolsMandante());
            ps.setInt(2, partida.getGolsVisitante());
            ps.setBoolean(3, partida.isEncerrada());
            ps.setInt(4, partida.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar resultado da partida.", e);
        }
    }

    public List<Partida> listarTodas() {
        String sql = "SELECT id, clube_mandante_id, clube_visitante_id, campeonato_id, " +
                "data_hora, gols_mandante, gols_visitante, encerrada FROM partida ORDER BY id";
        List<Partida> partidas = new ArrayList<>();
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                partidas.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar partidas.", e);
        }
        return partidas;
    }

    public Partida buscarPorId(int id) {
        String sql = "SELECT id, clube_mandante_id, clube_visitante_id, campeonato_id, " +
                "data_hora, gols_mandante, gols_visitante, encerrada FROM partida WHERE id = ?";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar partida por id.", e);
        }
        return null;
    }

    public int contarTotal() {
        String sql = "SELECT COUNT(*) FROM partida";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar partidas.", e);
        }
        return 0;
    }

    private Partida mapear(ResultSet rs) throws SQLException {
        Clube mandante  = clubeDAO.buscarPorId(rs.getInt("clube_mandante_id"));
        Clube visitante = clubeDAO.buscarPorId(rs.getInt("clube_visitante_id"));
        Campeonato campeonato = campeonatoDAO.buscarPorId(rs.getInt("campeonato_id"));
        LocalDateTime dataHora = rs.getTimestamp("data_hora").toLocalDateTime();

        Partida partida = new Partida(mandante, visitante, dataHora, campeonato);
        partida.setId(rs.getInt("id"));

        int golsMandante  = rs.getInt("gols_mandante");
        int golsVisitante = rs.getInt("gols_visitante");
        boolean encerrada = rs.getBoolean("encerrada");
        if (encerrada) {
            partida.registrarResultado(golsMandante, golsVisitante);
        }
        return partida;
    }
}
