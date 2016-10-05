$(document).ready(function () {
    var pathArray = $(location).attr('pathname').split('/');
    var currentCommunity = pathArray[1];

    $('#create').click(function () {
        $('#new-channel').submit()
    });

    $('#new-channel').validate({
        rules: {
            title: {
                required: true,
                minlength: 4,
                remote: {
                    url: '/' + currentCommunity + "/channels/check_title",
                    type: "post",
                    data: {  // MUST send the CSRF Token Value
                        '_csrf': function () {
                            return $('input[name="_csrf"]').val();
                        }
                    }
                }
            }
        },
        messages: {
            title: {
                remote: "This title already exists."
            }
        },
        submitHandler: function () {
            var channelForm = {
                title: $('#title').val(),
                description: $('#description').val(),
                communityTitle: currentCommunity
            };
            var csrfToken = csrf;
            var csrfHeader = 'X-CSRF-TOKEN';
            var headers = {};
            headers[csrfHeader] = csrfToken;
            $.ajax({
                url: '/' + currentCommunity + '/channels/new',
                type: "post",
                contentType: "application/json",
                cache: false,
                data: JSON.stringify(channelForm),
                headers: headers,
                success: function (response) {
                    if (response === 'success') {
                        $('#myModal').modal("hide")
                    }
                }
            });
            return false;
        }
    });

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
    }).on("paste", function (event) {
        event.preventDefault();
    });
});