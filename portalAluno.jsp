<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Usuario, model.DAO.CursoDAO, model.DAO.TurmaDAO, model.Curso, model.Turma" %>

<%
    // Segurança — só aluno acessa
    Usuario aluno = (Usuario) session.getAttribute("usuarioLogado");
    if (aluno == null || !"aluno".equals(aluno.getTipo().toLowerCase())) {
        response.sendRedirect("login.jsp");
        return;
    }

    CursoDAO cursoDAO = new CursoDAO();
    TurmaDAO turmaDAO = new TurmaDAO();

    Curso curso = cursoDAO.buscar(aluno.getIdCurso());
    Turma turma = turmaDAO.buscar(aluno.getIdTurma());
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<title>Portal do Aluno</title>

<style>
body{
    font-family: Arial, sans-serif;
    background: #f4f4f4;
    margin:0; padding:0;
}
.header{
    background: #0066ff; color:white;
    padding:15px; text-align:center;
}
.container{
    width:80%; margin:auto; margin-top:20px;
    background:white; padding:20px;
    border-radius:8px;
    box-shadow:0 0 8px rgba(0,0,0,0.1);
}
.info-box{
    border:1px solid #ddd;
    padding:15px; border-radius:8px;
    margin-bottom:20px;
}
.section-box{
    background:#fafafa; border:1px solid #ddd;
    padding:15px; border-radius:8px;
    margin-bottom:15px;
}
.btn{
    background:#0066ff; padding:8px 16px;
    border-radius:5px; text-decoration:none;
    color:white; font-weight:bold; display:inline-block;
}
.logout{ background:#ff4c4c; float:right; }
</style>

</head>
<body>

<div class="header">
    <h2>Bem-vindo, <%= aluno.getNome() %>!</h2>
    <a class="btn logout" href="logout.jsp">Sair</a>
</div>

<div class="container">

    <div class="info-box">
        <h3>📘 Informações do aluno</h3>
        <p><strong>Nome:</strong> <%= aluno.getNome() %></p>
        <p><strong>RGM:</strong> <%= aluno.getRgm() %></p>
        <p><strong>Curso:</strong> <%= (curso != null ? curso.getNome() : "Não definido") %></p>
        <p><strong>Turma:</strong> <%= (turma != null ? turma.getNome() : "Não definida") %></p>
    </div>

    <div class="section-box">
        <h3>📊 Notas</h3>
        <p>(em breve… será carregado automaticamente)</p>
        <a href="#" class="btn">Ver histórico</a>
    </div>

    <div class="section-box">
        <h3>📆 Faltas</h3>
        <p>(em breve… exibiremos relatório oficial)</p>
        <a href="#" class="btn">Ver calendário</a>
    </div>

    <div class="section-box">
        <h3>🎥 Aulas online / materiais</h3>
        <p>(você poderá integrar Google Meet, Zoom ou YouTube)</p>
        <a href="#" class="btn">Acessar aulas</a>
    </div>

</div>

</body>
</html>
