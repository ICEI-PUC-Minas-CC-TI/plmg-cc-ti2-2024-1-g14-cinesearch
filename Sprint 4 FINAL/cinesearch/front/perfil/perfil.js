document.addEventListener('DOMContentLoaded', function() {
    const userId = localStorage.getItem('userId');
    if (!userId) {
        alert('Usuário não logado!');
        window.location.href = '../login/index.html'; // Redireciona para a página de login
        return;
    }

    fetch(`http://localhost:9091/usuarios/${userId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na resposta da requisição');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('perfil-usuario').value = data.nomeUsuario;
            document.getElementById('perfil-email').value = data.email;
        })
        .catch(error => console.error('Erro ao buscar dados do usuário:', error));

    document.getElementById('perfil-form').addEventListener('submit', function(event) {
        event.preventDefault();
        const nomeUsuario = document.getElementById('perfil-usuario').value;
        const email = document.getElementById('perfil-email').value;
        const senha = document.getElementById('perfil-senha').value;

        fetch(`http://localhost:9091/usuarios/${userId}`, {
            method: 'PUT',
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
            if (response.status === 200) {
                alert('Informações atualizadas com sucesso!');
            } else {
                alert('Erro ao atualizar informações');
            }
        })
        .catch(error => console.error('Erro ao atualizar informações:', error));
    });

    document.getElementById('logout').addEventListener('click', function() {
        localStorage.removeItem('userId');
        alert('Logout realizado com sucesso!');
        window.location.href = '../login/index.html';
    });

    document.getElementById('delete-account').addEventListener('click', function() {
        if (confirm('Tem certeza que deseja deletar sua conta?')) {
            fetch(`http://localhost:9091/usuarios/${userId}`, {
                method: 'DELETE'
            })
            .then(response => {
                if (response.status === 200) {
                    localStorage.removeItem('userId');
                    alert('Conta deletada com sucesso!');
                    window.location.href = '../login/index.html';
                } else {
                    alert('Erro ao deletar conta');
                }
            })
            .catch(error => console.error('Erro ao deletar conta:', error));
        }
    });
});
