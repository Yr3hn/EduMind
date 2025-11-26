package model.DAO;

import config.ConectaDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;
import model.Util;

public class UsuarioDAO {

    /**
     * Cadastra um novo usuário, adaptando-se às colunas existentes (nome/nomeCompleto, idCurso, idTurma).
     * @param u O objeto Usuario a ser cadastrado.
     * @return true se o cadastro foi bem-sucedido.
     */
    public boolean cadastrar(Usuario u){
        boolean temIdCurso = verificarColunaExiste("usuario", "idCurso");
        boolean temIdTurma = verificarColunaExiste("usuario", "idTurma");
        boolean temNomeCompleto = verificarColunaExiste("usuario", "nomeCompleto");

        String sql;
        PreparedStatement stmt = null;
        
        try {
            Connection conn = ConectaDB.conectar();

            if(temIdCurso && temIdTurma) {
                // Banco tem ambas as colunas
                sql = temNomeCompleto
                    ? "INSERT INTO usuario (nomeCompleto, email, rgm, senha, tipo, idCurso, idTurma) VALUES (?,?,?,?,?,?,?)"
                    : "INSERT INTO usuario (nome, email, rgm, senha, tipo, idCurso, idTurma) VALUES (?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);

                stmt.setString(1, u.getNome());
                stmt.setString(2, u.getEmail());
                stmt.setString(3, u.getRgm());
                stmt.setString(4, Util.md5(u.getSenha()));
                stmt.setString(5, u.getTipo());
                if(u.getIdCurso() > 0) {
                    stmt.setInt(6, u.getIdCurso());
                } else {
                    stmt.setNull(6, java.sql.Types.INTEGER);
                }
                if(u.getIdTurma() > 0) {
                    stmt.setInt(7, u.getIdTurma());
                } else {
                    stmt.setNull(7, java.sql.Types.INTEGER);
                }
            } else {
                // Banco não tem idCurso e idTurma
                sql = temNomeCompleto
                ? "INSERT INTO usuario (nomeCompleto, email, rgm, senha, tipo) VALUES (?,?,?,?,?)"
                : "INSERT INTO usuario (nome, email, rgm, senha, tipo) VALUES (?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);
                
                stmt.setString(1, u.getNome());
                stmt.setString(2, u.getEmail());
                stmt.setString(3, u.getRgm());
                stmt.setString(4, Util.md5(u.getSenha()));
                stmt.setString(5, u.getTipo());
            }

            stmt.executeUpdate();

            stmt.close();
            conn.close();

            return true;

        } catch (SQLException e){
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifica se uma coluna existe em uma tabela específica.
     */
    private boolean verificarColunaExiste(String tabela, String coluna) {
        try {
            Connection conn = ConectaDB.conectar();
            DatabaseMetaData meta = conn.getMetaData();
            // A tabela pode ser case-sensitive, dependendo do SGBD/Configuração. Usamos toLowerCase() aqui.
            ResultSet rs = meta.getColumns(null, null, tabela.toLowerCase(), coluna); 
            boolean existe = rs.next();
            rs.close();
            conn.close();
            return existe;
        } catch (SQLException e) {
            // Se der erro, assume que não existe para evitar falhas de runtime.
            return false;
        }
    }

    /**
     * Tenta autenticar um usuário usando RGM e senha (texto plano ou MD5).
     */
    public Usuario autenticar(String rgm, String senha){
        Usuario u = null; 
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        ResultSet rs = null;

        try {
            Connection conn = ConectaDB.conectar();

            // 1. Tenta com senha em texto plano
            String sql1 = "SELECT * FROM usuario WHERE rgm = ? AND senha = ?";
            stmt1 = conn.prepareStatement(sql1);
            stmt1.setString(1, rgm);
            stmt1.setString(2, senha); 

            rs = stmt1.executeQuery();

            if(rs.next()){
                u = criarUsuarioDoResultSet(rs);
            } else {
                // 2. Se não encontrou, tenta com senha hashada MD5
                if(rs != null) rs.close();
                if(stmt1 != null) stmt1.close();

                String sql2 = "SELECT * FROM usuario WHERE rgm = ? AND senha = ?";
                stmt2 = conn.prepareStatement(sql2);
                stmt2.setString(1, rgm);
                stmt2.setString(2, Util.md5(senha)); 

                rs = stmt2.executeQuery();

                if(rs.next()){
                    u = criarUsuarioDoResultSet(rs);
                }
                if(stmt2 != null) stmt2.close();
            }

            if(rs != null && !rs.isClosed()) rs.close();
            if(stmt1 != null && !stmt1.isClosed()) stmt1.close();
            conn.close();

        } catch (SQLException e){
            System.out.println("Erro ao autenticar usuário: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Erro geral ao autenticar: " + e.getMessage());
            e.printStackTrace();
        }

        return u;
    }

    /**
     * Mapeia os dados do ResultSet para um objeto Usuario.
     */
    private Usuario criarUsuarioDoResultSet(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        
        // ID (id ou idUser)
        try {
            u.setIdUser(rs.getInt("id"));
        } catch (SQLException e) {
            try {
                u.setIdUser(rs.getInt("idUser"));
            } catch (SQLException e2) {
                u.setIdUser(0);
            }
        }

        // NOME (nomeCompleto ou nome)
        String nomeCompleto = null;
        try {
            nomeCompleto = rs.getString("nomeCompleto");
        } catch (SQLException e) { /* Ignora se coluna não existir */ }

        if(nomeCompleto != null && !nomeCompleto.trim().isEmpty()) {
            u.setNome(nomeCompleto);
        } else {
            try {
                u.setNome(rs.getString("nome"));
            } catch (SQLException e) {
                u.setNome(""); 
            }
        }

        u.setEmail(rs.getString("email"));
        u.setRgm(rs.getString("rgm"));
        u.setSenha(rs.getString("senha"));

        // Tipo
        String tipo = rs.getString("tipo");
        if(tipo != null && !tipo.trim().isEmpty()) {
            u.setTipo(tipo.toLowerCase().trim());
        } else {
            u.setTipo("");
        }

        // idCurso
        try {
            int idCurso = rs.getInt("idCurso");
            u.setIdCurso(rs.wasNull() ? 0 : idCurso);
        } catch (SQLException e) { u.setIdCurso(0); }

        // idTurma
        try {
            int idTurma = rs.getInt("idTurma");
            u.setIdTurma(rs.wasNull() ? 0 : idTurma);
        } catch (SQLException e) { u.setIdTurma(0); }
        
        // *** NOVOS CAMPOS DE PERFIL (Para evitar duplicação no buscar) ***
        // Telefones (adaptação para colunas existentes)
        try { u.setTelefone(rs.getString("telefone")); } catch (SQLException e) { u.setTelefone(null); }
        
        // Endereço
        try { u.setEndereco(rs.getString("endereco")); } catch (SQLException e) { u.setEndereco(null); }
        
        // Foto
        try { 
            // Tenta fotoCaminho, se falhar, tenta foto_caminho
            String foto = rs.getString("fotoCaminho");
            if (foto == null || foto.isEmpty()) foto = rs.getString("foto_caminho"); 
            u.setFotoCaminho(foto);
        } catch (SQLException e) { u.setFotoCaminho(null); }
        // ***************************************************************

        return u;
    }

    /**
     * Lista usuários, opcionalmente filtrando por tipo.
     */
    public List<Usuario> listar(String tipo) {
        List<Usuario> lista = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Connection conn = ConectaDB.conectar();
            
            // Escolhe coluna de ordenação conforme existência no banco
            boolean temNomeCompleto = verificarColunaExiste("usuario", "nomeCompleto");
            boolean temNome = verificarColunaExiste("usuario", "nome");
            String colunaOrdenacao;
            if (temNomeCompleto) {
                colunaOrdenacao = "nomeCompleto";
            } else if (temNome) {
                colunaOrdenacao = "nome";
            } else {
                colunaOrdenacao = "id"; // fallback seguro
            }

            String sql;
            if (tipo != null && !tipo.trim().isEmpty() && !"todos".equalsIgnoreCase(tipo)) {
                sql = "SELECT * FROM usuario WHERE LOWER(tipo) = ? ORDER BY " + colunaOrdenacao;
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, tipo.toLowerCase());
            } else {
                sql = "SELECT * FROM usuario ORDER BY " + colunaOrdenacao;
                stmt = conn.prepareStatement(sql);
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(criarUsuarioDoResultSet(rs));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Busca um usuário pelo ID e carrega todos os campos (incluindo perfil).
     * Mapeia os dados do ResultSet para um objeto Usuario usando criarUsuarioDoResultSet.
     * @param id O ID do usuário (id ou idUser).
     * @return Objeto Usuario com os dados.
     */
    public Usuario buscar(int id){
        Usuario u = null;
        try{
            Connection conn = ConectaDB.conectar();
            String idCol = verificarColunaExiste("usuario","id") ? "id" : "idUser";
            
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM usuario WHERE "+idCol+"=?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                u = criarUsuarioDoResultSet(rs);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch(SQLException e){
            System.out.println("Erro ao buscar usuário: "+e.getMessage());
            e.printStackTrace();
        }
        return u;
    }
    
    /**
     * NOVO: Atualiza apenas os dados pessoais do perfil (nome, email, telefone, endereco).
     * Adapta-se às colunas existentes.
     */
    public boolean atualizarPerfil(Usuario u){
        try{
            Connection conn = ConectaDB.conectar();
            boolean temNomeCompleto = verificarColunaExiste("usuario","nomeCompleto");
            boolean temTelefone = verificarColunaExiste("usuario","telefone");
            boolean temEndereco = verificarColunaExiste("usuario","endereco");
            String idCol = verificarColunaExiste("usuario","id") ? "id" : "idUser";

            StringBuilder sql = new StringBuilder("UPDATE usuario SET ");
            
            // Nome e Email
            sql.append(temNomeCompleto ? "nomeCompleto=?" : "nome=?");
            sql.append(", email=?");
            
            // Campos de Perfil
            if(temTelefone) sql.append(", telefone=?");
            if(temEndereco) sql.append(", endereco=?");
            
            sql.append(" WHERE ").append(idCol).append("=?");

            PreparedStatement ps = conn.prepareStatement(sql.toString());
            int idx = 1;
            
            // Valores
            ps.setString(idx++, u.getNome());
            ps.setString(idx++, u.getEmail());
            
            if(temTelefone) ps.setString(idx++, u.getTelefone());
            if(temEndereco) ps.setString(idx++, u.getEndereco());
            
            // Condição WHERE
            ps.setInt(idx++, u.getIdUser());
            
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch(SQLException e){
            System.out.println("Erro ao atualizar perfil: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * NOVO: Atualiza apenas o caminho da foto de perfil.
     * Requer que a coluna 'fotoCaminho' ou 'foto_caminho' exista.
     */
    public boolean atualizarFoto(int id, String novoCaminho){
        try{
            Connection conn = ConectaDB.conectar();
            // Verifica a coluna mais provável
            String fotoCol = verificarColunaExiste("usuario","fotoCaminho") ? "fotoCaminho" : "foto_caminho";
            String idCol = verificarColunaExiste("usuario","id") ? "id" : "idUser";

            if (!verificarColunaExiste("usuario", fotoCol)) {
                System.out.println("ERRO: Coluna de foto não existe. Adicione 'fotoCaminho' ou 'foto_caminho' na tabela.");
                return false;
            }
            
            String sql = "UPDATE usuario SET " + fotoCol + "=? WHERE " + idCol + "=?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, novoCaminho);
            ps.setInt(2, id);
            
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch(SQLException e){
            System.out.println("Erro ao atualizar foto: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * NOVO: Atualiza apenas a senha do usuário.
     */
    public boolean atualizarSenha(int id, String novaSenhaCriptografada){
        try{
            Connection conn = ConectaDB.conectar();
            String idCol = verificarColunaExiste("usuario","id") ? "id" : "idUser";
            String sql = "UPDATE usuario SET senha=? WHERE " + idCol + "=?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, novaSenhaCriptografada); // JÁ DEVE ESTAR EM MD5
            ps.setInt(2, id);
            
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch(SQLException e){
            System.out.println("Erro ao atualizar senha: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Atualiza todos os dados de um usuário (usado tipicamente em telas de administração).
     */
    public boolean atualizar(Usuario u){
        try{
            Connection conn = ConectaDB.conectar();
            boolean temIdCurso = verificarColunaExiste("usuario","idCurso");
            boolean temIdTurma = verificarColunaExiste("usuario","idTurma");
            boolean temNomeCompleto = verificarColunaExiste("usuario","nomeCompleto");
            String idCol = verificarColunaExiste("usuario","id") ? "id" : "idUser";
            
            // Verifica campos de perfil se necessário, mas o 'atualizarPerfil' é o método dedicado.
            // Aqui, focamos nos campos principais:
            StringBuilder sql = new StringBuilder("UPDATE usuario SET ");
            if(temNomeCompleto){
                sql.append("nomeCompleto=?");
            } else {
                sql.append("nome=?");
            }
            sql.append(", email=?, rgm=?, senha=?, tipo=?");
            if(temIdCurso) sql.append(", idCurso=?");
            if(temIdTurma) sql.append(", idTurma=?");
            sql.append(" WHERE ").append(idCol).append("=?");

            PreparedStatement ps = conn.prepareStatement(sql.toString());
            int idx = 1;
            ps.setString(idx++, u.getNome());
            ps.setString(idx++, u.getEmail());
            ps.setString(idx++, u.getRgm());
            ps.setString(idx++, Util.md5(u.getSenha()));
            ps.setString(idx++, u.getTipo());
            if(temIdCurso){
                if(u.getIdCurso()>0) ps.setInt(idx++, u.getIdCurso()); else ps.setNull(idx++, java.sql.Types.INTEGER);
            }
            if(temIdTurma){
                if(u.getIdTurma()>0) ps.setInt(idx++, u.getIdTurma()); else ps.setNull(idx++, java.sql.Types.INTEGER);
            }
            ps.setInt(idx++, u.getIdUser());
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch(SQLException e){
            System.out.println("Erro ao atualizar usuário: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Exclui um usuário pelo ID.
     */
    public boolean excluir(int id){
        try{
            Connection conn = ConectaDB.conectar();
            String idCol = verificarColunaExiste("usuario","id") ? "id" : "idUser";
            PreparedStatement ps = conn.prepareStatement("DELETE FROM usuario WHERE "+idCol+"=?");
            ps.setInt(1,id);
            ps.executeUpdate();
            ps.close();
            conn.close();
            return true;
        } catch(SQLException e){
            System.out.println("Erro ao excluir usuário: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}