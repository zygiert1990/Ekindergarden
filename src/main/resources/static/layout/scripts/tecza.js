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
                showErrors(e);
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
});

$(document).ready(function () {
    $("#loginForm").submit(function (event) {
        event.preventDefault();
        ajaxPost();
    });

    function ajaxPost() {
        var formData = {
            email: $("#email").val(),
            password: $("#password").val()
        };

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: window.origin + "/token/login",
            data: JSON.stringify(formData),
            dataType: 'json',
            success: function (result) {
                alert("Logowanie przebiegło pomyślnie");
                $.cookie('token', result.token);
                window.location.href = window.origin + "/tecza/" + result.role.toLowerCase();
            },
            error: function (e) {
                alert("Podano nieprawidłowy e-mail lub hasło");
            }
        });
        resetData();
    }

    function resetData() {
        $("#email").val("");
        $("#password").val("");
    }
});

function logout() {
    $.removeCookie('token');
    localStorage.clear();
}

function showErrors(e) {
    if (e.responseJSON.errors === undefined) {
        alert(e.responseJSON.message);
    }
    else if (e.responseJSON.errors.length === 1) {
        alert(e.responseJSON.errors[0].defaultMessage);
    } else {
        var errorList = e.responseJSON.errors;
        var errorMessage = '';
        errorList.forEach(function (error) {
            errorMessage = errorMessage.concat(error.defaultMessage + '\n');
        });
        alert(errorMessage);
    }
}

$(document).ready(function () {
    $("#addChildForm").submit(function (event) {
        event.preventDefault();
        ajaxPost();
    });

    function ajaxPost() {
        var formData = {
            name: $("#name").val(),
            surname: $("#surname").val(),
            pesel: $("#pesel").val(),
            firstParentCivilId: $("#idparent1").val(),
            secondParentCivilId: $("#idparent2").val()
        };

        $.ajax({
            type: "POST",
            contentType: "application/json",
            headers: {'Authorization': $.cookie('token')},
            url: window.origin + "/tecza/rest/admin/addChild",
            data: JSON.stringify(formData),
            dataType: 'json',
            success: function (result) {
                alert("Dodano dziecko");
            },
            error: function (e) {
                showErrors(e);
            }
        });
        resetData();
    }

    function resetData() {
        $("#name").val("");
        $("#surname").val("");
        $("#pesel").val("");
        $("#idparent1").val("");
        $("#idparent2").val("");
    }
});

function getBalance() {
    $.ajax({
        type: "GET",
        url: window.origin + "/tecza/rest/parent/getAll",
        headers: {'Authorization': $.cookie('token')},
        success: function (result) {
            $("#month").empty();
            $("#balance").empty();
            var month = new Date().getMonth() + 1;
            var balance = result[0].payment.balance;
            $("#month").append(month);
            $("#balance").append(balance);
        }
    });
}