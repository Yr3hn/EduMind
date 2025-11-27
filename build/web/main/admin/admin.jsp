<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.Usuario" %>
<%
    // ===========================================
    // 1. CÓDIGO DE SEGURANÇA E AUTORIZAÇÃO (JSP)
    // ===========================================
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null || !"ADMIN".equalsIgnoreCase(usuarioLogado.getTipo())) {
        // Redireciona para o login se não for admin ou não estiver logado
        response.sendRedirect(request.getContextPath() + "/loginAdmin.jsp?erro=Acesso Negado."); 
        return;
    }
    
    // Captura o nome para exibição na Navbar
    String nomeAdmin = usuarioLogado.getNomeCompleto() != null ? usuarioLogado.getNomeCompleto() : "Admin";
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>EduMind - Painel Administrativo</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <link rel="stylesheet" href="src/style.css" />

    <style>
        .modal-overlay {
            display: none; /* Inicia oculto */
            position: fixed;
            z-index: 1000; /* Acima de todo o conteúdo */
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.5); /* Fundo semi-transparente */
            justify-content: center; /* Centraliza o conteúdo horizontalmente */
            align-items: center; /* Centraliza o conteúdo verticalmente */
        }

        .modal-content {
            background-color: #fefefe;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            width: 90%;
            max-width: 450px; /* Tamanho razoável para o formulário */
            position: relative;
        }

        .close-btn {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            position: absolute;
            top: 10px;
            right: 15px;
            cursor: pointer;
        }

        .close-btn:hover,
        .close-btn:focus {
            color: #000;
            text-decoration: none;
            cursor: pointer;
        }

        /* Estilos para o formulário dentro do modal (opcional, mas recomendado) */
        .modal-content .form-card {
            box-shadow: none; /* Remove a sombra do card se for mantido */
            border: none;
            padding: 0;
        }
    </style>
