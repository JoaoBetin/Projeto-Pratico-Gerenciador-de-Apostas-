package Apostas;

public class Aposta {
    private int golsCasa;
    private int golsFora;
    private ResultadoEsperado resultadoEsperado;

    public Aposta() {
    }

    public Aposta(int golsCasa, int golsFora, ResultadoEsperado resultadoEsperado) {
        this.golsCasa = golsCasa;
        this.golsFora = golsFora;
        this.resultadoEsperado = resultadoEsperado;
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

    public ResultadoEsperado getResultadoEsperado() {
        return resultadoEsperado;
    }

    public void setResultadoEsperado(ResultadoEsperado resultadoEsperado) {
        this.resultadoEsperado = resultadoEsperado;
    }

    @Override
    public String toString() {
        return "Aposta{" +
                "golsCasa=" + golsCasa +
                ", golsFora=" + golsFora +
                ", resultadoEsperado=" + resultadoEsperado +
                '}';
    }
}
