const loginWrapper = document.getElementById("loginWrapper");
const secondWrapper = document.getElementById("secondWrapper");
const thirdWrapper = document.getElementById("thirdWrapper");

const loginBtn = document.getElementById("loginBtn");
const primeiroBtn = document.getElementById("primeiroBtn");

// Mostra a tela de login (RGM e senha)
loginBtn.addEventListener("click", () => {
  switchWrapper(loginWrapper, secondWrapper);
});

// Mostra a tela de primeiro acesso (email)
primeiroBtn.addEventListener("click", () => {
  switchWrapper(loginWrapper, thirdWrapper);
});

// Função genérica de transição
function switchWrapper(hide, show) {
  hide.classList.remove("active");
  setTimeout(() => {
    show.classList.add("active");
  }, 300);
}

// Botão de registrar email
const emailButton = thirdWrapper.querySelector("button");
emailButton.addEventListener("click", () => {
  const emailInput = thirdWrapper.querySelector("input").value;

  if (emailInput.trim() === "") {
    alert("Por favor, insira seu email.");
    return;
  }

  alert("Um email foi enviado para o registro da sua conta.");
});

// Começar com apenas a primeira tela visível
loginWrapper.classList.add("active");

// Botão de voltar (telas 2 e 3)
document.querySelectorAll(".back-button").forEach(btn => {
  btn.addEventListener("click", () => {
    switchWrapper(btn.closest(".wrapper"), loginWrapper);
  });
});

// Botão para entrar
document.getElementById("btnEntrar").addEventListener("click", () => {
  window.location.href = "../main/aluno/main-page/index.html";
});
