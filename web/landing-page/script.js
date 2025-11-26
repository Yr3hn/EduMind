const hamburger = document.querySelector(".hamburger");
const navLinks = document.querySelector(".nav-links");

hamburger.addEventListener("click", () => {
  navLinks.classList.toggle("show");
});

function animateOnScroll(selector, delay = 150) {
  const elements = document.querySelectorAll(selector);

  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (
          entry.isIntersecting &&
          !entry.target.classList.contains("appear")
        ) {
          const index = Array.from(elements).indexOf(entry.target);
          setTimeout(() => {
            entry.target.classList.add("appear");
          }, index * delay);
        }
      });
    },
    { threshold: 0.2 }
  );

  elements.forEach((el) => observer.observe(el));
}

window.addEventListener("DOMContentLoaded", () => {
  animateOnScroll(".card"); // cards
  animateOnScroll(".main-testmonial .foto img", 0);
  animateOnScroll(".main-testmonial .testmonial-text", 200);
});