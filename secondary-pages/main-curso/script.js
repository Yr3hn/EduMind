const cards = document.querySelectorAll(".card");
const cardContent = document.querySelector(".card-content");

cards.forEach((card) => {
  card.addEventListener("click", () => {
    document.querySelector(".card-wrapper").style.display = "none";
    cardContent.classList.add("show");
  });
});

document.querySelector(".card-content button").addEventListener("click", () => {
  cardContent.classList.remove("show");
  document.querySelector(".card-wrapper").style.display = "grid";
});

