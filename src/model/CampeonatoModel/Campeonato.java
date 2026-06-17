package model.CampeonatoModel;

public class Campeonato {
    private int id;
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

    public int getId()                 { return id; }
    public void setId(int id)          { this.id = id; }
    public String getNome()            { return nome; }
    public void setNome(String nome)   { this.nome = nome; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Campeonato)) return false;
        Campeonato outro = (Campeonato) obj;
        return this.nome.equalsIgnoreCase(outro.nome);
    }

    @Override
    public String toString() {
        return "Campeonato: " + nome + " (" + totalClubes + "/8 clubes)";
    }
}
