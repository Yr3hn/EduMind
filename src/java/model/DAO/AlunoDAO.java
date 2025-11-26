package model.DAO;

import java.sql.*;
import java.util.*;
import config.ConectaDB;
import model.Aluno;

public class AlunoDAO {

    public List<Aluno> listar() {
        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM aluno";

        try(Connection con = ConectaDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                Aluno a = new Aluno();
                a.setIdAluno(rs.getInt("idAluno"));
                a.setNome(rs.getString("nome"));
                a.setRgm(rs.getString("rgm"));
                a.setSenha(rs.getString("senha"));
                a.setIdCurso(rs.getInt("idCurso"));
                a.setIdTurma(rs.getInt("idTurma"));
                lista.add(a);
            }

        } catch(Exception e) { e.printStackTrace(); }

        return lista;
    }

    public Aluno buscar(int id){
        Aluno a = null;

        try(Connection con = ConectaDB.conectar();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM aluno WHERE idAluno=?")) {

            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                a = new Aluno();
                a.setIdAluno(rs.getInt("idAluno"));
                a.setNome(rs.getString("nome"));
                a.setRgm(rs.getString("rgm"));
                a.setSenha(rs.getString("senha"));
                a.setIdCurso(rs.getInt("idCurso"));
                a.setIdTurma(rs.getInt("idTurma"));
            }

        } catch(Exception e) { e.printStackTrace(); }

        return a;
    }

    public boolean inserir(Aluno a) {
        try(Connection con = ConectaDB.conectar();
            PreparedStatement ps = con.prepareStatement("INSERT INTO aluno VALUES(NULL,?,?,?,?,?)")) {

            ps.setString(1,a.getNome());
            ps.setString(2,a.getRgm());
            ps.setString(3,a.getSenha());
            ps.setInt(4,a.getIdCurso());
            ps.setInt(5,a.getIdTurma());
            ps.executeUpdate();
            return true;

        } catch(Exception e) { e.printStackTrace(); return false; }
    }

    public boolean atualizar(Aluno a) {
        try(Connection con = ConectaDB.conectar();
            PreparedStatement ps = con.prepareStatement("UPDATE aluno SET nome=?, rgm=?, senha=?, idCurso=?, idTurma=? WHERE idAluno=?")) {

            ps.setString(1,a.getNome());
            ps.setString(2,a.getRgm());
            ps.setString(3,a.getSenha());
            ps.setInt(4,a.getIdCurso());
            ps.setInt(5,a.getIdTurma());
            ps.setInt(6,a.getIdAluno());
            ps.executeUpdate();
            return true;

        } catch(Exception e) { e.printStackTrace(); return false; }
    }

    public boolean excluir(int id){
        try(Connection con = ConectaDB.conectar();
            PreparedStatement ps = con.prepareStatement("DELETE FROM aluno WHERE idAluno=?")) {

            ps.setInt(1,id);
            ps.executeUpdate();
            return true;

        } catch(Exception e) { e.printStackTrace(); return false; }
    }
}
