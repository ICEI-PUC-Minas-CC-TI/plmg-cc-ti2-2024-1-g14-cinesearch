document.getElementById('signUp').addEventListener('click', () => {
    document.querySelector('.container').classList.add('right-panel-active');
});

document.getElementById('signIn').addEventListener('click', () => {
    document.querySelector('.container').classList.remove('right-panel-active');
});

document.getElementById('signup-form').addEventListener('submit', function(event) {
    event.preventDefault();
    const nomeUsuario = document.getElementById('signup-usuario').value;
    const email = document.getElementById('signup-email').value;
    const senha = document.getElementById('signup-senha').value;

    fetch('http://localhost:9091/usuarios', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            nomeUsuario: nomeUsuario,
            email: email,
            senha: senha
        })
    })
    .then(response => {
        if (response.status === 201) {
            alert('Usuário criado com sucesso!');
        } else {
            alert('Erro ao criar usuário');
        }
    })
    .catch(error => console.error('Erro ao cadastrar usuário:', error));
});

document.getElementById('login-form').addEventListener('submit', function(event) {
    event.preventDefault();
    const email = document.getElementById('login-email').value;
    const senha = document.getElementById('login-senha').value;

    fetch('http://localhost:9091/usuarios/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            email: email,
            senha: senha
        })
    })
    .then(response => {
        if (response.status === 200) {
            return response.json(); // Retorna o JSON contendo o ID do usuário
        } else {
            throw new Error('Erro ao realizar login');
        }
    })
    .then(data => {
        const userId = data.userId; // Extrai o ID do usuário da resposta
        if (userId !== undefined) {
            localStorage.setItem('userId', userId); // Armazena o ID do usuário no local storage
            alert('Login realizado com sucesso!');
            window.location.href = '../perfil/perfil.html'; // Redireciona para a página de perfil após login
        } else {
            alert('Erro ao realizar login. ID de usuário não encontrado.');
        }
    })
    .catch(error => console.error('Erro ao fazer login:', error));
});

document.getElementById('voltar').addEventListener('click', function() {
    window.location.href = '../paginainicial/principal.html'; // Redireciona para a página principal
});
