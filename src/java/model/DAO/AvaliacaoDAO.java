package model.DAO;

import config.ConectaDB;
import model.Avaliacao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {

    public boolean lancarPorAlunoTurma(int idAluno, int idTurma, String materia, float nota, int faltas) {
        Integer idAlunoTurma = obterIdAlunoTurma(idAluno, idTurma);
        if (idAlunoTurma == null) return false;

        String sql = "INSERT INTO avaliacao (idAlunoTurma, atividade, nota, faltas) VALUES (?,?,?,?)";
        try (Connection con = ConectaDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAlunoTurma);
            ps.setString(2, materia);
            ps.setFloat(3, nota);
            ps.setInt(4, faltas);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Avaliacao> listarPorAlunoTurma(int idAluno, int idTurma) {
        List<Avaliacao> lista = new ArrayList<>();
        Integer idAlunoTurma = obterIdAlunoTurma(idAluno, idTurma);
        if (idAlunoTurma == null) return lista;

        String sql = "SELECT * FROM avaliacao WHERE idAlunoTurma = ?";
        try (Connection con = ConectaDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAlunoTurma);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Avaliacao a = new Avaliacao();
                try { a.setIdAvaliacao(rs.getInt("idAvaliacao")); } catch (SQLException ex) { /* opcional */ }
                try { a.setIdAlunoTurma(rs.getInt("idAlunoTurma")); } catch (SQLException ex) { a.setIdAlunoTurma(idAlunoTurma); }
                try { a.setNota(rs.getFloat("nota")); } catch (SQLException ex) { a.setNota(0f); }
                try { a.setFaltas(rs.getInt("faltas")); } catch (SQLException ex) { a.setFaltas(0); }
                try { a.setAtividade(rs.getString("atividade")); } catch (SQLException ex) { a.setAtividade(null); }
                lista.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    private Integer obterIdAlunoTurma(int idAluno, int idTurma) {
        String sql = "SELECT id FROM turmaaluno WHERE idAluno=? AND idTurma=?";
        try (Connection con = ConectaDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAluno);
            ps.setInt(2, idTurma);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}