<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario, model.DAO.CursoDAO, model.DAO.TurmaDAO, model.Curso, model.Turma" %>

<%
    // Segurança — só professor acessa
    Usuario professor = (Usuario) session.getAttribute("usuarioLogado");
    if (professor == null || !"professor".equals(professor.getTipo().toLowerCase())) {
        response.sendRedirect("login.jsp");
        return;
    }

    CursoDAO cursoDAO = new CursoDAO();
    TurmaDAO turmaDAO = new TurmaDAO();

    Curso curso = cursoDAO.buscar(professor.getIdCurso());
    Turma turma = turmaDAO.buscar(professor.getIdTurma());
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<title>Portal do Professor</title>

<style>
body{
    font-family: Arial, sans-serif;
    background: #f4f4f4;
    margin:0; padding:0;
}
.header{
    background:#008000; /* verde professor */
    padding:15px; text-align:center;
    color:white;
}
.container{
    width:80%; margin:20px auto; background:white;
    padding:20px; border-radius:10px;
    box-shadow: 0 0 8px rgba(0,0,0,0.1);
}
.section{
    border:1px solid #ddd; background:#fafafa;
    padding:15px; border-radius:8px;
    margin-bottom:15px;
}
.btn{
    padding:8px 15px;
    display:inline-block;
    border-radius:6px;
    text-decoration:none;
    color:white;
    font-weight:bold;
}
.btn-green{ background:#28a745; }
.btn-blue{ background:#007bff; }
.btn-orange{ background:#ff8800; }
.btn-red{ background:#dc3545; float:right; }
</style>

</head>
<body>

<div class="header">
    <h2>Portal do Professor</h2>
    <a href="logout.jsp" class="btn btn-red">Sair</a>
</div>

<div class="container">

    <div class="section">
        <h3>👨‍🏫 Dados do Professor</h3>
        <p><strong>Nome:</strong> <%= professor.getNome() %></p>
        <p><strong>RGM:</strong> <%= professor.getRgm() %></p>
        <p><strong>E-mail:</strong> <%= professor.getEmail() %></p>
        <p><strong>Curso:</strong> <%= curso != null ? curso.getNome() : "Não atribuído" %></p>
        <p><strong>Turma:</strong> <%= turma != null ? turma.getNome() : "Não atribuída" %></p>
    </div>

    <div class="section">
        <h3>📊 Lançamento de Notas</h3>
        <p>O professor poderá lançar as notas dos alunos da sua turma.</p>
        <a href="#" class="btn btn-blue">Acessar Notas</a>
    </div>

    <div class="section">
        <h3>📆 Controle de Faltas</h3>
        <p>O professor poderá registrar e consultar faltas dos seus alunos.</p>
        <a href="#" class="btn btn-orange">Registrar Faltas</a>
    </div>

    <div class="section">
        <h3>🎥 Aulas Online / Materiais</h3>
        <p>Esta área será utilizada para disponibilizar links, PDF e apresentações.</p>
        <a href="#" class="btn btn-green">Abrir Conteúdo</a>
    </div>

</div>

</body>
</html>
