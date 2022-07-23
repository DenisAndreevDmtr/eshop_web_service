<!DOCTYPE html>
<html lang="en">
<head>
    <title>Register</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../../sources/scripts/script.js"></script>
    <style>
        span.error {
            color: red;
        }
    </style>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<div class="container">
    <div class="col-md-8 offset-md-4">
        <h2>Register</h2>

        <form method="post" action="${contextPath}/register" class="needs-validation" novalidate>
            <div class="form-group">
                <label for="login">Login:</label>
                <input type="text" class="form-control w-25" id="login" placeholder="Enter login" name="login"
                       required>
                <div class="invalid-feedback">Login should be entered!</div>
                <span class="error">${loginError}</span>
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <input type="text" class="form-control w-25" id="password" placeholder="Enter password" name="password"
                       required>
                <div class="invalid-feedback">Password should be entered!</div>
                <span class="error">${passwordError}</span>
            </div>

            <div class="form-group">
                <label for="confirmpassword">Confirm password:</label>
                <input type="text" class="form-control w-25" id="confirmpassword" placeholder="Confirm password"
                       name="confirmpassword"
                       required>
                <div class="invalid-feedback">You need to confirm password!</div>
            </div>


            <div class="form-group">
                <label for="name">Name:</label>
                <input type="name" class="form-control w-25" id="name" placeholder="Enter name"
                       name="name"
                       required>
                <div class="invalid-feedback">Name should be entered!</div>
            </div>

            <div class="form-group">
                <label for="surname">Surname:</label>
                <input type="surname" class="form-control w-25" id="surname" placeholder="Enter surname"
                       name="surname"
                       required>
                <div class="invalid-feedback">Surname should be entered!</div>
            </div>

            <div class="form-group">
                <label for="dateBorn">Date born:</label>
                <input type="date" class="form-control w-25" id="dateBorn" placeholder="Enter your birth date"
                       name="dateBorn"
                       required>
                <div class="invalid-feedback">Birth date should be entered!</div>
            </div>

            <div class="form-group">
                <label for="email">E-mail:</label>
                <input type="email" class="form-control w-25" id="email" placeholder="Enter your E-mail"
                       name="eMail"
                       required>
                <div class="invalid-feedback">E-mail should be entered!</div>
            </div>


            <button type="submit" class="btn btn-primary" style="width: 90px; margin: 5px 0;">Register</button>
        </form>
    </div>
</div>
<script>
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            var forms = document.getElementsByClassName('needs-validation');
            var validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (validateForm() === false || form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>
</body>
</html>

