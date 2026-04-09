package model;

public class Grupo {
    private String nome;
    private Participante[] participantes;
    private int totalParticipantes;

    public Grupo() {
        this.nome = "";
        this.participantes = new Participante[5];
        this.totalParticipantes = 0;
    }

    public Grupo(String nome) {
        this.nome = nome;
        this.participantes = new Participante[5];
        this.totalParticipantes = 0;
    }

    public boolean adicionarParticipante(Participante p) {
        if (totalParticipantes >= 5) return false;
        for (int i = 0; i < totalParticipantes; i++) {
            if (participantes[i].getEmail().equals(p.getEmail())) return false;
        }
        participantes[totalParticipantes++] = p;
        return true;
    }

    public Participante[] getParticipantes() {
        Participante[] resultado = new Participante[totalParticipantes];
        for (int i = 0; i < totalParticipantes; i++) {
            resultado[i] = participantes[i];
        }
        return resultado;
    }

    public String getNome()           { return nome; }
    public void setNome(String nome)  { this.nome = nome; }
    public int getTotalParticipantes(){ return totalParticipantes; }
    public boolean estaLotado()       { return totalParticipantes >= 5; }

    @Override
    public String toString() {
        return "Grupo: " + nome + " (" + totalParticipantes + "/5 participantes)";
    }
}
