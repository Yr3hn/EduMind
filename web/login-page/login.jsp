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
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="style.css">
  <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700&display=swap" rel="stylesheet">
  <title>Document</title>
</head>
<body>
  <div class="wrapper active" id="loginWrapper">
    <div class="logo">
      <img src="#">
    </div> <!--logo-->
    <div class="buttons">
      <button id="loginBtn">Login</button>
      <button id="primeiroBtn">Primeiro Acesso</button>
    </div> <!--buttons-->
  </div> <!--wrapper-->

  <div class="wrapper" id="secondWrapper">
    <div class="logo">
      <img src="#">
    </div> <!--logo-->
    <div class="inputs">
      <input type="text" placeholder="rgm"> <!--rgm-->
      <input type="password" placeholder="senha"> <!--senha-->
      <button id="btnEntrar">Entrar</button>
      <button class="back-button">Voltar</button>
    </div> <!--inputs-->
  </div> <!--wrapper-->

  <div class="wrapper" id="thirdWrapper">
    <div class="logo">
      <img src="#">
    </div> <!--logo-->
    <div class="inputs">
      <p>"registre aqui seu email"</p>
      <input type="text" placeholder="email"> <!--email-->
      <button>Entrar</button>
      <button class="back-button">Voltar</button>
    </div> <!--inputs-->
  </div> <!--wrapper-->

  <!--ondas-->

  <section>
    <div class="onda ondas1"></div>
    <div class="onda ondas2"></div>
    <div class="onda ondas3"></div>
    <div class="onda ondas4"></div>
  </section>

  <script src="script.js"></script>
</body>
</html>
