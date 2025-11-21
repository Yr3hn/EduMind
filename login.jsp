<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.DAO.UsuarioDAO" %>
<%@ page import="model.Usuario" %>
<%@ page import="model.Util" %>

<%
    // Processa o login se houver submissão POST
    if("POST".equals(request.getMethod())){
        String rgm = request.getParameter("rgm");
        String senha = request.getParameter("senha");
        
        if(rgm != null && senha != null && !rgm.trim().isEmpty() && !senha.trim().isEmpty()){
            // IMPORTANTE: Envia senha em texto plano, o DAO tenta texto plano primeiro, depois MD5
            // (No banco ainda está texto plano, mas quando atualizar para MD5 continuará funcionando)
            UsuarioDAO dao = new UsuarioDAO();
            Usuario u = dao.autenticar(rgm, senha);
            
            if(u != null){
                session.setAttribute("usuarioLogado", u);
                
                String tipo = u.getTipo() != null ? u.getTipo().toLowerCase() : "";
                if(tipo.equals("admin")){
                    response.sendRedirect("portalAdmin.jsp");
                    return;
                } else if(tipo.equals("professor")){
                    response.sendRedirect("portalProfessor.jsp");
                    return;
                } else {
                    response.sendRedirect("portalAluno.jsp");
                    return;
                }
            } else {
                // Login falhou - será redirecionado no processaLogin.jsp
                response.sendRedirect("login.jsp?erro=RGM ou senha incorretos");
                return;
            }
        } else {
            response.sendRedirect("login.jsp?erro=Preencha todos os campos");
            return;
        }
    }
    
    // Verifica se há mensagem de erro
    String erro = request.getParameter("erro");
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>EduMind - Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="login-page">

<div class="wrapper active">

    <div class="logo">
        <img src="#" alt="logo">
    </div>

    <form class="inputs" action="processaLogin.jsp" method="post">
        
        <% if(erro != null && !erro.isEmpty()) { %>
        <div style="color: red; background-color: #ffebee; padding: 10px; border-radius: 5px; margin-bottom: 15px; border: 1px solid #f44336;">
            <strong>Erro:</strong> <%= erro %>
        </div>
        <% } %>
        
        <label for="rgm">RGM</label>
        <input type="text" id="rgm" name="rgm" required placeholder="Digite seu RGM" value="<%= request.getParameter("rgm") != null ? request.getParameter("rgm") : "" %>">

        <label for="senha">Senha</label>
        <input type="password" id="senha" name="senha" required placeholder="Digite sua senha">

        <button type="submit">Entrar</button>
    </form>

</div>

</body>
</html>
