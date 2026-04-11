package model.PessoaModel;

import interfaces.Pontuavel;
import model.CampeonatoModel.Partida;

public class Aposta implements Pontuavel {
    private Participante participante;
    private Partida partida;
    private String resultadoPrevisto;
    private int golsMandantePrevisto;
    private int golsVisitantePrevisto;

    public Aposta() {
        this.golsMandantePrevisto = 0;
        this.golsVisitantePrevisto = 0;
    }

    public Aposta(Participante participante, Partida partida,
                  int golsMandante, int golsVisitante) {
        this.participante = participante;
        this.partida = partida;
        this.golsMandantePrevisto = golsMandante;
        this.golsVisitantePrevisto = golsVisitante;

        if (golsMandante > golsVisitante)       this.resultadoPrevisto = "mandante";
        else if (golsVisitante > golsMandante)  this.resultadoPrevisto = "visitante";
        else                                    this.resultadoPrevisto = "empate";
    }

    @Override
    public int calcularPontos(Partida partida) {
        if (!partida.isEncerrada()) return 0;

        boolean acertouResultado = resultadoPrevisto.equals(partida.getResultado());
        boolean acertouPlacar =
                golsMandantePrevisto == partida.getGolsMandante() && golsVisitantePrevisto == partida.getGolsVisitante();

        if (acertouPlacar)    return 10;
        if (acertouResultado) return 5;
        return 0;
    }


    public Participante getParticipante() {
        return participante;
    }

    public Partida getPartida() {
        return partida;
    }

    public String getResultadoPrevisto() {
        return resultadoPrevisto;
    }

    public int getGolsMandantePrevisto() {
        return golsMandantePrevisto;
    }

    public int getGolsVisitantePrevisto() {
        return golsVisitantePrevisto;
    }

    @Override
    public String toString() {
        return participante.getNome() + " apostou: " +
                golsMandantePrevisto + " x " + golsVisitantePrevisto +
                " em " + partida.toString();
    }
}
