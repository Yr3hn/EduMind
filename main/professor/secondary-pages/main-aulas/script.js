
// esconder o ao-vivo e o gravadas

const toggleInput = document.getElementById("toggle");
const cardsGravadas = document.querySelector(".cards-container");
const cardsAoVivo = document.querySelector(".cards-container-live");

// começa mostrando só as gravadas
cardsAoVivo.style.display = "none";

toggleInput.addEventListener("change", () => {
  const mostrarAoVivo = toggleInput.checked;
  const esconder = mostrarAoVivo ? cardsGravadas : cardsAoVivo;
  const mostrar = mostrarAoVivo ? cardsAoVivo : cardsGravadas;

  mostrar.querySelectorAll('.ao-vivo').forEach(el => el.style.display = 'flex');

  // fade-out no que vai sair
  esconder.classList.add("fade-out");

  setTimeout(() => {
    esconder.style.display = "none";
    esconder.classList.remove("fade-out");

    mostrar.style.display = "grid";
    mostrar.classList.add("fade-out");
    setTimeout(() => mostrar.classList.remove("fade-out"), 50);
  }, 400);
});

const botaoGravar = document.querySelector(".gravar button");

toggleInput.addEventListener("change", () => {
  const mostrarAoVivo = toggleInput.checked;

  // troca o texto do botão
  botaoGravar.innerHTML = mostrarAoVivo
    ? `<i class="fa-solid fa-broadcast-tower"></i> Começar Aula`
    : `<i class="fa-solid fa-upload"></i> Enviar Gravação`;

  // se quiser mudar a cor também:
  botaoGravar.style.background = mostrarAoVivo ? "#b91c1c" : "#af2cd3be";

  // resto do seu código continua igual abaixo...
});

const modalGravacao = document.getElementById("modal-gravacao");
const modalAoVivo = document.getElementById("modal-aovivo");
const toggle = document.getElementById("toggle");

// abrir o modal certo
botaoGravar.addEventListener("click", () => {
  if (toggle.checked) {
    modalAoVivo.classList.remove("hidden"); // AO VIVO
  } else {
    modalGravacao.classList.remove("hidden"); // GRAVADAS
  }
});

// fechar
document.querySelectorAll(".close-modal, .btn-cancelar").forEach((btn) => {
  btn.addEventListener("click", () => {
    modalGravacao.classList.add("hidden");
    modalAoVivo.classList.add("hidden");
  });
});

