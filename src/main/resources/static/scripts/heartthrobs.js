/* Fonction permettant de changer le pays affiché */

function showHeartthrobs(category) {

    /* Sélection des éléments */
    const descriptions = document.querySelectorAll('.s3-description');
    const bentos = document.querySelectorAll('.bento');
    const links = document.querySelectorAll('.section-3-view-link');
    const buttons = document.querySelectorAll('.list-heartthrobs button');

    /* Affiche la description du pays sélectionné */
    descriptions.forEach(description => {
        if (description.getAttribute('category') === category) {
            description.style.display = 'block';
        } else {
            description.style.display = 'none';
        }
    });

    /* Affiche le bento du pays sélectionné */
    bentos.forEach(bento => {
        if (bento.getAttribute('category') === category) {
            bento.style.display = 'grid';
        } else {
            bento.style.display = 'none';
        }
    });

    /* Affiche le lien du pays sélectionné */
    links.forEach(link => {
        if (link.getAttribute('category') === category) {
            link.style.display = 'block';
        } else {
            link.style.display = 'none';
        }
    });

    /* Modifie le style du bouton du pays sélectionné */
    buttons.forEach(button => {
        if (button.getAttribute('category') === category) {
            button.classList.add('active');
        } else {
            button.classList.remove('active');
        }
    });
}

/* Pays affiché au chargement du DOM : Italie */

document.addEventListener('DOMContentLoaded', () => {
    showHeartthrobs('IT');
});