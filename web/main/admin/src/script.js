
const isAdmin = localStorage.getItem('isAdmin') === 'true';
if (!isAdmin) {
  // se não é admin, redireciona pra login
  window.location.href = 'index.html';
}

const userNameEl = document.getElementById('userName');
const btnLogout = document.getElementById('btnLogout');

userNameEl.textContent = localStorage.getItem('userName') || 'Administrador';

btnLogout.addEventListener('click', () => {
  // Limpe apenas as chaves relacionadas à sessão (não apague dados do app se não quiser)
  localStorage.removeItem('isAdmin');
  localStorage.removeItem('userName');
  // redireciona para tela de login
  window.location.href = '/admin-page/index.html';
});

const menuItems = document.querySelectorAll('.menu-item');
const panels = document.querySelectorAll('.panel');

function activatePanelFromHash() {
  const hash = window.location.hash.replace('#', '') || 'dashboard';
  panels.forEach(p => p.classList.toggle('active-panel', p.id === hash));
  menuItems.forEach(mi => {
    const target = mi.getAttribute('href') ? mi.getAttribute('href').replace('#','') : null;
    mi.classList.toggle('active', target === hash);
  });
}
window.addEventListener('hashchange', activatePanelFromHash);
activatePanelFromHash();

function getList(key){ return JSON.parse(localStorage.getItem(key) || '[]'); }
function setList(key, arr){ localStorage.setItem(key, JSON.stringify(arr)); }

const KEYS = {
  professores: 'edumind_professores',
  alunos: 'edumind_alunos',
  turmas: 'edumind_turmas',
  disciplinas: 'edumind_disciplinas'
};

function updateCounts(){
  document.getElementById('count-professores').textContent = getList(KEYS.professores).length;
  document.getElementById('count-alunos').textContent = getList(KEYS.alunos).length;
  document.getElementById('count-turmas').textContent = getList(KEYS.turmas).length;
  document.getElementById('count-disciplinas').textContent = getList(KEYS.disciplinas).length;
}

function renderList(key, ulSelector, mapFn){
  const ul = document.getElementById(ulSelector);
  ul.innerHTML = '';
  const items = getList(key);
  if (items.length === 0){
    ul.innerHTML = '<li><small>Nenhum registro.</small></li>';
    return;
  }
  items.forEach((it, idx) => {
    const li = document.createElement('li');

    const meta = document.createElement('div');
    meta.className = 'meta';
    meta.innerHTML = mapFn(it);

    const actions = document.createElement('div');
    actions.className = 'actions';

    const delBtn = document.createElement('button');
    delBtn.className = 'icon-btn';
    delBtn.innerHTML = '<i class="fa-solid fa-trash"></i>';
    delBtn.title = 'Remover';
    delBtn.addEventListener('click', () => {
      if (!confirm('Remover este registro?')) return;
      const arr = getList(key);
      arr.splice(idx,1);
      setList(key, arr);
      refreshAll();
    });

    actions.appendChild(delBtn);
    li.appendChild(meta);
    li.appendChild(actions);
    ul.appendChild(li);
  });
}

function refreshAll(){
  updateCounts();
  renderList(KEYS.professores, 'lista-professores', p => `<strong>${escapeHtml(p.nome)}</strong><small>${escapeHtml(p.disciplina)} • ${escapeHtml(p.email)}</small>`);
  renderList(KEYS.alunos, 'lista-alunos', a => `<strong>${escapeHtml(a.nome)}</strong><small>${escapeHtml(a.turma)} • ${escapeHtml(a.email)}</small>`);
  renderList(KEYS.turmas, 'lista-turmas', t => `<strong>${escapeHtml(t.nome)}</strong><small>Ano: ${escapeHtml(t.ano)}</small>`);
  renderList(KEYS.disciplinas, 'lista-disciplinas', d => `<strong>${escapeHtml(d.nome)}</strong><small>Código: ${escapeHtml(d.codigo)}</small>`);
}
refreshAll();

function wireForm(formId, key, mapper){
  const form = document.getElementById(formId);
  form.addEventListener('submit', e => {
    e.preventDefault();
    const formData = new FormData(form);
    const obj = mapper(Object.fromEntries(formData));
    const arr = getList(key);
    arr.push(obj);
    setList(key, arr);
    form.reset();
    refreshAll();
  });
}

wireForm('form-professor', KEYS.professores, raw => ({
  nome: raw.nome.trim(),
  email: raw.email.trim(),
  disciplina: raw.disciplina.trim()
}));

wireForm('form-aluno', KEYS.alunos, raw => ({
  nome: raw.nome.trim(),
  email: raw.email.trim(),
  turma: raw.turma.trim()
}));

wireForm('form-turma', KEYS.turmas, raw => ({
  nome: raw.nome.trim(),
  ano: raw.ano.trim()
}));

wireForm('form-disciplina', KEYS.disciplinas, raw => ({
  nome: raw.nome.trim(),
  codigo: raw.codigo.trim()
}));

/* ---------- CLEAR ALL BUTTONS ---------- */
document.getElementById('limpar-professores').addEventListener('click', () => {
  if (confirm('Limpar todos os professores?')) { setList(KEYS.professores, []); refreshAll(); }
});
document.getElementById('limpar-alunos').addEventListener('click', () => {
  if (confirm('Limpar todos os alunos?')) { setList(KEYS.alunos, []); refreshAll(); }
});
document.getElementById('limpar-turmas').addEventListener('click', () => {
  if (confirm('Limpar todas as turmas?')) { setList(KEYS.turmas, []); refreshAll(); }
});
document.getElementById('limpar-disciplinas').addEventListener('click', () => {
  if (confirm('Limpar todas as disciplinas?')) { setList(KEYS.disciplinas, []); refreshAll(); }
});

