$(document).ready(function () {
    $(document).ready(function () {
        // SUBMIT FORM
        $("#registerForm").submit(function (event) {
            // Prevent the form from submitting via the browser.
            event.preventDefault();
            ajaxPost();
        });

        function ajaxPost() {
            // PREPARE FORM DATA
            var formData = {
                name: $("#name").val(),
                surname: $("#surname").val(),
                civilId: $("#civilid").val(),
                email: $("#email").val(),
                phoneNumber: $("#phone").val(),
                password: $("#password").val(),
                matchingPassword: $("#matchingPassword").val()
            };

            // DO POST
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: window.origin + "/tecza/register/parent",
                data: JSON.stringify(formData),
                dataType: 'json',
                success: function (result) {
                    alert("Rejestracja przebiegła pomyślnie");
                },
                error: function (e) {
                    if (e.responseJSON.errors.length === 1){
                        alert(e.responseJSON.errors[0].defaultMessage);
                    } else {
                        var errorList = e.responseJSON.errors;
                        var errorMessage = '';
                        errorList.forEach(function (error) {
                            errorMessage = errorMessage.concat(error.defaultMessage + '\n');
                        })
                    alert(errorMessage);
                    }
                }
            });
            // Reset FormData after Posting
            resetData();
        }

        function resetData() {
            $("#name").val("");
            $("#surname").val("");
            $("#civilid").val("");
            $("#email").val("");
            $("#phone").val("");
            $("#password").val("");
            $("#matchingPassword").val("");
        }
    })
});

$(document).ready(function () {
    $(document).ready(function () {
        // SUBMIT FORM
        $("#loginForm").submit(function (event) {
            // Prevent the form from submitting via the browser.
            event.preventDefault();
            ajaxPost();
        });

        function ajaxPost() {
            // PREPARE FORM DATA
            var formData = {
                email: $("#email").val(),
                password: $("#password").val()
            };

            // DO POST
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: window.origin + "/token/login",
                data: JSON.stringify(formData),
                dataType: 'json',
                success: function (result) {
                    alert("Logowanie przebiegło pomyślnie");
                },
                error: function (e) {
                    alert("Podano nieprawidłowy e-mail lub hasło");
                }
            });
            // Reset FormData after Posting
            resetData();
        }

        function resetData() {
            $("#email").val("");
            $("#password").val("");
        }
    })
});