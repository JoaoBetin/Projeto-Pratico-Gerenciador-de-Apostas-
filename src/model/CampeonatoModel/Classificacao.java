package model.CampeonatoModel;

import model.PessoaModel.Aposta;
import model.PessoaModel.Grupo;
import model.PessoaModel.Participante;

public class Classificacao {
    private Grupo grupo;
    private Partida[] partidas;
    private int totalPartidas;

    public Classificacao() {
        this.partidas = new Partida[0];
        this.totalPartidas = 0;
    }

    public Classificacao(Grupo grupo, Partida[] partidas, int totalPartidas) {
        this.grupo = grupo;
        this.partidas = partidas;
        this.totalPartidas = totalPartidas;
    }

    public void calcularPontuacaoGeral(Aposta[] apostas, int totalApostas) {
        for (int i = 0; i < totalApostas; i++) {
            Aposta aposta = apostas[i];
            if (aposta.getPartida().isEncerrada()) {
                int pontos = aposta.calcularPontos(aposta.getPartida());
                aposta.getParticipante().setPontuacao(pontos);
            }
        }
    }

    public Participante[] gerarRanking() {
        Participante[] ranking = grupo.getParticipantes();
        int n = ranking.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (ranking[j].getPontuacao() < ranking[j + 1].getPontuacao()) {
                    Participante temp = ranking[j];
                    ranking[j] = ranking[j + 1];
                    ranking[j + 1] = temp;
                }
            }
        }
        return ranking;
    }

    public String getRankingFormatado() {
        Participante[] ranking = gerarRanking();
        StringBuilder sb = new StringBuilder();
        sb.append("=== Classificação — ").append(grupo.getNome()).append(" ===\n");

        for (int i = 0; i < ranking.length; i++) {
            sb.append(i + 1).append("º  ")
                    .append(ranking[i].getNome())
                    .append("  —  ")
                    .append(ranking[i].getPontuacao())
                    .append(" pts\n");
        }
        return sb.toString();
    }

    public Grupo getGrupo() { return grupo; }

    @Override
    public String toString() {
        return getRankingFormatado();
    }
}
