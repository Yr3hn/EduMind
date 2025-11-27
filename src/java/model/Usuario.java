package model;

public class Usuario {

    // Nome correto para combinar com o DAO e com a tabela 'usuario'
    private int id; 
    private String nomeCompleto; 
    private String email;
    private String rgm;
    private String senha;
    private String tipo;
    private int idCurso;
    private int idTurma;

    // Campos adicionais opcionais
    private String telefone;
    private String endereco; 
    private String foto; // compatível com a coluna 'foto' do banco

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRgm() { return rgm; }
    public void setRgm(String rgm) { this.rgm = rgm; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }

    public int getIdTurma() { return idTurma; }
    public void setIdTurma(int idTurma) { this.idTurma = idTurma; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
}
