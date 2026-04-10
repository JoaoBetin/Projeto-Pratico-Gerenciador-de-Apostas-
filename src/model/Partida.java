package model;

import java.time.LocalDateTime;

public class Partida {
    private Clube casa;
    private Clube fora;
    private LocalDateTime dataHora;
    private int golsCasa;
    private int golsFora;
    private boolean encerrada;

    public Partida() {
        this.golsCasa = -1;
        this.golsFora = -1;
        this.encerrada = false;
    }

    public Partida(Clube casa, Clube fora, LocalDateTime dataHora) {
        this.casa = casa;
        this.fora = fora;
        this.dataHora = dataHora;
        this.golsCasa = -1;
        this.golsFora = -1;
        this.encerrada = false;
    }

    public void registrarResultado(int golsMandante, int golsVisitante) {
        this.golsCasa = golsMandante;
        this.golsFora = golsVisitante;
        this.encerrada = true;
    }

    public boolean aceitaAposta() {
        if (encerrada) return false;  // ← linha nova
        return LocalDateTime.now().isBefore(dataHora.minusMinutes(20));
    }

    public String getResultado() {
        if (!encerrada) return null;
        if (golsCasa > golsFora) return "mandante";
        if (golsFora > golsCasa) return "visitante";
        return "empate";
    }

    public Clube getCasa() {
        return casa;
    }

    public Clube getFora() {
        return fora;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public int getGolsCasa() {
        return golsCasa;
    }

    public int getGolsFora() {
        return golsFora;
    }

    public boolean isEncerrada() {
        return encerrada;
    }

    @Override
    public String toString() {
        String placar = encerrada
                ? golsCasa + " x " + golsFora
                : "a realizar";
        return casa.getSigla() + " vs " +
                fora.getSigla() + " [" + placar + "]";
    }
}
