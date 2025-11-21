<%@ page import="model.DAO.UsuarioDAO" %>
<%@ page import="model.Usuario" %>
<%@ page import="model.Util" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%
try {
    String rgm = request.getParameter("rgm");
    String senha = request.getParameter("senha");
    
    if(rgm == null || senha == null || rgm.trim().isEmpty() || senha.trim().isEmpty()) {
        response.sendRedirect("login.jsp?erro=Preencha todos os campos");
        return;
    }
    
    // IMPORTANTE: Envia senha em texto plano, o DAO tenta texto plano primeiro, depois MD5
    // (No banco ainda está texto plano, mas quando atualizar para MD5 continuará funcionando)
    UsuarioDAO dao = new UsuarioDAO();
    Usuario u = dao.autenticar(rgm, senha);
    
    if(u != null){
        session.setAttribute("usuarioLogado", u);
        
        String tipo = u.getTipo() != null ? u.getTipo().toLowerCase() : "";
        
        if(tipo.equals("admin")){
            response.sendRedirect("portalAdmin.jsp");
        } else if(tipo.equals("professor")){
            response.sendRedirect("portalProfessor2.jsp");
        } else {
            response.sendRedirect("portalAluno2.jsp");
        }

    } else {
        response.sendRedirect("login.jsp?erro=RGM ou senha incorretos");
    }
} catch (Exception e) {
    System.out.println("Erro ao processar login: " + e.getMessage());
    e.printStackTrace();
    response.sendRedirect("login.jsp?erro=Erro ao processar login. Tente novamente.");
}
%>
