<%@ page import="model.Usuario" %>
<%
    // Obtém o objeto Usuario da sessão
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

    // 1. Verifica se o usuário não está logado
    if (usuarioLogado == null) {
        // Redireciona para a página de login (ex: loginAdmin.jsp)
        // Usamos getContextPath() para garantir que o caminho seja absoluto e funcione
        response.sendRedirect(request.getContextPath() + "/main/admin/admin.jsp?erro=Acesso Negado. Faça o login.");
        return;
    }

    // 2. Verifica se o usuário logado NÃO é um Administrador (ignora maiúsculas/minúsculas)
    // Usamos getTipo() para obter o tipo do usuário.
    String tipoUsuario = usuarioLogado.getTipo();
    
    if (tipoUsuario == null || !"ADMIN".equalsIgnoreCase(tipoUsuario)) {
        
        // Remove o usuário da sessão, forçando um novo login
        session.invalidate(); 
        
        // Redireciona para a página de login com a mensagem de erro
        response.sendRedirect(request.getContextPath() + "/main/admin/admin.jsp?erro=Seu usuário não possui permissão de Administrador.");
        return;
    }
    
    // Se o código chegou até aqui, o usuário é um Administrador e o restante da página é exibido.
    
String erro = null;
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
        <h1>EduMind - Acesso Admin</h1>
        
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