function escapeHtml(text){
  if (!text) return '';
  return String(text)
    .replace(/&/g,'&amp;')
    .replace(/</g,'&lt;')
    .replace(/>/g,'&gt;')
    .replace(/"/g,'&quot;')
    .replace(/'/g,'&#039;');
}

document.querySelectorAll('.menu-item').forEach(mi=>{
  mi.addEventListener('click', (e)=>{
    const href = (mi.getAttribute('href')||'').replace('#','');
    if (href) {
      setTimeout(()=> window.scrollTo({top:0, behavior:'smooth'}), 50);
    }
  });
});

document.getElementById('searchInput').addEventListener('input', (e) => {
  const q = e.target.value.trim().toLowerCase();
  if (!q) {
    refreshAll();
    return;
  }

  const filterAndRender = (key, ulId, predicate) => {
    const ul = document.getElementById(ulId);
    ul.innerHTML = '';
    const items = getList(key).filter(predicate);
    if (items.length === 0) { ul.innerHTML = '<li><small>Nenhum resultado.</small></li>'; return; }
    items.forEach((it, idx)=>{
      const li = document.createElement('li');
      const meta = document.createElement('div'); meta.className='meta';
      meta.innerHTML = predicate === null ? '' : `<strong>${escapeHtml(it.nome || it.codigo || '')}</strong><small>${escapeHtml(JSON.stringify(it))}</small>`;
      const actions = document.createElement('div'); actions.className='actions';
      const del = document.createElement('button'); del.className='icon-btn'; del.innerHTML = '<i class="fa-solid fa-trash"></i>';
      del.addEventListener('click', () => {
        if (!confirm('Remover este registro?')) return;
        const arr = getList(key);
        arr.splice(idx,1); setList(key, arr); refreshAll();
      });
      actions.appendChild(del);
      li.appendChild(meta); li.appendChild(actions); ul.appendChild(li);
    });
  };

  filterAndRender(KEYS.professores, 'lista-professores', it =>
    `${it.nome} ${it.email} ${it.disciplina}`.toLowerCase().includes(q)
  );
  filterAndRender(KEYS.alunos, 'lista-alunos', it =>
    `${it.nome} ${it.email} ${it.turma}`.toLowerCase().includes(q)
  );
  filterAndRender(KEYS.turmas, 'lista-turmas', it =>
    `${it.nome} ${it.ano}`.toLowerCase().includes(q)
  );
  filterAndRender(KEYS.disciplinas, 'lista-disciplinas', it =>
    `${it.nome} ${it.codigo}`.toLowerCase().includes(q)
  );
  document.getElementById('count-professores').textContent = getList(KEYS.professores).filter(it => `${it.nome} ${it.email} ${it.disciplina}`.toLowerCase().includes(q)).length;
  document.getElementById('count-alunos').textContent = getList(KEYS.alunos).filter(it => `${it.nome} ${it.email} ${it.turma}`.toLowerCase().includes(q)).length;
});

/* ---------- inicial ---------- */
refreshAll();

// ===========================================
// Lógica para Navegação de Abas (Manter)
// ===========================================
document.querySelectorAll('.menu-item').forEach(item => {
    item.addEventListener('click', function(e) {
        e.preventDefault();
        
        // Remove a classe 'active' de todos os itens do menu
        document.querySelectorAll('.menu-item').forEach(el => el.classList.remove('active'));
        this.classList.add('active');
        
        // Esconde todos os painéis de conteúdo
        document.querySelectorAll('.panel').forEach(panel => panel.classList.remove('active-panel'));
        
        // Mostra o painel correspondente ao href
        const targetId = this.getAttribute('href').substring(1);
        document.getElementById(targetId).classList.add('active-panel');
    });
});


// ===========================================
// Lógica para Cadastro de Professor (AJAX)
// ===========================================
const formProfessor = document.getElementById('form-professor');
const msgProfessor = document.getElementById('msg-professor');

if (formProfessor) {
    formProfessor.addEventListener('submit', function(e) {
        e.preventDefault(); // Impede o envio HTTP padrão

        const formData = new FormData(this); // Cria um objeto com os dados do formulário
        
        // Adiciona a ação para o servidor saber o que fazer
        formData.append('acao', 'cadastrarProfessor'); 

        // Limpa mensagens anteriores
        msgProfessor.textContent = 'Processando...';
        msgProfessor.style.color = '#333';

        // Envia a requisição via Fetch API
        fetch('professor-api.jsp', { // Novo arquivo JSP/Servlet que criaremos
            method: 'POST',
            body: new URLSearchParams(formData) // Converte FormData para o formato URL-encoded
        })
        .then(response => response.json()) // Espera uma resposta JSON do servidor
        .then(data => {
            if (data.sucesso) {
                msgProfessor.textContent = data.mensagem;
                msgProfessor.style.color = 'green';
                formProfessor.reset(); // Limpa o formulário
                // Aqui você pode adicionar o código para atualizar a lista de professores na tela
            } else {
                msgProfessor.textContent = data.mensagem || 'Erro ao cadastrar.';
                msgProfessor.style.color = 'red';
            }
        })
        .catch(error => {
            console.error('Erro na requisição:', error);
            msgProfessor.textContent = 'Falha na comunicação com o servidor.';
            msgProfessor.style.color = 'red';
        });
    });
}