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
                url: window.origin + "/register/parent",
                data: JSON.stringify(formData),
                dataType: 'json',
                success: function (result) {
                    console.log(result);
                },
                error: function (e) {
                    alert(e.responseJSON.message);
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
                url: "http://ec2-35-180-42-91.eu-west-3.compute.amazonaws.com:8080/token/login",
                data: JSON.stringify(formData),
                dataType: 'json',
                success: function (result) {
                    console.log(result);
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