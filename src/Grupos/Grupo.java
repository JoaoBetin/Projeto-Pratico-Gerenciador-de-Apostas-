package Grupos;

import Usuarios.Usuario;

import java.util.ArrayList;

public class Grupo {
    private String nomeGrupo;
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private final int limite = 5;

    public Grupo() {
    }

    public Grupo(String nomeGrupo, ArrayList<Usuario> usuarios) {
        this.nomeGrupo = nomeGrupo;
        this.usuarios = usuarios;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public int getLimite() {
        return limite;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "nomeGrupo='" + nomeGrupo + '\'' +
                ", usuarios=" + usuarios +
                ", limite=" + limite +
                '}';
    }
}
