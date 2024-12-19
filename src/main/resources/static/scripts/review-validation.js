document.getElementById('form').addEventListener('submit', function (e) {

    e.preventDefault();
    let isValid = true;

    // Destination
    const destinationSelect = document.getElementById('destination-select');
    const destinationError = document.getElementById('destinationError');
    if (!destinationSelect.value) {
        destinationError.textContent = "Veuillez sélectionner une destination.";
        isValid = false;
    } else {
        destinationError.textContent = "";
    }

    // Note
    const ratingInput = document.getElementById('rating-input');
    const ratingError = document.getElementById('ratingError');
    const rating = parseInt(ratingInput.value);
    if (isNaN(rating) || rating < 1 || rating > 5) {
        ratingError.textContent = "Veuillez entrer une note entre 1 et 5.";
        isValid = false;
    } else {
        ratingError.textContent = "";
    }

    // Titre
    const titleInput = document.getElementById('title-input').value.trim();
    const titleError = document.getElementById('titleError');
    if (!titleInput || titleInput.length > 50) {
        titleError.textContent = "Le titre doit contenir entre 1 et 50 caractères.";
        isValid = false;
    } else {
        titleError.textContent = "";
    }

    // Commentaire
    const commentInput = document.getElementById('comment-input').value.trim();
    const commentError = document.getElementById('commentError');
    if (!commentInput || commentInput.length > 1500) {
        commentError.textContent = "Le commentaire doit contenir entre 1 et 1500 caractères.";
        isValid = false;
    } else {
        commentError.textContent = "";
    }

    // Validation
    if (isValid) {
        this.submit();
    }

});