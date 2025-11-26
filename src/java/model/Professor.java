package model;

public class Professor {
    private int idProfessor;
    private String nome;
    private String rgm;
    private String senha;
    private int idCurso;
    private int idTurma;

    public int getIdProfessor() { return idProfessor; }
    public void setIdProfessor(int idProfessor) { this.idProfessor = idProfessor; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getRgm() { return rgm; }
    public void setRgm(String rgm) { this.rgm = rgm; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }

    public int getIdTurma() { return idTurma; }
    public void setIdTurma(int idTurma) { this.idTurma = idTurma; }
}
