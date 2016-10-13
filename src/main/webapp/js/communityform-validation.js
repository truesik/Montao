$(document).ready(function () {
    $("#title").on("keypress", function (event) {
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

    $('#title').on("paste", function (event) {
        event.preventDefault();
    });

    $('#new-community').validate({
        rules: {
            title: {
                required: true,
                minlength: 4,
                remote: {
                    url: "/communities/check_title",
                    type: "post",
                    data: {  // MUST send the CSRF Token Value
                        '_csrf': function () {
                            return $('input[name="_csrf"]').val();
                        }
                    }
                }
            },
            founder: {
                required: true
            }
        },
        messages: {
            title: {
                remote: "This title already exists."
            }
        }
    });
});