![Badge de Status do Projeto](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)
![Badge de Licença](https://img.shields.io/badge/Licen%C3%A7a-MIT-blue)
![Badge de Tecnologias](https://img.shields.io/badge/Backend-Java%20(JSP%2FServlets)-red)
![Badge de Tecnologias](https://img.shields.io/badge/Banco%20de%20Dados-MySQL-orange)
![Badge de Tecnologias](https://img.shields.io/badge/Frontend-HTML%2FCSS%2FJS-green)

## 🎯 Sobre o Projeto

**EduMind** é uma plataforma robusta e completa de ensino remoto (e-learning) projetada para conectar administradores, professores e alunos em um ambiente virtual de aprendizado dinâmico e eficiente. O sistema oferece todas as ferramentas necessárias para a gestão de conteúdo, acompanhamento de desempenho e comunicação, simulando a experiência de uma sala de aula presencial no ambiente digital.

O projeto foi desenvolvido com foco em uma arquitetura web full-stack, utilizando **Java (JSP/Servlets)** para o backend e **MySQL** para persistência de dados, garantindo escalabilidade e segurança.

## ✨ Funcionalidades Principais

O EduMind é dividido em três portais principais, cada um com funcionalidades específicas para seu perfil de usuário:

### 🌐 Acesso Geral

*   **Landing Page:** Página inicial profissional com informações sobre o sistema, depoimentos de clientes e um formulário de **Primeiro Acesso/Registro**.
*   **Login Inteligente:** Redirecionamento automático do usuário para o portal correspondente (Admin, Professor ou Aluno) após a autenticação.
*   **Primeiro Acesso:** Formulário de pré-registro que envia os dados para o Painel Administrativo, onde o cadastro final é realizado por um administrador.

### 🧑‍🏫 Portal do Professor

*   **Gestão de Conteúdo:** Postagem de aulas gravadas e agendamento de aulas ao vivo.
*   **Avaliações:** Lançamento de atividades e provas para as turmas.
*   **Correção e Notas:** Lançamento de notas para as atividades e provas realizadas pelos alunos.

### 👨‍🎓 Portal do Aluno

*   **Participação em Aulas:** Acesso e participação em aulas gravadas e ao vivo.
*   **Realização de Avaliações:** Execução de atividades e provas lançadas pelos professores.
*   **Acompanhamento de Desempenho:** Acesso a uma aba dedicada para visualização de notas e faltas, mantendo o aluno sempre informado sobre sua situação acadêmica.

### 👑 Painel Administrativo (Admin)

*   **Gestão de Usuários:** Cadastro e gerenciamento de todos os usuários do sistema (Admin, Professor e Aluno).
*   **Gestão Acadêmica:** Cadastro e organização de **Turmas**, **Disciplinas** e **Cursos**.

## 🛠️ Tecnologias Utilizadas

| Categoria | Tecnologia | Descrição |
| :--- | :--- | :--- |
| **Backend** | Java (JSP/Servlets) | Lógica de negócio, controle de acesso e manipulação de dados. |
| **Banco de Dados** | MySQL | Sistema de gerenciamento de banco de dados relacional. |
| **Frontend** | HTML5, CSS3, JavaScript | Estrutura, estilização e interatividade da interface do usuário. |
| **Ferramenta de Desenvolvimento** | NetBeans | IDE utilizada para o desenvolvimento e gerenciamento do projeto. |

## 🚀 Como Executar o Projeto

Para configurar e rodar o EduMind em seu ambiente local, siga os passos abaixo:

### Pré-requisitos

Certifique-se de ter instalado em sua máquina:

*   **JDK (Java Development Kit)** - Versão 8 ou superior.
*   **Servidor de Aplicação Web** (Ex: Apache Tomcat).
*   **MySQL Server** - Para o banco de dados.
*   **NetBeans IDE** (Recomendado para a estrutura do projeto).

### Configuração do Banco de Dados

1.  Crie um banco de dados MySQL com o nome `edumindb`.
2.  Importe o script SQL para criação das tabelas (o script não está no repositório, mas as tabelas podem ser inferidas pelos modelos Java: `Aluno`, `Professor`, `Turma`, `Curso`, `Avaliacao`, `Usuario`).
3.  Verifique e, se necessário, ajuste as credenciais de conexão no arquivo `EduMind/src/java/config/ConectaDB.java`:

    ```java
    // Linha 12: Ajuste a URL, usuário e senha conforme sua configuração
    conn = DriverManager.getConnection(url, "root", ""); 
    ```

### Execução

1.  Abra o projeto no **NetBeans IDE**.
2.  Configure o servidor Apache Tomcat no NetBeans.
3.  Clique com o botão direito no projeto e selecione **Run** (Executar).
4.  O projeto será compilado e implantado no servidor, abrindo a **Landing Page** no seu navegador.

## 📝 Licença

Este projeto está sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.

## 📞 Contato

Seu Nome/Nome da Equipe - [contatoeduweb@gmail.com]

Link do Projeto: [https://github.com/Y3rhn/EduMind](https://github.com/Yr3hn/EduMind)
