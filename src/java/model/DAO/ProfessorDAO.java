package model.DAO;

import java.sql.*;
import java.util.*;
import config.ConectaDB;
import model.Professor;

public class ProfessorDAO {

    public List<Professor> listar() {
        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT * FROM professor";

        try(Connection con = ConectaDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                Professor p = new Professor();
                p.setIdProfessor(rs.getInt("idProfessor"));
                p.setNome(rs.getString("nome"));
                p.setRgm(rs.getString("rgm"));
                p.setSenha(rs.getString("senha"));
                p.setIdCurso(rs.getInt("idCurso"));
                p.setIdTurma(rs.getInt("idTurma"));
                lista.add(p);
            }

        } catch(Exception e) { e.printStackTrace(); }

        return lista;
    }

    public Professor buscar(int id) {
        Professor p = null;
        String sql = "SELECT * FROM professor WHERE idProfessor=?";

        try(Connection con = ConectaDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                p = new Professor();
                p.setIdProfessor(rs.getInt("idProfessor"));
                p.setNome(rs.getString("nome"));
                p.setRgm(rs.getString("rgm"));
                p.setSenha(rs.getString("senha"));
                p.setIdCurso(rs.getInt("idCurso"));
                p.setIdTurma(rs.getInt("idTurma"));
            }

        } catch(Exception e) { e.printStackTrace(); }

        return p;
    }

    public boolean inserir(Professor p) {
        String sql = "INSERT INTO professor VALUES(NULL,?,?,?,?,?)";

        try(Connection con = ConectaDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1,p.getNome());
            ps.setString(2,p.getRgm());
            ps.setString(3,p.getSenha());
            ps.setInt(4,p.getIdCurso());
            ps.setInt(5,p.getIdTurma());
            ps.executeUpdate();
            return true;

        } catch(Exception e) { e.printStackTrace(); return false; }
    }

    public boolean atualizar(Professor p) {
        String sql = "UPDATE professor SET nome=?, rgm=?, senha=?, idCurso=?, idTurma=? WHERE idProfessor=?";

        try(Connection con = ConectaDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1,p.getNome());
            ps.setString(2,p.getRgm());
            ps.setString(3,p.getSenha());
            ps.setInt(4,p.getIdCurso());
            ps.setInt(5,p.getIdTurma());
            ps.setInt(6,p.getIdProfessor());
            ps.executeUpdate();
            return true;

        } catch(Exception e) { e.printStackTrace(); return false; }
    }

    public boolean excluir(int id) {
        try(Connection con = ConectaDB.conectar();
            PreparedStatement ps = con.prepareStatement("DELETE FROM professor WHERE idProfessor=?")) {

            ps.setInt(1,id);
            ps.executeUpdate();
            return true;

        } catch(Exception e) { e.printStackTrace(); return false; }
    }
}
