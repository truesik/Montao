$(document).ready(function () {
    $("#username").on("keypress", function (event) {
        // Disallow anything not matching the regex pattern (A to Z uppercase, a to z lowercase, digits 0 to 9 and underscore ("_"))
        var englishAlphabetDigitsAndUnderscore = /\w+/g;
        // Retrieving the key from the char code passed in event.which
        var key = String.fromCharCode(event.which);
        if (englishAlphabetDigitsAndUnderscore.test(key)) {
            return true;
        }
        // If we got this far, just return false because a disallowed key was typed.
        return false;
    });

    $('#username').on("paste", function (event) {
        event.preventDefault();
    });

    $('#signup').validate({
        rules: {
            username: {
                required: true,
                minlength: 4,
                remote: {
                    url: "/registration/check_username",
                    type: "post",
                    data: {  // MUST send the CSRF Token Value
                        '_csrf': function () {
                            return $('input[name="_csrf"]').val();
                        }
                    }
                }
            },
            email: {
                required: true,
                email: true,
                remote: {
                    url: "/registration/check_email",
                    type: "post",
                    data: {
                        '_csrf': function () {
                            return $('input[name="_csrf"]').val();
                        }
                    }
                }
            },
            password: {
                required: true,
                minlength: 8
            },
            confirm_password: {
                required: true,
                equalTo: '#password'
            }
        },
        messages: {
            username: {
                remote: "This name already exists."
            },
            email: {
                remote: "This email already exists."
            }
        }
    });
});