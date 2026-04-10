package model;

public class Participante extends Pessoa {

    private int pontuacao;

    // Construtor padrão
    public Participante() {
        super();
        setPermissao(Permissao.USUARIO);
        this.pontuacao = 0;
    }

    // Construtor sobrecarregado — permissão já fixa como USUARIO
    public Participante(String nome, String email, String senha) {
        super(nome, email, senha, Permissao.USUARIO);
        this.pontuacao = 0;
    }

    public int getPontuacao()          { return pontuacao; }
    public void setPontuacao(int pts) { this.pontuacao += pts; }

    @Override
    public String toString() {
        return getNome() + " | Pontos: " + pontuacao;
    }
}