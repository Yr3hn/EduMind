package model;

public class Avaliacao {
    private int idAvaliacao;
    private int idAlunoTurma;
    private float nota;
    private int faltas;
    private String atividade;

    public int getIdAvaliacao() { return idAvaliacao; }
    public void setIdAvaliacao(int idAvaliacao) { this.idAvaliacao = idAvaliacao; }

    public int getIdAlunoTurma() { return idAlunoTurma; }
    public void setIdAlunoTurma(int idAlunoTurma) { this.idAlunoTurma = idAlunoTurma; }

    public float getNota() { return nota; }
    public void setNota(float nota) { this.nota = nota; }

    public int getFaltas() { return faltas; }
    public void setFaltas(int faltas) { this.faltas = faltas; }

    public String getAtividade() { return atividade; }
    public void setAtividade(String atividade) { this.atividade = atividade; }
}
