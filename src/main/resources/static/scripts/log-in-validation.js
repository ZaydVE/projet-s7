document.getElementById('login-form').addEventListener('submit', function (e) {

    e.preventDefault();
    let isValid = true;

    // Email
    const email = document.getElementById('email').value.trim();
    if (!email) {
        document.getElementById('emailError').textContent = "L'email est obligatoire.";
        isValid = false;
    } else {
        document.getElementById('emailError').textContent = "";
    }

    // Mot de passe
    const password = document.getElementById('password').value;
    if (!password) {
        document.getElementById('passwordError').textContent = "Le mot de passe est obligatoire.";
        isValid = false;
    } else {
        document.getElementById('passwordError').textContent = "";
    }

    // Validation
    if (isValid) {
        this.submit();
    }

});