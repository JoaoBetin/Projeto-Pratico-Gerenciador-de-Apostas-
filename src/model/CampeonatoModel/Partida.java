package model.CampeonatoModel;

import java.time.LocalDateTime;

public class Partida {

    private Clube clubeMandante;
    private Clube clubeVisitante;
    private LocalDateTime dataHora;
    private int golsMandante;
    private int golsVisitante;
    private boolean encerrada;
    private Campeonato campeonato;

    public Partida() {
        this.golsMandante  = -1;
        this.golsVisitante = -1;
        this.encerrada     = false;
    }

    public Partida(Clube clubeMandante, Clube clubeVisitante,
                   LocalDateTime dataHora, Campeonato campeonato) {
        this.clubeMandante  = clubeMandante;
        this.clubeVisitante = clubeVisitante;
        this.dataHora       = dataHora;
        this.campeonato     = campeonato;
        this.golsMandante   = -1;
        this.golsVisitante  = -1;
        this.encerrada      = false;
    }

    public void registrarResultado(int golsMandante, int golsVisitante) {
        this.golsMandante  = golsMandante;
        this.golsVisitante = golsVisitante;
        this.encerrada     = true;
    }

    public boolean aceitaAposta() {
        if (encerrada) return false;
        return LocalDateTime.now().isBefore(dataHora.minusMinutes(20));
    }

    public String getResultado() {
        if (!encerrada) return null;
        if (golsMandante > golsVisitante) return "mandante";
        if (golsVisitante > golsMandante) return "visitante";
        return "empate";
    }

    public Clube getClubeMandante()    { return clubeMandante; }
    public Clube getClubeVisitante()   { return clubeVisitante; }
    public LocalDateTime getDataHora() { return dataHora; }
    public int getGolsMandante()       { return golsMandante; }
    public int getGolsVisitante()      { return golsVisitante; }
    public boolean isEncerrada()       { return encerrada; }
    public Campeonato getCampeonato()  { return campeonato; }

    @Override
    public String toString() {
        String placar = encerrada
                ? golsMandante + " x " + golsVisitante
                : "a realizar";
        return clubeMandante.getSigla() + " vs " +
                clubeVisitante.getSigla() + " [" + placar + "]";
    }
}