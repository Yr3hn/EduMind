<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Recebe o parâmetro 'materia' da URL. Ex: index.jsp?materia=matematica
    String materiaSelecionada = request.getParameter("materia");

    // Define os dados de todas as matérias
    java.util.Map<String, String[]> materias = new java.util.HashMap<>();
    materias.put("matematica", new String[]{"Matemática", "Prof. Carlos Silva", "3 atividades"});
    materias.put("portugues", new String[]{"Português", "Prof. Ana Paula", "2 atividades"});
    materias.put("historia", new String[]{"História", "Prof. Eduardo Augusto", "4 atividades"});
    materias.put("biologia", new String[]{"Biologia", "Prof. Anthony Solto", "2 atividades"});

    // Simulação dos cards de atividades para a matéria de Matemática
    // Você precisaria de lógica real de banco de dados para buscar isso
    java.util.List<String[]> atividadesMatematica = new java.util.ArrayList<>();
    atividadesMatematica.add(new String[]{"Exercícios de Álgebra", "09/11/2025", "Entregue"});
    atividadesMatematica.add(new String[]{"Prova - Equações", "09/11/2025", "Pendente"});

    // Pega os dados da matéria selecionada, se houver
    String nomeMateria = "";
    String nomeProfessor = "";
    String numAtividades = "";
    java.util.List<String[]> atividades = null;

    if (materiaSelecionada != null && materias.containsKey(materiaSelecionada)) {
        String[] dados = materias.get(materiaSelecionada);
        nomeMateria = dados[0];
        nomeProfessor = dados[1];
        numAtividades = dados[2];

        // Aqui, um switch ou if/else seria usado para carregar as atividades corretas do banco de dados
        if ("matematica".equals(materiaSelecionada)) {
            atividades = atividadesMatematica;
        }
        // ... mais ifs para outras matérias ...
    }

    // Define classes CSS para controlar a visibilidade no lado do cliente
    String classCardWrapper = (materiaSelecionada == null || materiaSelecionada.isEmpty()) ? "visible" : "hidden";
    String classCardContent = (materiaSelecionada != null && !materiaSelecionada.isEmpty()) ? "visible" : "hidden";
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="../../../general-config/geral.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700&display=swap" rel="stylesheet">
    <title>EduWeb - Área do Professor</title>
</head>
<body>
    <header>
        <div class="left-header">
            <div class="icon-pfp">
                <img src="../../../general-config/img/pfp.webp" alt="Foto de perfil">
            </div>
            <div class="hamburger" id="hamburger">
                <i class="fa-solid fa-bars"></i>
            </div>
        </div>
        <div class="title">
            <i class="fa-solid fa-graduation-cap"></i>
            <p class="main-title">EduWeb</p>
            <p class="subtitle">Área do Professor</p>
        </div>
        <div class="bell-icon">
            <i class="fa-solid fa-bell"></i>
        </div>
    </header>
    <nav class="menu-lateral" id="menu-lateral">
        <ul>
            <img src="../../../general-config/img/pfp.webp" alt="Foto de perfil">
            <li><a href="/EduWebApp/main/professor/main-page/index.html">Início</a></li>
            <li><a href="/EduWebApp/main/professor/main-page/perfil-page/index.html">Perfil</a></li>
            <li><a href="/EduWebApp/main/professor/main-page/chat-page/index.html">Chat</a></li>
            <li><a href="/EduWebApp/main/professor/main-page/config-page/index.html">Configurações</a></li>
        </ul>
    </nav>
    <div class="overlay" id="overlay"></div>

    <main>
        <div class="card-wrapper <%= classCardWrapper %>">
            <%
                // Itera sobre as matérias para criar os cards
                for (java.util.Map.Entry<String, String[]> entry : materias.entrySet()) {
                    String chave = entry.getKey();
                    String[] dados = entry.getValue();
                    String nome = dados[0];
                    String prof = dados[1];
                    String numAtiv = dados[2];
            %>
            <a href="index.jsp?materia=<%= chave %>" class="card <%= chave %>">
                <div class="icon-wrapper">
                    <i class="fa-solid fa-book-open"></i>
                </div>
                <h2><%= nome %></h2>
                <p><%= prof %></p>
                <div class="atividades-counter">
                    <span><%= numAtiv %></span>
                    <i class="fa-solid fa-chevron-right"></i>
                </div>
            </a>
            <% } %>
        </div> <div class="card-content <%= classCardContent %>">
            <a href="index.jsp">
                <i class="fa-solid fa-chevron-left"></i>
                <span>Voltar para cursos</span>
            </a>
            <div class="content-materia">
                <span>"<%= nomeMateria %>"</span>
                <p>"<%= nomeProfessor %>"</p>
            </div> <div class="create-atividades">
                <p style="padding: 10px">Atividades</p>
                <button>
                    + Criar Atividade
                </button>
            </div>
            <div class="wrapper-atividades">
            <%
                if (atividades != null) {
                    for (String[] atividade : atividades) {
                        String titulo = atividade[0];
                        String dataEntrega = atividade[1];
                        String status = atividade[2];
                        String statusClass = status.equals("Entregue") ? "status-entregue" : "status-pendente";
            %>
                <div class="card-atividades">
                    <div class="icons-atividades">
                        <span class="titulo-atividade"><%= titulo %></span>
                        <div class="icons">
                            <button>
                                <i class="fa-solid fa-pen-to-square edit-btn"></i>
                            </button>
                            <button>
                                <i class="fa-solid fa-trash delete-btn"></i>
                            </button>
                        </div>
                    </div>
                    <div class="entrega">
                        <i class="fa-regular fa-clock"></i>
                        <span>Entrega: <%= dataEntrega %></span>
                        <span class="<%= statusClass %>"><%= status %></span>
                    </div>
                </div>
            <%
                    }
                }
            %>
            </div> </div> </main>

    <script src="../../../general-config/geral.js"></script>
    <script src="script.js"></script>
    <style>
        .hidden { display: none !important; }
        .visible { display: flex; /* ou block, dependendo do elemento */ }
    </style>
</body>
</html>