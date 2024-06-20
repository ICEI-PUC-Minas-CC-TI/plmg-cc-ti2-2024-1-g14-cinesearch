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
    const modal = document.getElementById("modal");
    const modalContent = document.getElementById("modal-content");
    const span = document.getElementsByClassName("close-button")[0];

    span.onclick = function() {
        modal.style.display = "none";
        modalContent.classList.remove("show");
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
            modalContent.classList.remove("show");
        }
    }

    document.getElementById('search-bar').addEventListener('input', filterFilmes);
    document.getElementById('prev-page').addEventListener('click', prevPage);
    document.getElementById('next-page').addEventListener('click', nextPage);
});

let allFilmes = [];
let filteredFilmes = [];
let currentPage = 1;
const filmesPerPage = 20;

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

        // Poster do filme
        const filmeImg = document.createElement('img');
        filmeImg.src = filme.poster ? filme.poster : '../imagens/placeholder.jpg';
        filmeImg.alt = 'Poster do filme';

        // Título e ano do filme
        const filmeDetails = document.createElement('div');
        filmeDetails.className = 'filme-details';

        const filmeTitle = document.createElement('h3');
        filmeTitle.textContent = filme.titulo;

        const filmeAno = document.createElement('p');
        filmeAno.textContent = `Ano: ${filme.ano}`;

        filmeDetails.appendChild(filmeTitle);
        filmeDetails.appendChild(filmeAno);

        // Informações do filme
        const filmeInfo = document.createElement('div');
        filmeInfo.className = 'filme-info';

        const descricaoTitle = document.createElement('h4');
        descricaoTitle.textContent = 'Descrição';

        const filmeDescricao = document.createElement('p');
        filmeDescricao.textContent = filme.sinopse;

        const saberMaisBtn = document.createElement('button');
        saberMaisBtn.textContent = 'Saber Mais';
        saberMaisBtn.onclick = () => showModal(filme);

        filmeInfo.appendChild(descricaoTitle);
        filmeInfo.appendChild(filmeDescricao);
        filmeInfo.appendChild(saberMaisBtn);

        filmeCard.appendChild(filmeImg);
        filmeCard.appendChild(filmeDetails);
        filmeCard.appendChild(filmeInfo);

        filmesDiv.appendChild(filmeCard);
    });

    updatePaginationButtons();
}

function showModal(filme) {
    const modal = document.getElementById("modal");
    const modalContent = document.getElementById("modal-content");
    const modalBody = document.getElementById("modal-body");
    modalBody.innerHTML = '';

    // Poster do filme
    const filmeImg = document.createElement('img');
    filmeImg.src = filme.poster ? filme.poster : '../imagens/placeholder.jpg';
    filmeImg.alt = 'Poster do filme';
    filmeImg.style.width = '100px';

    // Título do filme
    const filmeTitle = document.createElement('h2');
    filmeTitle.textContent = filme.titulo;

    // Ano de lançamento
    const filmeAno = document.createElement('p');
    filmeAno.textContent = `Ano: ${filme.ano}`;

    // Duração do filme
    const filmeDuracao = document.createElement('p');
    filmeDuracao.textContent = `Duração: ${filme.duracao}`;

    // Sinopse do filme
    const filmeSinopse = document.createElement('p');
    filmeSinopse.textContent = `Sinopse: ${filme.sinopse}`;

    // Idioma do filme
    const filmeIdioma = document.createElement('p');
    filmeIdioma.textContent = `Idioma: ${filme.idioma}`;

    // Prêmios do filme
    const filmePremios = document.createElement('p');
    filmePremios.textContent = `Prêmios: ${filme.premios}`;

    // Nota do filme
    const filmeNota = document.createElement('p');
    filmeNota.textContent = `Nota: ${filme.nota}`;

    // Popularidade do filme
    const filmePopularidade = document.createElement('p');
    filmePopularidade.textContent = `Popularidade: ${filme.popularidade}`;

    // Atores do filme
    if (filme.atores && filme.atores.length > 0) {
        const atoresTitle = document.createElement('h3');
        atoresTitle.textContent = 'Atores';
        const atoresDiv = document.createElement('div');
        atoresDiv.className = 'atores-lista';

        filme.atores.forEach(ator => {
            const atorDiv = document.createElement('div');
            atorDiv.className = 'ator-card';

            const atorImg = document.createElement('img');
            atorImg.src = ator.foto ? ator.foto : '../imagens/placeholder.jpg';
            atorImg.alt = ator.nome;
            atorImg.style.width = '100px';

            const atorNome = document.createElement('p');
            atorNome.textContent = ator.nome;

            atorDiv.appendChild(atorImg);
            atorDiv.appendChild(atorNome);
            atoresDiv.appendChild(atorDiv);
        });

        modalBody.appendChild(atoresTitle);
        modalBody.appendChild(atoresDiv);
    }

    modalBody.appendChild(filmeImg);
    modalBody.appendChild(filmeTitle);
    modalBody.appendChild(filmeAno);
    modalBody.appendChild(filmeDuracao);
    modalBody.appendChild(filmeSinopse);
    modalBody.appendChild(filmeIdioma);
    modalBody.appendChild(filmePremios);
    modalBody.appendChild(filmeNota);
    modalBody.appendChild(filmePopularidade);

    modal.style.display = "block";
    modalContent.classList.add("show");
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
