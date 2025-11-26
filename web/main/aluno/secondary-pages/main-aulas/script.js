
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