// Verificar se há avaliações de filmes armazenadas no localStorage
const storedReviews = localStorage.getItem('movieReviews');
const reviews = storedReviews ? JSON.parse(storedReviews) : [];

// Selecionar o elemento onde as avaliações serão inseridas
const movieReviewsList = document.getElementById('movieReviewsList');

// Função para renderizar as avaliações
function renderReviews() {
  movieReviewsList.innerHTML = '';

  if (reviews.length > 0) {
    // Iterar sobre cada avaliação e adicioná-la à lista
    reviews.forEach((review, index) => {
      const movieId = review.movieId;
      const movieName = review.movieName;
      const posterUrl = review.posterUrl;
      const rating = review.rating;
      const reviewText = review.review;

      // Criar um elemento de card para exibir a avaliação
      const card = document.createElement('div');
      card.classList.add('cards');

      // Gerar estrelas com base na avaliação
      const stars = getStars(rating);

      // Conteúdo do card
      card.innerHTML = `
        <section class="cards" data-movie-id="${movieId}">
          <div class="cards__parte1">
            <img src="${posterUrl}" alt="${movieName}" class="cards__parte1-capa">
          </div>
          <div class="cards__parte2">
            <h2 class="cards__parte2-titulo">${movieName}</h2>
            <div class="cards__parte2-details">
              <div class="rating">${stars}</div>
            </div>
            <p class="cards__parte3-texto">${reviewText}</p>
            <button class="delete-review">Excluir</button>
          </div>
        </section>
      `;

      // Adicionar evento de clique no botão de exclusão
      const deleteButton = card.querySelector('.delete-review');
      deleteButton.addEventListener('click', () => {
        deleteReview(index);
      });

      // Adicionar o card à lista de avaliações
      movieReviewsList.appendChild(card);
    });
  } else {
    // Se não houver avaliações de filmes, mostrar uma mensagem
    movieReviewsList.innerHTML = '<p>Você ainda não avaliou nenhum filme.</p>';
  }
}

// Função para gerar estrelas com base na avaliação
function getStars(rating) {
  const starFull = '<i class="fa fa-star"></i>';
  const starEmpty = '<i class="fa fa-star-o"></i>';
  let stars = '';

  for (let i = 1; i <= 5; i++) {
    stars += i <= rating ? starFull : starEmpty;
  }
  return stars;
}

// Função para excluir uma avaliação
function deleteReview(index) {
  reviews.splice(index, 1);
  localStorage.setItem('movieReviews', JSON.stringify(reviews));
  renderReviews();
}

// Renderizar as avaliações ao carregar a página
renderReviews();
