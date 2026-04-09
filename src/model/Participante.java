package model;

public class Participante extends Pessoa{
    int pontuacao;

    public Participante() {
        super();
        setPermissao(Permissao.USUARIO);
        this.pontuacao = 0;
    }

    public Participante(String nome, String email, String senha, Permissao permissao) {
        super(nome, email, senha, permissao);
        this.pontuacao = 0;
    }

    public Participante(String joão, String mail, String number) {
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pts){
        this.pontuacao += pts;
    }

    @Override
    public String toString() {
        return "Participante{" +
                "pontuacao=" + pontuacao +
                '}';
    }
}
