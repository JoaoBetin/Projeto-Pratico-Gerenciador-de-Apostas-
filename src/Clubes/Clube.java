package Clubes;

public class Clube {
    private String nome;

    public Clube(String nome) {
        this.nome = nome;
    }

    public Clube() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Clube{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
