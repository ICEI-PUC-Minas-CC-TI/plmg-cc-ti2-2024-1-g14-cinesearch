document.addEventListener('DOMContentLoaded', function() {
    fetchFilmes();
});

function fetchFilmes() {
    fetch('http://localhost:9091/filmes')
        .then(response => response.json())
        .then(data => {
            console.log('Filmes recebidos:', data);
            const filmesDiv = document.getElementById('filmes');
            data.forEach(filme => {
                const filmeCard = document.createElement('div');
                filmeCard.className = 'filme-card';

                const filmeImg = document.createElement('img');
                if (filme.poster) {
                    filmeImg.src = 'data:image/jpeg;base64,' + arrayBufferToBase64(filme.poster);
                } else {
                    filmeImg.alt = 'Sem imagem disponível';
                }

                const filmeTitle = document.createElement('h3');
                filmeTitle.textContent = filme.titulo;

                const filmeDescription = document.createElement('p');
                filmeDescription.textContent = filme.sinopse;

                filmeCard.appendChild(filmeImg);
                filmeCard.appendChild(filmeTitle);
                filmeCard.appendChild(filmeDescription);

                filmesDiv.appendChild(filmeCard);
            });
        })
        .catch(error => console.error('Erro ao buscar filmes:', error));
}

function arrayBufferToBase64(buffer) {
    var binary = '';
    var bytes = new Uint8Array(buffer);
    var len = bytes.byteLength;
    for (var i = 0; i < len; i++) {
        binary += String.fromCharCode(bytes[i]);
    }
    return window.btoa(binary);
}
