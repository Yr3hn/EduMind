package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;

import java.util.ArrayList;
import java.util.List;
import config.ConectaDB;
import model.Usuario;
import model.Util;

public class UsuarioDAO {

    /**
     * Tenta cadastrar um novo usuário (Professor/Aluno) no banco de dados.
     * @param usuario O objeto Usuario a ser cadastrado.
     * @return true se o cadastro foi bem-sucedido, false caso contrário.
     */
    public boolean cadastrar(Usuario usuario) {
        // SQL para inserir os dados do novo usuário na tabela.
        // ATENÇÃO: Incluí o campo `rgm` (Linha 3) que é NOT NULL na sua tabela.
        String sql = "INSERT INTO usuario (nomeCompleto, rgm, email, senha, tipo) VALUES (?, ?, ?, ?, ?)";
        
        // Use try-with-resources para garantir que a conexão seja fechada
        try (Connection conexao = ConectaDB.conectar(); // Presumindo que ConexaoDAO.getConexao() retorna a conexão
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            
            // 1. Preenche os parâmetros da SQL
            stmt.setString(1, usuario.getNomeCompleto());
            stmt.setString(2, usuario.getRgm());     // <--- ESSENCIAL: O RGM é NOT NULL
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getSenha());
            stmt.setString(5, usuario.getTipo());

            // 2. Executa a inserção
            int rowsAffected = stmt.executeUpdate();

            // 3. Retorna true se pelo menos uma linha foi inserida
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            // Em caso de erro (ex: e-mail ou RGM duplicado se forem UNIQUE), imprime e retorna false
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public boolean inserir(Usuario u) {
    // Nota: O campo 'senha' do objeto Usuario JÁ DEVE ESTAR CRIPTOGRAFADO (MD5) 
    // antes de ser passado para este método.
    
    // id é auto-incrementado, idCurso e idTurma são opcionais (podem ser null/0)
    String sql = "INSERT INTO usuario (nomeCompleto, rgm, email, tipo, senha) VALUES (?, ?, ?, ?, ?)";
    
    // Verifica se o RGM ou Email já existe, se desejar:
    // if (buscarPorRGM(u.getRgm()) != null || buscarPorEmail(u.getEmail()) != null) return false;

    try (Connection conn = ConectaDB.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, u.getNomeCompleto());
        stmt.setString(2, u.getRgm());
        stmt.setString(3, u.getEmail());
        stmt.setString(4, u.getTipo());
        stmt.setString(5, u.getSenha()); // Senha (já MD5)
        
        int linhasAfetadas = stmt.executeUpdate();
        return linhasAfetadas > 0;

    } catch (SQLException e) {
        // Se houver uma exceção (ex: violação de UNIQUE key em email ou rgm), retorna false
        e.printStackTrace();
        return false;
    }
}
    private boolean verificarColunaExiste(String tabela, String coluna) {
        try (Connection conn = ConectaDB.conectar()) {
            DatabaseMetaData meta = conn.getMetaData();

            try (ResultSet rs = meta.getColumns(null, null, tabela, coluna)) {
                if (rs.next()) return true;
            }

            try (ResultSet rs2 = meta.getColumns(null, null, tabela, coluna.toUpperCase())) {
                return rs2.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Usuario criarUsuarioDoResultSet(
            ResultSet rs,
            boolean temIdCurso,
            boolean temIdTurma,
            boolean temTelefone,
            boolean temFoto) throws SQLException {

        Usuario u = new Usuario();

        u.setId(rs.getInt("id"));
        u.setNomeCompleto(rs.getString("nomeCompleto"));
        u.setRgm(rs.getString("rgm"));
        u.setEmail(rs.getString("email"));
        u.setTipo(rs.getString("tipo"));

        String senhaBD = rs.getString("senha");
        if (senhaBD != null && senhaBD.length() == 32) {
            u.setSenha("HASH_MD5");
        } else {
            u.setSenha(senhaBD);
        }

        if (temIdCurso) u.setIdCurso(rs.getInt("idCurso"));
        if (temIdTurma) u.setIdTurma(rs.getInt("idTurma"));
        if (temTelefone) u.setTelefone(rs.getString("telefone"));
        if (temFoto) u.setFoto(rs.getString("foto"));

        return u;
    }

    public Usuario autenticar(String rgm, String senhaDigitada) {
        String sql = "SELECT * FROM usuario WHERE rgm = ? AND senha = ?";

        try (Connection conn = ConectaDB.conectar()) {

            boolean temIdCurso = verificarColunaExiste("usuario", "idCurso");
            boolean temIdTurma = verificarColunaExiste("usuario", "idTurma");
            boolean temTelefone = verificarColunaExiste("usuario", "telefone");
            boolean temFoto = verificarColunaExiste("usuario", "foto");

            // 1️⃣ Tentar senha normal (texto plano)
            try (PreparedStatement stmt1 = conn.prepareStatement(sql)) {
                stmt1.setString(1, rgm);
                stmt1.setString(2, senhaDigitada);

                try (ResultSet rs1 = stmt1.executeQuery()) {
                    if (rs1.next()) {
                        return criarUsuarioDoResultSet(rs1, temIdCurso, temIdTurma, temTelefone, temFoto);
                    }
                }
            }

            // 2️⃣ Tentar versão MD5
            String senhaMD5 = Util.md5(senhaDigitada);

            try (PreparedStatement stmt2 = conn.prepareStatement(sql)) {
                stmt2.setString(1, rgm);
                stmt2.setString(2, senhaMD5);

                try (ResultSet rs2 = stmt2.executeQuery()) {
                    if (rs2.next()) {
                        return criarUsuarioDoResultSet(rs2, temIdCurso, temIdTurma, temTelefone, temFoto);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Login falhou
    }
    
    // --- NOVO MÉTODO ADICIONADO ABAIXO ---
    
    /**
     * Autentica um usuário pelo email e senha, verificando texto plano e MD5.
     * Necessário para o login exclusivo do Administrador.
     */
    public Usuario autenticarPorEmail(String email, String senhaDigitada) {
        // A query SQL muda para buscar pelo campo 'email'
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";

        try (Connection conn = ConectaDB.conectar()) {

            boolean temIdCurso = verificarColunaExiste("usuario", "idCurso");
            boolean temIdTurma = verificarColunaExiste("usuario", "idTurma");
            boolean temTelefone = verificarColunaExiste("usuario", "telefone");
            boolean temFoto = verificarColunaExiste("usuario", "foto");

            // 1️⃣ Tentar senha normal (texto plano)
            try (PreparedStatement stmt1 = conn.prepareStatement(sql)) {
                stmt1.setString(1, email); // Usa o 'email'
                stmt1.setString(2, senhaDigitada);

                try (ResultSet rs1 = stmt1.executeQuery()) {
                    if (rs1.next()) {
                        return criarUsuarioDoResultSet(rs1, temIdCurso, temIdTurma, temTelefone, temFoto);
                    }
                }
            }

            // 2️⃣ Tentar versão MD5
            String senhaMD5 = Util.md5(senhaDigitada);

            try (PreparedStatement stmt2 = conn.prepareStatement(sql)) {
                stmt2.setString(1, email); // Usa o 'email'
                stmt2.setString(2, senhaMD5);

                try (ResultSet rs2 = stmt2.executeQuery()) {
                    if (rs2.next()) {
                        return criarUsuarioDoResultSet(rs2, temIdCurso, temIdTurma, temTelefone, temFoto);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Login falhou
    }
}