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

function getBirthDateFromPesel(pesel) {
    var year = parseInt(pesel.substring(0, 2), 10);
    var month = parseInt(pesel.substring(2, 4), 10) - 20;
    var day = parseInt(pesel.substring(4, 6), 10);
    return new Date("20" + year + "-" + month + "-" + day).toLocaleDateString("pl-PL");
}

function getSexFromPesel(pesel) {
    var sex = parseInt(pesel.substring(9, 10), 10);
    return sex % 2 === 1 ? "chłopiec" : "dziewczynka";
}

function getChildren() {
    $.ajax({
        type: "GET",
        url: window.origin + "/tecza/rest/parent/getAll",
        headers: {'Authorization': $.cookie('token')},
        success: function (result) {
            $("#childResult").empty();
            var name = result[0].name;
            var surname = result[0].surname;
            var pesel = result[0].pesel;
            var birthday = getBirthDateFromPesel(pesel);
            var sex = getSexFromPesel(pesel);
            var html =
                "<div class='btmspace-30 center'>\n" +
                "<h1 class='nospace'>Dane Dziecka</h1>\n" +
                "</div>\n" +
                "</br>\n" +
                "<div class='scrollable'>\n" +
                "<h2>Dane osobowe</h2>\n" +
                "                <table>\n" +
                "                    <tbody>\n" +
                "                    <tr>\n" +
                "                        <th>Imię</th>\n" +
                "                        <td>" + name + "</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <th>Nazwisko</th>\n" +
                "                        <td>" + surname + "</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <th>PESEL</th>\n" +
                "                        <td>" + pesel + "</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <th>Data urodzenia</th>\n" +
                "                        <td>" + birthday + "</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <th>Płeć</th>\n" +
                "                        <td>" + sex + "</td>\n" +
                "                    </tr>\n" +
                "                    </tbody>\n" +
                "                </table>\n" +
                "</div>";
            $("#childResult").append(html);
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function getTrustedPeople() {
    $.ajax({
        type: "GET",
        url: window.origin + "/tecza/rest/parent/getAll",
        headers: {'Authorization': $.cookie('token')},
        success: function (result) {
            localStorage.setItem('id', result[0].id);
            $("#trustedPeopleResult").empty();
            var html = '';
            if (result[0].trustedPeople.length != 0) {
                result[0].trustedPeople.forEach(function (trustedPerson) {
                    html += "<tr>" +
                        "<td><input type='checkbox' name='record'></td>" +
                        "<td>" + trustedPerson.name + "</td>" +
                        "<td>" + trustedPerson.surname + "</td>" +
                        "<td>" + trustedPerson.civilId + "</td>" +
                        "<td>" + trustedPerson.phoneNumber + "</td>" +
                        "</tr>";
                });
                $(".delete-row").css('visibility', 'visible');
                $("#trustedPeopleResult").append(html);
            }
        }
    });
}

$(document).ready(function () {
    $("#addTrustedPerson").click(function (event) {
        event.preventDefault();
        addTrustedPerson();
    });

    function addTrustedPerson() {
        var formData = {
            name: $("#name").val(),
            surname: $("#surname").val(),
            civilId: $("#civilid").val(),
            phoneNumber: $("#phone").val()
        };

        $.ajax({
            type: "POST",
            contentType: "application/json",
            headers: {'Authorization': $.cookie('token')},
            url: window.origin + "/tecza/rest/parent/addTrustedPerson/" + localStorage.getItem('id'),
            data: JSON.stringify(formData),
            dataType: 'json',
            success: function (result) {
                alert("Dodano osobę upoważnioną do odbioru dziecka");
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
        $("#civilid").val("");
        $("#phone").val("");
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