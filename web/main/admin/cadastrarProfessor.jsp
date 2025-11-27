<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Usuario" %>
<%@ page import="model.DAO.UsuarioDAO" %> 
<%@ page import="org.json.JSONObject" %>
<%
// Define o tipo de conteúdo da resposta como JSON
response.setContentType("application/json");
response.setCharacterEncoding("UTF-8");
JSONObject jsonResponse = new JSONObject();

// 1. Receber e Validar Dados
String nomeCompleto = request.getParameter("nome");
String email = request.getParameter("email");
String rgm = request.getParameter("rgm"); 

// A variável disciplina foi removida daqui!

String senhaPadrao = "123456"; 
String tipo = "PROFESSOR"; 

// VALIDAÇÃO REVISADA: Apenas campos NOT NULL da tabela 'usuario' (nome, email, rgm)
if (nomeCompleto == null || nomeCompleto.isEmpty() ||
email == null || email.isEmpty() ||
rgm == null || rgm.isEmpty()) {

jsonResponse.put("success", false);
jsonResponse.put("message", "Todos os campos obrigatórios (Nome, E-mail, RGM) são requeridos.");
}else{
try {
// 2. Criar Objeto Modelo 
Usuario novoProfessor = new Usuario();
novoProfessor.setNomeCompleto(nomeCompleto);
novoProfessor.setRgm(rgm); 
novoProfessor.setEmail(email);
novoProfessor.setSenha(senhaPadrao);
novoProfessor.setTipo(tipo);

// 3. Chamar a Lógica de Persistência (DAO)
UsuarioDAO dao = new UsuarioDAO();
boolean cadastrado = dao.cadastrar(novoProfessor);

if (cadastrado) {
jsonResponse.put("success", true);
jsonResponse.put("message", "Professor " + nomeCompleto + " cadastrado com sucesso.");
} else {
jsonResponse.put("success", false);
jsonResponse.put("message", "Falha ao cadastrar. O e-mail ou RGM já pode estar em uso, ou houve erro na conexão.");
}

 } catch (Exception e) {
 e.printStackTrace();
jsonResponse.put("success", false);
jsonResponse.put("message", "Erro interno do servidor: " + e.getMessage());
}
}

// Retorna a resposta JSON
out.print(jsonResponse.toString());
out.flush();
%>