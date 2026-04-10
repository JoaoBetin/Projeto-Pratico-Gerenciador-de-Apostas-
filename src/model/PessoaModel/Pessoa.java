package model.PessoaModel;

public abstract class Pessoa {
    private String nome;
    private String email;
    private String senha;
    private Permissao permissao;

    public Pessoa() {
    }

    public Pessoa(String nome, String email, String senha, Permissao permissao) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.permissao = permissao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }

    public boolean isAdministrador(){
        return this.permissao == Permissao.ADMINISTRADOR;
    }

    public boolean autenticar(String email, String senha){
        return this.email.equals(email) && this.senha.equals(senha);
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", permissao=" + permissao +
                '}';
    }
}
