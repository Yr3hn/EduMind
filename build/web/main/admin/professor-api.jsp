<%-- 
    Document   : professor-api
    Created on : 27 de nov. de 2025, 14:17:41
    Author     : pedro
--%>

<%@ page contentType="application/json; charset=UTF-8" language="java" %>
<%@ page import="model.DAO.UsuarioDAO" %>
<%@ page import="model.Usuario" %>
<%@ page import="model.Util" %>
<%@ page import="java.io.PrintWriter" %> // Este import é necessário, mas não a declaração.
<%@ page import="java.util.Random" %>
<%

    // Captura o parâmetro 'erro' passado pela URL (ex: ?erro=Acesso Negado.)
    String erro = request.getParameter("erro");
    // Se o parâmetro não existir, 'erro' será null.

    // Define que a resposta será JSON
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    // REMOVA ESTA LINHA: PrintWriter out = response.getWriter();

// Verificação básica de segurança: Apenas POSTs são aceitos

    // Verificação básica de segurança: Apenas POSTs são aceitos
    if (!"POST".equalsIgnoreCase(request.getMethod())) {
        out.print("{\"sucesso\": false, \"mensagem\": \"Método não permitido.\"}");
        return;
    }
    
    // ** 1. VERIFICAÇÃO DE SESSÃO (MANTENHA A SEGURANÇA) **
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    if (usuarioLogado == null || !"ADMIN".equalsIgnoreCase(usuarioLogado.getTipo())) {
        out.print("{\"sucesso\": false, \"mensagem\": \"Acesso negado. Faça o login.\"}");
        return;
    }

    String acao = request.getParameter("acao");

    if ("cadastrarProfessor".equals(acao)) {
        
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        // String disciplina = request.getParameter("disciplina"); // Se não for salvar, ignore

        if (nome != null && email != null) {
            
            Usuario novoProfessor = new Usuario();
            novoProfessor.setNomeCompleto(nome);
            novoProfessor.setEmail(email);
            novoProfessor.setTipo("PROFESSOR");
            
            // ** Geração de RGM e Senha Padrão **
            Random rand = new Random();
            String rgmGerado = String.format("%08d", rand.nextInt(100000000)); // RGM de 8 dígitos
            String senhaPadrao = "123456"; // Senha inicial
            
            novoProfessor.setRgm(rgmGerado);
            novoProfessor.setSenha(Util.md5(senhaPadrao)); // Criptografa a senha antes de enviar

            UsuarioDAO dao = new UsuarioDAO();
            
            if (dao.inserir(novoProfessor)) {
                // Sucesso
                out.print("{\"sucesso\": true, \"mensagem\": \"Professor " + nome + " cadastrado! Senha inicial: " + senhaPadrao + "\"}");
                return;
            } else {
                // Erro de BD
                out.print("{\"sucesso\": false, \"mensagem\": \"Erro ao inserir no banco de dados. Email pode já existir.\"}");
                return;
            }
        } else {
            // Campos faltando
            out.print("{\"sucesso\": false, \"mensagem\": \"Dados incompletos.\"}");
            return;
        }
    }

    // Ação não reconhecida
    out.print("{\"sucesso\": false, \"mensagem\": \"Ação não reconhecida.\"}");
%>