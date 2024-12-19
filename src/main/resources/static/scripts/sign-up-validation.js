document.getElementById('registration-form').addEventListener('submit', function (e) {

    e.preventDefault();
    let isValid = true;

    // Nom
    const lastname = document.getElementById('lastname').value.trim();
    if (!lastname) {
        document.getElementById('lastnameError').textContent = "Le nom est obligatoire.";
        isValid = false;
    } else if (lastname.length > 45) {
        document.getElementById('lastnameError').textContent = "Le nom est trop long.";
        isValid = false;
    } else {
        document.getElementById('lastnameError').textContent = "";
    }

    // Prénom
    const firstname = document.getElementById('firstname').value.trim();
    if (!lastname) {
        document.getElementById('firstnameError').textContent = "Le prénom est obligatoire.";
        isValid = false;
    } else if (lastname.length > 45) {
        document.getElementById('firstnameError').textContent = "Le prénom est trop long.";
        isValid = false;
    } else {
        document.getElementById('firstnameError').textContent = "";
    }

    // Email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const email = document.getElementById('email').value.trim();
    document.getElementById('emailError').textContent = emailRegex.test(email) ? "" : "L'email est invalide.";
    isValid &= emailRegex.test(email);

    // Téléphone
    const phoneRegex = /^\d{10}$/;
    const phonenumber = document.getElementById('phonenumber').value.trim();
    document.getElementById('phoneError').textContent = phoneRegex.test(phonenumber) ? "" : "Le numéro de téléphone est invalide.";
    isValid &= phoneRegex.test(phonenumber);

    // Mot de passe
    const password = document.getElementById('password').value;
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    document.getElementById('passwordError').textContent = passwordRegex.test(password) ? "" : "Le mot de passe doit contenir au moins 8 caractères, dont une majuscule, un chiffre et un caractère spécial.";
    isValid &= passwordRegex.test(password);

    // Confirmer le mot de passe
    const confirmPassword = document.getElementById('confirmPassword').value;
    document.getElementById('confirmPasswordError').textContent = (password === confirmPassword) ? "" : "Les mots de passe ne correspondent pas.";
    isValid &= password === confirmPassword;

    // Validation
    if (isValid) {
        this.submit();
    }

});