</head>
<body>
    <aside class="sidebar" aria-label="Barra lateral">
    <div class="brand">
      <div class="logo">EM</div>
      <div class="brand-text">
        <h1>EduMind</h1>
        <small>Admin</small>
      </div>
    </div>

    <nav class="menu">
      <a href="#dashboard" class="menu-item active"><i class="fa-solid fa-chart-simple"></i><span>Dashboard</span></a>
            <a id="btn-modal-professor" class="menu-item"><i class="fa-solid fa-chalkboard-user"></i><span>Professores</span></a>
      <a href="#alunos" class="menu-item"><i class="fa-solid fa-user-graduate"></i><span>Alunos</span></a>
      <a href="#turmas" class="menu-item"><i class="fa-solid fa-school"></i><span>Turmas</span></a>
      <a href="#disciplinas" class="menu-item"><i class="fa-solid fa-book"></i><span>Disciplinas</span></a>
      <div class="menu-sep"></div>
      <a id="btn-settings" class="menu-item"><i class="fa-solid fa-gear"></i><span>Configurações</span></a>
    </nav>

    <div class="sidebar-footer">
      <small>Painel • EduMind - Por: Pedro</small>
    </div>
  </aside>

    <main class="main">
        <header class="topbar">
      <div class="search">
        <input id="searchInput" type="search" placeholder="Pesquisar..." aria-label="Pesquisar" />
        <i class="fa-solid fa-magnifying-glass"></i>
      </div>

      <div class="top-actions">
        <div class="user-info" title="Usuário">
          <i class="fa-solid fa-user-circle"></i>
                    <span id="userName"><%= nomeAdmin %></span>
        </div>
        <button id="btnLogout" class="btn-ghost" title="Sair"><i class="fa-solid fa-right-from-bracket"></i></button>
      </div>
    </header>

        <section class="content">
            <section id="dashboard" class="panel active-panel">
        <h2>Dashboard</h2>

        <div class="cards">
          <div class="card">
            <div class="card-icon"><i class="fa-solid fa-chalkboard-user"></i></div>
            <div class="card-body">
              <small>Professores</small>
              <strong id="count-professores">0</strong>
            </div>
          </div>

          <div class="card">
            <div class="card-icon"><i class="fa-solid fa-user-graduate"></i></div>
            <div class="card-body">
              <small>Alunos</small>
              <strong id="count-alunos">0</strong>
            </div>
          </div>

          <div class="card">
            <div class="card-icon"><i class="fa-solid fa-school"></i></div>
            <div class="card-body">
              <small>Turmas</small>
              <strong id="count-turmas">0</strong>
            </div>
          </div>

          <div class="card">
            <div class="card-icon"><i class="fa-solid fa-book"></i></div>
            <div class="card-body">
              <small>Disciplinas</small>
              <strong id="count-disciplinas">0</strong>
            </div>
          </div>
        </div>

        <div class="welcome-card">
          <h3>Bem-vindo ao Painel</h3>
          <p>Use a barra lateral para navegar entre registros e editar cadastros. Este painel salva localmente (localStorage).</p>
        </div>
      </section>

            <section id="professores" class="panel">
        <h2>Professores</h2>

        <div class="panel-grid">
                    
          <div class="card list-card">
            <h3>Lista de Professores</h3>
            <ul id="lista-professores" class="list"></ul>
                        <button type="button" id="limpar-professores" class="btn-ghost" style="margin-top: 15px;">Limpar todos</button>
          </div>
        </div>
      </section>

            <section id="alunos" class="panel">
        <h2>Alunos</h2>

        <div class="panel-grid">
          <form id="form-aluno" class="card form-card">
            <h3>Novo Aluno</h3>
            <input name="nome" type="text" placeholder="Nome completo" required />
            <input name="email" type="email" placeholder="E-mail" required />
            <input name="turma" type="text" placeholder="Turma" required />
            <div class="form-actions">
              <button type="submit" class="btn-primary">Registrar</button>
              <button type="button" id="limpar-alunos" class="btn-ghost">Limpar todos</button>
            </div>
          </form>

          <div class="card list-card">
            <h3>Lista de Alunos</h3>
            <ul id="lista-alunos" class="list"></ul>
          </div>
        </div>
      </section>

            <section id="turmas" class="panel">
        <h2>Turmas</h2>

        <div class="panel-grid">
          <form id="form-turma" class="card form-card">
            <h3>Nova Turma</h3>
            <input name="nome" type="text" placeholder="Nome da turma" required />
            <input name="ano" type="text" placeholder="Ano" required />
            <div class="form-actions">
              <button type="submit" class="btn-primary">Registrar</button>
              <button type="button" id="limpar-turmas" class="btn-ghost">Limpar todos</button>
            </div>
          </form>

          <div class="card list-card">
            <h3>Lista de Turmas</h3>
            <ul id="lista-turmas" class="list"></ul>
          </div>
        </div>
      </section>

            <section id="disciplinas" class="panel">
        <h2>Disciplinas</h2>

        <div class="panel-grid">
          <form id="form-disciplina" class="card form-card">
            <h3>Nova Disciplina</h3>
            <input name="nome" type="text" placeholder="Nome da disciplina" required />
            <input name="codigo" type="text" placeholder="Código (ex: MAT101)" required />
            <div class="form-actions">
              <button type="submit" class="btn-primary">Registrar</button>
              <button type="button" id="limpar-disciplinas" class="btn-ghost">Limpar todos</button>
            </div>
          </form>

          <div class="card list-card">
            <h3>Lista de Disciplinas</h3>
            <ul id="lista-disciplinas" class="list"></ul>
          </div>
        </div>
      </section>
    </section>
  </main>

    <div id="modal-professor" class="modal-overlay">
        <div class="modal-content">
            <span class="close-btn">&times;</span>
            
            <form id="form-professor" class="card form-card">
                <h3>Novo Professor</h3>
                <input name="nome" type="text" placeholder="Nome completo" required />
                <input name="email" type="email" placeholder="E-mail" required />
                <input type="text" id="rgmProfessor" name="rgm" placeholder="RGM (Matrícula)" required>
                <div class="form-actions">
                    <button type="submit" class="btn-primary">Registrar</button>
                    </div>
                <p id="msg-professor" style="margin-top: 10px;"></p>
            </form>
        </div>
    </div>
    
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Referências para o modal e botão do professor
            const btnModalProfessor = document.getElementById('btn-modal-professor');
            const modalProfessor = document.getElementById('modal-professor');
            const closeModalBtn = modalProfessor ? modalProfessor.querySelector('.close-btn') : null;
            
            // Referências para navegação
            const menuItems = document.querySelectorAll('.menu-item');
            const panels = document.querySelectorAll('.panel');

            // Função para alternar entre painéis (abas)
            function switchPanel(targetId, currentItem) {
                // Remove 'active-panel' de todos os painéis e 'active' de todos os itens de menu
                panels.forEach(p => p.classList.remove('active-panel'));
                menuItems.forEach(i => i.classList.remove('active'));

                // Adiciona 'active-panel' ao painel alvo e 'active' ao item de menu
                const targetPanel = document.getElementById(targetId);
                if (targetPanel) {
                    targetPanel.classList.add('active-panel');
                }
                if (currentItem) {
                    currentItem.classList.add('active');
                }
            }

            // ===========================================
            // Controle do Modal de Professor
            // ===========================================

            if (btnModalProfessor && modalProfessor) {
                // 1. Abrir Modal ao clicar no menu "Professores"
                btnModalProfessor.addEventListener('click', function(e) {
                    e.preventDefault(); // Evita que a página role para uma âncora (embora não haja)
                    modalProfessor.style.display = 'flex'; // Exibe o modal
                    
                    // Altera a visualização principal para a lista de professores no fundo
                    switchPanel('professores', btnModalProfessor);
                });
            }

            // 2. Fechar Modal ao clicar no 'X'
            if (closeModalBtn) {
                closeModalBtn.addEventListener('click', function() {
                    modalProfessor.style.display = 'none';
                });
            }

            // 3. Fechar Modal ao clicar fora dele (overlay)
            if (modalProfessor) {
                window.addEventListener('click', function(event) {
                    if (event.target === modalProfessor) {
                        modalProfessor.style.display = 'none';
                    }
                });
            }

            // ===========================================
            // Controle de Navegação Padrão (Dashboard, Alunos, etc.)
            // ===========================================

            menuItems.forEach(item => {
                // Apenas itens com 'href' (navegação de painel)
                if (item.getAttribute('href')) { 
                    item.addEventListener('click', function(e) {
                        e.preventDefault();
                        const targetId = item.getAttribute('href').substring(1); // Remove o '#'
                        
                        // Garante que o modal esteja fechado ao navegar para outra seção
                        if (modalProfessor) {
                            modalProfessor.style.display = 'none';
                        }

                        switchPanel(targetId, item);
                    });
                }
            });
            
            // Inicialização da navegação (aplica o painel 'active' correto ao carregar)
            // Já está definido em 'dashboard' no HTML, mas aqui está a lógica de reativação se necessário:
            // switchPanel('dashboard', document.querySelector('.menu-item.active'));

            // *Adicione aqui o restante da sua lógica JavaScript (limpar listas, salvar dados, etc.)*
        });
    </script>
</body>
</html>