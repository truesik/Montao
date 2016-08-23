$(document).ready(function () {
    $('#message').keypress(function (event) {
        if ((event.keyCode == 10 || event.keyCode == 13) && event.ctrlKey) {
            if ($.trim($(this).val()).length > 0) {
                var messageForm = {
                    newMessage: $(this).val()
                };
                var csrfToken = csrf;
                var csrfHeader = 'X-CSRF-TOKEN';
                var headers = {};
                headers[csrfHeader] = csrfToken;
                $.ajax({
                    url: $(location).attr('pathname') + '/messages/new',
                    type: "post",
                    cache: false,
                    data: messageForm,
                    headers: headers,
                    success: function (result) {
                        if (result == 'success') {
                            $('#message').val('')
                        }
                    }
                });
                return false;
            }
        }
    })
});