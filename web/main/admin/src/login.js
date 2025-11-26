document.getElementById("login-form").addEventListener("submit", async (e) => {
  e.preventDefault();

  const email = document.getElementById("email").value.trim();
  const senha = document.getElementById("senha").value.trim();
  const erro = document.getElementById("erro");

  const resposta = await fetch("/api/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, senha }),
  });

  const data = await resposta.json();

  if (data.success && data.isAdmin) {
    window.location.href = "/admin-page/index.html";
  } else if (data.success && !data.isAdmin) {
    erro.textContent = "Acesso negado. Você não é administrador.";
  } else {
    erro.textContent = "Email ou senha incorretos.";
  }
});
