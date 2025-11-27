<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.DAO.UsuarioDAO" %>
<%@ page import="model.Usuario" %>

<%

    // Captura o parâmetro 'erro' passado pela URL (ex: ?erro=Acesso Negado.)
    String erro = request.getParameter("erro");
    // Se o parâmetro não existir, 'erro' será null.

    // Processa o login se houver POST
    if("POST".equals(request.getMethod())) {

        String rgm = request.getParameter("rgm");
        String senha = request.getParameter("senha");

        if(rgm != null && senha != null && !rgm.trim().isEmpty() && !senha.trim().isEmpty()) {

            UsuarioDAO dao = new UsuarioDAO();
            Usuario u = dao.autenticar(rgm, senha);

            if(u != null){
                session.setAttribute("usuarioLogado", u);

                String tipo = u.getTipo() != null ? u.getTipo() : "";

                switch(tipo.toUpperCase()){
                    case "ADMIN":
                        response.sendRedirect("../main/admin/portalAdmin.jsp");
                        return;
                    case "PROFESSOR":
                        response.sendRedirect("portalProfessor.jsp");
                        return;
                    default:
                        response.sendRedirect("portalAluno.jsp");
                        return;
                }
            } else {
                response.sendRedirect("login.jsp?erro=RGM ou senha incorretos");
                return;
            }

        } else {
            response.sendRedirect("login.jsp?erro=Preencha todos os campos");
            return;
        }
    }
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="style.css">
  <title>Login</title>
</head>
<body>

<div class="wrapper active" id="loginWrapper">
    <div class="logo">
      <img src="#">
    </div>
    <div class="buttons">
      <button id="loginBtn">Login</button>
      <button id="primeiroBtn">Primeiro Acesso</button>
    </div>
</div>

<div class="wrapper" id="secondWrapper">
    <div class="logo">
      <img src="#">
    </div>

    <!-- FORMULÁRIO DE LOGIN CORRETO -->
    <form method="POST" class="inputs">
      <input type="text" name="rgm" placeholder="RGM">
      <input type="password" name="senha" placeholder="Senha">

      <button type="submit" id="btnEntrar">Entrar</button>
      <button type="button" class="back-button">Voltar</button>
    </form>

    <% if(erro != null){ %>
        <p style="color:red; margin-top:10px;"><%= erro %></p>
    <% } %>

</div>

<div class="wrapper" id="thirdWrapper">
    <div class="logo">
      <img src="#">
    </div>
    <div class="inputs">
      <p>"registre aqui seu email"</p>
      <input type="text" placeholder="email">
      <button>Entrar</button>
      <button class="back-button">Voltar</button>
    </div>
</div>

<section>
    <div class="onda ondas1"></div>
    <div class="onda ondas2"></div>
    <div class="onda ondas3"></div>
    <div class="onda ondas4"></div>
</section>

<script src="script.js"></script>

</body>
</html>
