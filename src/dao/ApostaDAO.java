package dao;

import model.CampeonatoModel.Partida;
import model.PessoaModel.Aposta;
import model.PessoaModel.Participante;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApostaDAO {

    private final ParticipanteDAO participanteDAO = new ParticipanteDAO();
    private final PartidaDAO partidaDAO = new PartidaDAO();

    public Aposta inserir(Aposta aposta) {
        String sql = "INSERT INTO aposta " +
                "(participante_id, partida_id, gols_mandante_previsto, " +
                "gols_visitante_previsto, resultado_previsto) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConexaoBD.getConexao()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, aposta.getParticipante().getId());
            ps.setInt(2, aposta.getPartida().getId());
            ps.setInt(3, aposta.getGolsMandantePrevisto());
            ps.setInt(4, aposta.getGolsVisitantePrevisto());
            ps.setString(5, aposta.getResultadoPrevisto());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    aposta.setId(rs.getInt(1));
                }
            }
            return aposta;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir aposta no banco.", e);
        }
    }

    public List<Aposta> listarTodas() {
        String sql = "SELECT id, participante_id, partida_id, gols_mandante_previsto, " +
                "gols_visitante_previsto FROM aposta ORDER BY id";
        List<Aposta> apostas = new ArrayList<>();
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                apostas.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar apostas.", e);
        }
        return apostas;
    }

    public List<Aposta> listarPorPartida(int partidaId) {
        String sql = "SELECT id, participante_id, partida_id, gols_mandante_previsto, " +
                "gols_visitante_previsto FROM aposta WHERE partida_id = ? ORDER BY id";
        List<Aposta> apostas = new ArrayList<>();
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql)) {
            ps.setInt(1, partidaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    apostas.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar apostas da partida.", e);
        }
        return apostas;
    }

    public int contarTotal() {
        String sql = "SELECT COUNT(*) FROM aposta";
        try (PreparedStatement ps = ConexaoBD.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar apostas.", e);
        }
        return 0;
    }

    private Aposta mapear(ResultSet rs) throws SQLException {
        Participante participante = participanteDAO.buscarPorId(rs.getInt("participante_id"));
        Partida partida = partidaDAO.buscarPorId(rs.getInt("partida_id"));
        int golsMandante  = rs.getInt("gols_mandante_previsto");
        int golsVisitante = rs.getInt("gols_visitante_previsto");

        Aposta aposta = new Aposta(participante, partida, golsMandante, golsVisitante);
        aposta.setId(rs.getInt("id"));
        return aposta;
    }
}
