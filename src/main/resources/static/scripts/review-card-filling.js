// Sélection des éléments
const titleInput = document.getElementById('title-input');
const commentInput = document.getElementById('comment-input');
const destinationSelect = document.getElementById('destination-select');
const ratingInput = document.getElementById('rating-input');
const titleDisplay = document.getElementById('title-display');
const commentDisplay = document.getElementById('comment-display');
const bodyDisplay = document.getElementById('body');
const destinationDisplay = document.getElementById('destination-display');
const ratingDisplay = document.getElementById('rating-display');
const popup = document.getElementById('popup');
const popupComment = document.getElementById('popup-comment');
const popupClose = document.getElementById('popup-close');

// Initialisation
ratingDisplay.innerHTML = '<i class="fa-regular fa-star"></i>'.repeat(5);
titleDisplay.textContent = '';
commentDisplay.textContent = '';
destinationDisplay.innerHTML = '';

// MAJ automatique


// Titre
titleInput.addEventListener('input', () => {
    titleDisplay.textContent = titleInput.value || '';
});

// Commentaire
commentInput.addEventListener('input', () => {
    const fullComment = commentInput.value || '';
    commentDisplay.textContent = fullComment.length > 256
        ? fullComment.substring(0, 256) + ' ...'
        : fullComment;
});

// Destination
destinationSelect.addEventListener('change', () => {

    // Récupère l'option sélectionnée
    const selectedOption = destinationSelect.options[destinationSelect.selectedIndex];

    // Récupère le nom de la destination via l'attribut data-name
    const destinationName = selectedOption.getAttribute('data-name') || '';

    // Met à jour l'aperçu dynamique
    destinationDisplay.innerHTML = `<i class="fa-solid fa-location-dot"></i> ${destinationName}`;
});

// Note
ratingInput.addEventListener('input', () => {
    const rating = Math.min(Math.max(parseInt(ratingInput.value, 10) || 0, 1), 5);
    ratingDisplay.innerHTML =
        '<i class="fa-solid fa-star"></i>'.repeat(rating) +
        '<i class="fa-regular fa-star"></i>'.repeat(5 - rating);
});

// Pop-up

bodyDisplay.addEventListener('click', () => {
    popupComment.textContent = commentInput.value || 'Saisissez un commentaire.';
    popup.classList.remove('hidden');
});

popupClose.addEventListener('click', () => {
    popup.classList.add('hidden');
});

popup.addEventListener('click', (e) => {
    if (e.target === popup) {
        popup.classList.add('hidden');
    }
});