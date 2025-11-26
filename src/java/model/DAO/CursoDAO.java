package model.DAO;

import config.ConectaDB;
import model.Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    // LISTAR TODOS
    public List<Curso> listar() {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT * FROM curso";

        try (Connection con = ConectaDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Curso c = new Curso();
                int id;
                try { id = rs.getInt("idCurso"); } catch (SQLException ex) { id = rs.getInt("id"); }
                c.setIdCurso(id);
                c.setNome(rs.getString("nome"));
                try { c.setCargaHoraria(rs.getInt("cargaHoraria")); } catch (SQLException ex) { c.setCargaHoraria(0); }
                try { c.setMaterias(rs.getString("materias")); } catch (SQLException ex) { c.setMaterias(null); }
                lista.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // BUSCAR POR ID
    public Curso buscar(int id) {
        Curso c = null;
        String idCol = getIdColumn();
        String sql = "SELECT * FROM curso WHERE " + idCol + " = ?";

        try (Connection con = ConectaDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c = new Curso();
                int idRead;
                try { idRead = rs.getInt("idCurso"); } catch (SQLException ex) { idRead = rs.getInt("id"); }
                c.setIdCurso(idRead);
                c.setNome(rs.getString("nome"));
                try { c.setCargaHoraria(rs.getInt("cargaHoraria")); } catch (SQLException ex) { c.setCargaHoraria(0); }
                try { c.setMaterias(rs.getString("materias")); } catch (SQLException ex) { c.setMaterias(null); }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    // INSERIR
    public boolean inserir(Curso curso) {
        boolean temCarga = verificarColunaExiste("curso", "cargaHoraria");
        boolean temMaterias = verificarColunaExiste("curso", "materias");

        String sql;
        try (Connection con = ConectaDB.conectar()) {
            if (temCarga && temMaterias) {
                sql = "INSERT INTO curso (nome, cargaHoraria, materias) VALUES (?,?,?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, curso.getNome());
                    ps.setInt(2, curso.getCargaHoraria());
                    if (curso.getMaterias() != null && !curso.getMaterias().trim().isEmpty()) {
                        ps.setString(3, curso.getMaterias());
                    } else {
                        ps.setNull(3, java.sql.Types.LONGVARCHAR);
                    }
                    ps.executeUpdate();
                    return true;
                }
            } else if (temCarga) {
                sql = "INSERT INTO curso (nome, cargaHoraria) VALUES (?,?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, curso.getNome());
                    ps.setInt(2, curso.getCargaHoraria());
                    ps.executeUpdate();
                    return true;
                }
            } else {
                sql = "INSERT INTO curso (nome) VALUES (?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, curso.getNome());
                    ps.executeUpdate();
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ATUALIZAR
    public boolean atualizar(Curso curso) {
        String idCol = getIdColumn();
        boolean temCarga = verificarColunaExiste("curso", "cargaHoraria");
        boolean temMaterias = verificarColunaExiste("curso", "materias");

        StringBuilder sb = new StringBuilder("UPDATE curso SET nome = ?");
        if (temCarga) sb.append(", cargaHoraria = ?");
        if (temMaterias) sb.append(", materias = ?");
        sb.append(" WHERE ").append(idCol).append(" = ?");

        try (Connection con = ConectaDB.conectar();
             PreparedStatement ps = con.prepareStatement(sb.toString())) {

            int idx = 1;
            ps.setString(idx++, curso.getNome());
            if (temCarga) ps.setInt(idx++, curso.getCargaHoraria());
            if (temMaterias) {
                if (curso.getMaterias() != null && !curso.getMaterias().trim().isEmpty()) {
                    ps.setString(idx++, curso.getMaterias());
                } else {
                    ps.setNull(idx++, java.sql.Types.LONGVARCHAR);
                }
            }
            ps.setInt(idx++, curso.getIdCurso());
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // EXCLUIR
    public boolean excluir(int id) {
        String idCol = getIdColumn();
        String sql = "DELETE FROM curso WHERE " + idCol + " = ?";

        try (Connection con = ConectaDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helpers para compatibilidade com esquemas diferentes
    private String getIdColumn() {
        return verificarColunaExiste("curso", "idCurso") ? "idCurso" : "id";
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
