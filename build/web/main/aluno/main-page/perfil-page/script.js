document
        .getElementById("fileInput")
        .addEventListener("change", function (event) {
          const file = event.target.files[0];
          if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
              document.getElementById("profileImage").src = e.target.result;
            };
            reader.readAsDataURL(file);
          }
        });

// Abrir modal
document.getElementById("btnSenha").addEventListener("click", () => {
  document.getElementById("modalSenha").style.display = "flex";
});

// Botão Cancelar
document.getElementById("cancelarSenha").addEventListener("click", () => {
  document.getElementById("modalSenha").style.display = "none";
});

// Fechar ao clicar fora do conteúdo
const modal = document.getElementById("modalSenha");
const modalContent = document.querySelector("#modalSenha .modal-content");

modal.addEventListener("click", (e) => {
  if (!modalContent.contains(e.target)) {
    modal.style.display = "none";
  }
});

// (Opcional) Fechar com ESC
document.addEventListener("keydown", (e) => {
  if (e.key === "Escape") {
    modal.style.display = "none";
  }
});
