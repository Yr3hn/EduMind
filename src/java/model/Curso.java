package model;

public class Curso {
    private int idCurso;
    private String nome;
    private int cargaHoraria;
    private String materias;

    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }
    public String getMaterias() { return materias; }
    public void setMaterias(String materias) { this.materias = materias; }
}
