package Partidas;

import Clubes.Clube;

import java.time.LocalDateTime;

public class Partida {
    private Clube casa;
    private Clube fora;
    private int golsCasa;
    private int golsFora;
    private LocalDateTime dataHora;

    public Partida() {
    }

    public Partida(Clube casa, Clube fora, int golsCasa, int golsFora, LocalDateTime dataHora) {
        this.casa = casa;
        this.fora = fora;
        this.golsCasa = golsCasa;
        this.golsFora = golsFora;
        this.dataHora = dataHora;
    }

    public Clube getCasa() {
        return casa;
    }

    public void setCasa(Clube casa) {
        this.casa = casa;
    }

    public Clube getFora() {
        return fora;
    }

    public void setFora(Clube fora) {
        this.fora = fora;
    }

    public int getGolsCasa() {
        return golsCasa;
    }

    public void setGolsCasa(int golsCasa) {
        this.golsCasa = golsCasa;
    }

    public int getGolsFora() {
        return golsFora;
    }

    public void setGolsFora(int golsFora) {
        this.golsFora = golsFora;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public String toString() {
        return "Partida{" +
                "casa=" + casa +
                ", fora=" + fora +
                ", golsCasa=" + golsCasa +
                ", golsFora=" + golsFora +
                ", dataHora=" + dataHora +
                '}';
    }
}
