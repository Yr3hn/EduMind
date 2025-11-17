// hamburguer funcional

const hamburger = document.getElementById("hamburger");
const menuLateral = document.getElementById("menu-lateral");
const overlay = document.createElement("div");
overlay.classList.add("overlay");
document.body.appendChild(overlay);

hamburger.addEventListener("click", () => {
  menuLateral.classList.toggle("active");
  overlay.classList.toggle("active");
});

overlay.addEventListener("click", () => {
  menuLateral.classList.remove("active");
  overlay.classList.remove("active");
});
