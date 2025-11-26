package model.DAO;

import config.ConectaDB;
import model.Turma;
import java.sql.*;
import java.util.*;

public class TurmaDAO {

    public List<Turma> listar() {
        List<Turma> lista = new ArrayList<>();
        String sql = "SELECT * FROM turma";

        try(Connection con = ConectaDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                Turma t = new Turma();
                int id;
                try { id = rs.getInt("idTurma"); } catch (SQLException ex) { id = rs.getInt("id"); }
                t.setIdTurma(id);
                t.setNome(rs.getString("nome"));
                t.setIdCurso(rs.getInt("idCurso"));
                t.setLinkAulaOnline(rs.getString("linkAulaOnline"));
                lista.add(t);
            }

        } catch(Exception e){ e.printStackTrace(); }

        return lista;
    }

    public Turma buscar(int id){
        Turma t = null;
        String idCol = getIdColumn();
        String sql = "SELECT * FROM turma WHERE " + idCol + " = ?";

        try(Connection con = ConectaDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                t = new Turma();
                int idRead;
                try { idRead = rs.getInt("idTurma"); } catch (SQLException ex) { idRead = rs.getInt("id"); }
                t.setIdTurma(idRead);
                t.setNome(rs.getString("nome"));
                t.setIdCurso(rs.getInt("idCurso"));
                t.setLinkAulaOnline(rs.getString("linkAulaOnline"));
            }

        } catch(Exception e){ e.printStackTrace(); }

        return t;
    }

    public boolean inserir(Turma t){
        if(t.getIdCurso() <= 0) {
            String sql = "INSERT INTO turma (nome, idCurso, linkAulaOnline) VALUES(?, NULL, ?)";
            try(Connection con = ConectaDB.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1,t.getNome());
                ps.setString(2,t.getLinkAulaOnline());
                ps.executeUpdate();
                return true;
            } catch(Exception e){ 
                System.out.println("Erro ao inserir turma: " + e.getMessage());
                e.printStackTrace(); 
                return false; 
            }
        } else {
            String sql = "INSERT INTO turma (nome, idCurso, linkAulaOnline) VALUES(?,?,?)";
            try(Connection con = ConectaDB.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1,t.getNome());
                ps.setInt(2,t.getIdCurso());
                ps.setString(3,t.getLinkAulaOnline());
                ps.executeUpdate();
                return true;
            } catch(Exception e){ 
                System.out.println("Erro ao inserir turma: " + e.getMessage());
                e.printStackTrace(); 
                return false; 
            }
        }
    }

    public boolean atualizar(Turma t){
        String idCol = getIdColumn();
        if(t.getIdCurso() <= 0) {
            String sql = "UPDATE turma SET nome=?, idCurso=NULL, linkAulaOnline=? WHERE " + idCol + "=?";
            try(Connection con = ConectaDB.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1,t.getNome());
                ps.setString(2,t.getLinkAulaOnline());
                ps.setInt(3,t.getIdTurma());
                ps.executeUpdate();
                return true;
            } catch(Exception e){ 
                System.out.println("Erro ao atualizar turma: " + e.getMessage());
                e.printStackTrace(); 
                return false; 
            }
        } else {
            String sql = "UPDATE turma SET nome=?, idCurso=?, linkAulaOnline=? WHERE " + idCol + "=?";
            try(Connection con = ConectaDB.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1,t.getNome());
                ps.setInt(2,t.getIdCurso());
                ps.setString(3,t.getLinkAulaOnline());
                ps.setInt(4,t.getIdTurma());
                ps.executeUpdate();
                return true;
            } catch(Exception e){ 
                System.out.println("Erro ao atualizar turma: " + e.getMessage());
                e.printStackTrace(); 
                return false; 
            }
        }
    }

    public boolean excluir(int id){
        String idCol = getIdColumn();
        try(Connection con = ConectaDB.conectar();
            PreparedStatement ps = con.prepareStatement("DELETE FROM turma WHERE " + idCol + "=?")) {

            ps.setInt(1,id);
            ps.executeUpdate();
            return true;

        } catch(Exception e){ e.printStackTrace(); return false; }
    }

    private String getIdColumn() {
        return verificarColunaExiste("turma", "idTurma") ? "idTurma" : "id";
    }

    private boolean verificarColunaExiste(String tabela, String coluna) {
        try {
            Connection con = ConectaDB.conectar();
            DatabaseMetaData meta = con.getMetaData();
            ResultSet rs = meta.getColumns(null, null, tabela, coluna);
            boolean existe = rs.next();
            rs.close();
            con.close();
            return existe;
        } catch (SQLException e) {
            return false;
        }
    }
}
