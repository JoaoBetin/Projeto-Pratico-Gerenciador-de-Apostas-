package model.CampeonatoModel;

import java.util.Objects;

public class Clube {
    private String nome;
    private String sigla;
    private String cidade;

    public Clube() {
    }

    public Clube(String nome, String sigla, String cidade) {
        this.nome = nome;
        this.sigla = sigla;
        this.cidade = cidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return "Clube{" +
                "nome='" + nome + '\'' +
                ", sigla='" + sigla + '\'' +
                ", cidade='" + cidade + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Clube clube = (Clube) o;
        return Objects.equals(nome, clube.nome) && Objects.equals(sigla, clube.sigla) && Objects.equals(cidade, clube.cidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, sigla, cidade);
    }
}
