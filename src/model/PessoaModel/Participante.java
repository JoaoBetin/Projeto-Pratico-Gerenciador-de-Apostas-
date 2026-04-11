package model.PessoaModel;

public class Participante extends Pessoa {

    private int pontuacao;

    public Participante() {
        super();
        setPermissao(Permissao.USUARIO);
        this.pontuacao = 0;
    }

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