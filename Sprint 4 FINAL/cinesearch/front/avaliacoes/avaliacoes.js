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

    fetchFilmes();
    document.getElementById('search-bar').addEventListener('input', filterFilmes);
    document.getElementById('prev-page').addEventListener('click', prevPage);
    document.getElementById('next-page').addEventListener('click', nextPage);
    document.getElementById('avaliacao-submit').addEventListener('click', enviarAvaliacao);

    const avaliacaoModal = document.getElementById('avaliacao-modal');
    const verAvaliacoesModal = document.getElementById('ver-avaliacoes-modal');
    const closeButtons = document.querySelectorAll('.close-button');

    closeButtons.forEach(button => {
        button.onclick = function() {
            avaliacaoModal.style.display = 'none';
            verAvaliacoesModal.style.display = 'none';
        };
    });

    window.onclick = function(event) {
        if (event.target == avaliacaoModal || event.target == verAvaliacoesModal) {
            avaliacaoModal.style.display = 'none';
            verAvaliacoesModal.style.display = 'none';
        }
    };

    // Rating stars logic
    document.querySelectorAll('.star').forEach(star => {
        star.addEventListener('click', toggleStar);
    });
});

let allFilmes = [];
let filteredFilmes = [];
let currentPage = 1;
const filmesPerPage = 20;
let currentFilmeId = null;
let selectedRating = 0;

function fetchFilmes() {
    fetch('http://localhost:9091/filmes')
        .then(response => response.json())
        .then(data => {
            console.log('Filmes recebidos:', data);
            allFilmes = data;
            filteredFilmes = data;
            displayFilmes(filteredFilmes, currentPage);
        })
        .catch(error => console.error('Erro ao buscar filmes:', error));
}

function displayFilmes(filmes, page) {
    const filmesDiv = document.getElementById('filmes');
    filmesDiv.innerHTML = ''; // Limpa a lista de filmes antes de adicionar os novos

    const start = (page - 1) * filmesPerPage;
    const end = start + filmesPerPage;
    const filmesToDisplay = filmes.slice(start, end);

    filmesToDisplay.forEach(filme => {
        const filmeCard = document.createElement('div');
        filmeCard.className = 'filme-card';
        filmeCard.setAttribute('data-id', filme.id);

        // Título e ano do filme
        const filmeDetails = document.createElement('div');
        filmeDetails.className = 'filme-details';

        const filmeTitle = document.createElement('h3');
        filmeTitle.textContent = filme.titulo;

        const filmeAno = document.createElement('p');
        filmeAno.textContent = `Ano: ${filme.ano}`;

        filmeDetails.appendChild(filmeTitle);
        filmeDetails.appendChild(filmeAno);

        // Botão Ver Avaliações
        const verAvaliacoesBtn = document.createElement('button');
        verAvaliacoesBtn.textContent = 'Ver Avaliações';
        verAvaliacoesBtn.onclick = () => showAvaliacoesModal(filme.id);

        // Botão Deixar Avaliação
        const deixarAvaliacaoBtn = document.createElement('button');
        deixarAvaliacaoBtn.textContent = 'Deixar uma Avaliação';
        deixarAvaliacaoBtn.onclick = () => showAvaliacaoForm(filme.id);

        filmeCard.appendChild(filmeDetails);
        filmeCard.appendChild(verAvaliacoesBtn);
        filmeCard.appendChild(deixarAvaliacaoBtn);

        filmesDiv.appendChild(filmeCard);
    });

    updatePaginationButtons();
}

function showAvaliacoesModal(filmeId) {
    const modal = document.getElementById('ver-avaliacoes-modal');
    const avaliacoesContainer = document.getElementById('avaliacoes-container');
    avaliacoesContainer.innerHTML = ''; // Limpa o conteúdo anterior

    fetch(`http://localhost:9091/avaliacoes/filmeAval/${filmeId}`)
        .then(response => response.json())
        .then(avaliacoes => {
            if (avaliacoes.length === 0) {
                avaliacoesContainer.textContent = 'Nenhuma avaliação encontrada.';
            } else {
                avaliacoes.reverse().forEach(avaliacao => {
                    const avaliacaoCard = document.createElement('div');
                    avaliacaoCard.className = 'avaliacao-card';

                    const avaliacaoNota = document.createElement('h3');
                    avaliacaoNota.innerHTML = `Nota: ${getStars(avaliacao.nota)}`;

                    const avaliacaoComentario = document.createElement('p');
                    avaliacaoComentario.textContent = avaliacao.comentario;

                    avaliacaoCard.appendChild(avaliacaoNota);
                    avaliacaoCard.appendChild(avaliacaoComentario);

                    avaliacoesContainer.appendChild(avaliacaoCard);
                });
            }
            modal.style.display = 'block';
        })
        .catch(error => console.error('Erro ao carregar avaliações:', error));
}

function showAvaliacaoForm(filmeId) {
    currentFilmeId = filmeId;
    const modal = document.getElementById('avaliacao-modal');
    modal.style.display = 'block';
}

function enviarAvaliacao() {
    const comentario = document.getElementById('avaliacao-comentario').value;
    const userId = localStorage.getItem('userId');

    if (!currentFilmeId || selectedRating === null || comentario.trim() === '' || !userId) {
        alert('Por favor, preencha todos os campos.');
        return;
    }

    const avaliacao = {
        nota: selectedRating,
        comentario: comentario,
        filme_id: parseInt(currentFilmeId),
        usuario_id: parseInt(userId)
    };

    fetch('http://localhost:9091/avaliacoes', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(avaliacao)
    })
    .then(response => {
        if (response.status === 201) {
            alert('Avaliação enviada com sucesso!');
            const modal = document.getElementById('avaliacao-modal');
            modal.style.display = 'none';
            loadAvaliacoes(currentFilmeId, document.querySelector(`[data-id='${currentFilmeId}']`));
        } else {
            alert('Erro ao enviar avaliação');
        }
    })
    .catch(error => console.error('Erro ao enviar avaliação:', error));
}

function toggleStar(event) {
    const star = event.target;
    star.classList.toggle('selected');
    const stars = Array.from(document.querySelectorAll('.star'));

    selectedRating = stars.reduce((acc, star, index) => {
        return star.classList.contains('selected') ? index + 1 : acc;
    }, 0);
}

function getStars(nota) {
    let stars = '';
    for (let i = 0; i < 5; i++) {
        if (i < nota) {
            stars += '★';
        } else {
            stars += '☆';
        }
    }
    return stars;
}

function filterFilmes() {
    const searchText = document.getElementById('search-bar').value.toLowerCase();

    filteredFilmes = allFilmes.filter(filme => {
        return filme.titulo.toLowerCase().includes(searchText);
    });

    currentPage = 1; // Reset to first page when search is performed
    displayFilmes(filteredFilmes, currentPage);
}

function updatePaginationButtons() {
    const totalPages = Math.ceil(filteredFilmes.length / filmesPerPage);
    document.getElementById('prev-page').disabled = currentPage === 1;
    document.getElementById('next-page').disabled = currentPage === totalPages;
}

function prevPage() {
    if (currentPage > 1) {
        currentPage--;
        displayFilmes(filteredFilmes, currentPage);
    }
}

function nextPage() {
    const totalPages = Math.ceil(filteredFilmes.length / filmesPerPage);
    if (currentPage < totalPages) {
        currentPage++;
        displayFilmes(filteredFilmes, currentPage);
    }
}
