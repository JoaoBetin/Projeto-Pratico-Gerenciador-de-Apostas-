package model;

public class Administrador extends Pessoa{

    public Administrador() {
        super();
        setPermissao(Permissao.ADMINISTRADOR);
    }

    public Administrador(String nome, String email, String senha) {
        super(nome, email, senha, Permissao.ADMINISTRADOR);
    }
}
