document.addEventListener('DOMContentLoaded', function() {
    console.log('Página inicial carregada.');
});


document.addEventListener('DOMContentLoaded', function() {
    const loginStatusElement = document.getElementById('login-status');
    const userId = localStorage.getItem('userId');

    if (!userId) {
        loginStatusElement.innerHTML = '<a href="../login/index.html">Fazer Login</a>';
    } else {
        fetch(`http://localhost:9091/usuarios/${userId}`)
            .then(response => response.json())
            .then(data => {
                loginStatusElement.innerHTML = `<a href="../perfil/perfil.html">${data.nomeUsuario}</a> | <a href="#" id="logout">Logout</a>`;
                
                document.getElementById('logout').addEventListener('click', function() {
                    localStorage.removeItem('userId');
                    alert('Logout realizado com sucesso!');
                    window.location.href = '../login/index.html';
                });
            })
            .catch(error => console.error('Erro ao buscar dados do usuário:', error));
    }
});


