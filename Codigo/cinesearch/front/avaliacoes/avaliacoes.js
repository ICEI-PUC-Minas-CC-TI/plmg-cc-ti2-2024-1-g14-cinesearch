document.addEventListener('DOMContentLoaded', function() {
    loadFilmes();
    document.getElementById('filme-select').addEventListener('change', loadAvaliacoes);
    document.getElementById('avaliacao-form').addEventListener('submit', enviarAvaliacao);
});

function loadFilmes() {
    fetch('http://localhost:9091/filmes')
        .then(response => response.json())
        .then(filmes => {
            const filmeSelect = document.getElementById('filme-select');
            filmes.forEach(filme => {
                const option = document.createElement('option');
                option.value = filme.id;
                option.textContent = filme.titulo;
                filmeSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Erro ao carregar filmes:', error));
}

function loadAvaliacoes() {
    const filmeId = document.getElementById('filme-select').value;
    if (!filmeId) return;

    fetch(`http://localhost:9091/avaliacoes/filmeAval/${filmeId}`)
        .then(response => response.json())
        .then(avaliacoes => {
            const avaliacoesContainer = document.getElementById('avaliacoes-container');
            avaliacoesContainer.innerHTML = ''; // Limpar avaliações anteriores
            if (avaliacoes.length === 0) {
                avaliacoesContainer.textContent = 'Nenhuma avaliação encontrada.';
                return;
            }

            avaliacoes.forEach(avaliacao => {
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
        })
        .catch(error => console.error('Erro ao carregar avaliações:', error));
}

function enviarAvaliacao(event) {
    event.preventDefault();
    const filmeId = document.getElementById('filme-select').value;
    const nota = document.getElementById('avaliacao-nota').value;
    const comentario = document.getElementById('avaliacao-comentario').value;
    const userId = localStorage.getItem('userId'); // Supondo que o userId esteja armazenado no localStorage

    if (!filmeId || !nota || !comentario || !userId) {
        alert('Por favor, preencha todos os campos.');
        return;
    }

    const avaliacao = {
        nota: parseInt(nota),
        comentario: comentario,
        filme_id: parseInt(filmeId),
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
            loadAvaliacoes(); // Recarregar avaliações após enviar a nova avaliação
        } else {
            alert('Erro ao enviar avaliação');
        }
    })
    .catch(error => console.error('Erro ao enviar avaliação:', error));
}

function getStars(nota) {
    let stars = '';
    for (let i = 0; i < 5; i++) {
        if (i < nota) {
            stars += '★'; // estrela cheia
        } else {
            stars += '☆'; // estrela vazia
        }
    }
    return stars;
}
