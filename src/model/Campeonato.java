package model;

public class Campeonato {
    private String nome;
    private Clube[] clubes = new Clube[8];
    private int totalClubes = 0;

    public Campeonato(String nome) {
        this.nome = nome;
    }

    public boolean adicionarClube(Clube clube){
        if(totalClubes >= 8) return false;

        clubes[totalClubes++] = clube;
        return true;
    }

}
