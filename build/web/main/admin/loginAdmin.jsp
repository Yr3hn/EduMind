<%-- 
    Document   : loginAdmin
    Created on : 27 de nov. de 2025, 13:56:50
    Author     : pedro
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.DAO.UsuarioDAO" %>
<%@ page import="model.Usuario" %>

<%
    // ** DECLARAÇÃO E INICIALIZAÇÃO DA VARIÁVEL 'erro' **
    String erro = null; 

    // Processa o login se houver POST
    if("POST".equals(request.getMethod())) {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if(email != null && senha != null && !email.trim().isEmpty() && !senha.trim().isEmpty()) {

            UsuarioDAO dao = new UsuarioDAO();
            Usuario u = dao.autenticarPorEmail(email, senha);

            if(u != null){
                String tipo = u.getTipo() != null ? u.getTipo() : "";

                if("ADMIN".equalsIgnoreCase(tipo)){
                    session.setAttribute("usuarioLogado", u);
                    response.sendRedirect(request.getContextPath() + "/main/admin/admin.jsp");
                    return;
                } else {
                    erro = "Acesso restrito à administração.";
                }
            } else {
                erro = "Email ou senha incorretos.";
            }

        } else {
            erro = "Preencha o Email e a Senha.";
        }
    }
    // Opcionalmente, pode buscar mensagens de erro passadas via URL, caso necessário.
    // String erroUrl = request.getParameter("erro");
    // if (erroUrl != null) {
    //     erro = erroUrl;
    // }
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Painel Admin</title>
    <link rel="stylesheet" href="src/login.css">
</head>
<body>
    <div class="login-container">
        <h1>Acesso Administrativo</h1>
        
        <form method="POST" action="loginAdmin.jsp"> 
            <input type="email" name="email" placeholder="Email" required>
            <input type="password" name="senha" placeholder="Senha" required>
            <button type="submit">Entrar</button>
            
            <% if(erro != null){ %>
                <p style="color:red; margin-top:10px;" class="erro"><%= erro %></p>
            <% } %>
        </form>
    </div>
</body>
</html>
