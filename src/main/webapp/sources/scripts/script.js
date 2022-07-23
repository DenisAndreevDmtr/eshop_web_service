function validateForm() {
    var errorCounter = 0;

    errorCounter += validateConfirmPassword();
    errorCounter += validateEmail();


    if (errorCounter > 0) {
        return false;
    }
    return true;
}

function validateConfirmPassword() {
    var errorCounter = 0;
    var nameFld = document.getElementById("confirmpassword");
    var nameFld2 = document.getElementById("password");
    if (nameFld.value != nameFld2.value) {
        nameFld.classList.add('is-invalid');
        errorCounter++;
    }
    return errorCounter;
}

function validateEmail() {
    var errorCounter = 0;
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    var emailFld = document.getElementById("email");

    if (emailFld.value <= 0) {
        emailFld.classList.add('is-invalid');
        errorCounter++;
    } else {
        if (!re.test(String(emailFld.value).toLowerCase())) {
            emailFld.classList.add('is-invalid');
            errorCounter++;
        }
    }
    return errorCounter;
}













