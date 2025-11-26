package model;

public class Turma {
    private int idTurma;
    private String nome;
    private int idCurso;
    private String linkAulaOnline;

    public int getIdTurma() { return idTurma; }
    public void setIdTurma(int idTurma) { this.idTurma = idTurma; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }

    public String getLinkAulaOnline() { return linkAulaOnline; }
    public void setLinkAulaOnline(String linkAulaOnline) { this.linkAulaOnline = linkAulaOnline; }
}
