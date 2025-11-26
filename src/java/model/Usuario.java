package model;

public class Usuario {
    private int idUser; // Mapeia para 'id' na tabela
    private String nome; // Mapeia para 'nomeCompleto' na tabela
    private String email;
    private String rgm;
    private String senha;
    private String tipo;
    private int idCurso;
    private int idTurma;
    
    // CAMPOS QUE PRECISAM SER ADICIONADOS AO MODELO
    private String telefone; 
    private String endereco;
    private String fotoCaminho; 

    // Métodos Getters e Setters
    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

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
    
    // NOVOS GETTERS E SETTERS (Mantenha estes)
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getFotoCaminho() { return fotoCaminho; }
    public void setFotoCaminho(String fotoCaminho) { this.fotoCaminho = fotoCaminho; }
}