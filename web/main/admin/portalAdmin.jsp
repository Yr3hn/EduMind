<%@ page import="model.Usuario" %>
<%
Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
if (usuarioLogado == null || !"admin".equals(usuarioLogado.getTipo().toLowerCase())) {
    response.sendRedirect("login.jsp");
    return;
}
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Painel Administrativo</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            border-radius: 12px;
            padding: 30px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
        }
        h2 {
            color: #333;
            margin-bottom: 10px;
            font-size: 28px;
        }
        .welcome {
            color: #666;
            margin-bottom: 30px;
            font-size: 16px;
        }
        h3 {
            color: #667eea;
            margin-top: 30px;
            margin-bottom: 20px;
            font-size: 22px;
            border-bottom: 2px solid #667eea;
            padding-bottom: 10px;
        }
        .menu-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 64px;
            margin-top: 28px;
        }
        .card{margin:6px}
        @media (max-width: 768px) { .menu-grid { grid-template-columns: 1fr; } }
        .menu-item {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 25px;
            border-radius: 8px;
            text-align: center;
            transition: transform 0.3s, box-shadow 0.3s;
            text-decoration: none;
            color: white;
            display: block;
        }
        .menu-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4);
        }
        .menu-item h4 {
            margin: 0 0 10px 0;
            font-size: 18px;
        }
        .menu-item p {
            margin: 0;
            font-size: 14px;
            opacity: 0.9;
        }
        .logout {
            background: #dc3545;
            margin-top: 30px;
        }
        .logout:hover {
            background: #c82333;
            box-shadow: 0 8px 20px rgba(220, 53, 69, 0.4);
        }
        .error, .success {
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .error {
            background: #ffebee;
            color: #c62828;
            border-left: 4px solid #c62828;
        }
        .success {
            background: #e8f5e9;
            color: #2e7d32;
            border-left: 4px solid #2e7d32;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Painel Administrativo</h2>
    <p class="welcome">Olá, <strong><%= usuarioLogado.getNome() %></strong>! Bem-vindo ao sistema de gerenciamento.</p>

    <% 
        String erro = request.getParameter("erro");
        String sucesso = request.getParameter("sucesso");
    %>
    
    <% if(erro != null && !erro.isEmpty()) { %>
        <div class="error"><strong>Erro:</strong> <%= erro %></div>
    <% } %>
    
    <% if(sucesso != null && !sucesso.isEmpty()) { %>
        <div class="success"><strong>Sucesso:</strong> <%= sucesso %></div>
    <% } %>

    <h3>Gerenciamento</h3>
    
    <div class="menu-grid">
        <a href="admin/cadastrarUsuario.jsp?tipo=aluno" class="menu-item">
            <h4>👨‍🎓 Cadastrar Aluno</h4>
            <p>Adicionar novo aluno ao sistema</p>
        </a>
        
        <a href="admin/cadastrarUsuario.jsp?tipo=professor" class="menu-item">
            <h4>👨‍🏫 Cadastrar Professor</h4>
            <p>Adicionar novo professor ao sistema</p>
        </a>
        
        <a href="admin/cursos.jsp" class="menu-item">
            <h4>📚 Gerenciar Cursos</h4>
            <p>Cadastrar e editar cursos</p>
        </a>
        
        <a href="admin/turmas.jsp" class="menu-item">
            <h4>🏫 Gerenciar Turmas</h4>
            <p>Cadastrar e editar turmas</p>
        </a>
        
        <a href="logout.jsp" class="menu-item logout">
            <h4>🚪 Sair</h4>
            <p>Fazer logout do sistema</p>
        </a>
    </div>

</div>

</body>
</html>
