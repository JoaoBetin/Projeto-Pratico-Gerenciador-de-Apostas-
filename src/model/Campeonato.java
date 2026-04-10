package model;

public class Campeonato {
    private String nome;
    private Clube[] clubes;
    private int totalClubes;

    public Campeonato() {
        this.nome = "";
        this.clubes = new Clube[8];
        this.totalClubes = 0;
    }

    public Campeonato(String nome) {
        this.nome = nome;
        this.clubes = new Clube[8];
        this.totalClubes = 0;
    }

    public boolean adicionarClube(Clube clube) {
        if (totalClubes >= 8) return false;
        for (int i = 0; i < totalClubes; i++) {
            if (clubes[i].equals(clube)) return false;
        }
        clubes[totalClubes++] = clube;
        return true;
    }

    public Clube[] getClubes() {
        Clube[] resultado = new Clube[totalClubes];
        for (int i = 0; i < totalClubes; i++) {
            resultado[i] = clubes[i];
        }
        return resultado;
    }

    public String getNome()            { return nome; }
    public void setNome(String nome)   { this.nome = nome; }
    public int getTotalClubes()        { return totalClubes; }
    public boolean estaLotado()        { return totalClubes >= 8; }

    @Override
    public String toString() {
        return "Campeonato: " + nome + " (" + totalClubes + "/8 clubes)";
    }
}
