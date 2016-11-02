$(document).on('click', '.subscribe', function (event) {
    if (isAuthorizated == true) {
        var csrfToken = csrf;
        var csrfHeader = 'X-CSRF-TOKEN';
        var headers = {};
        headers[csrfHeader] = csrfToken;
        var id = event.target.id;
        $.ajax({
            url: '/communities/subscribe',
            type: "post",
            cache: false,
            data: {'communityTitle': id},
            headers: headers,
            success: function (response) {
                if (response === 'success') {
                    $(event.target).removeClass('subscribe').addClass('unsubscribe').text('Leave')
                }
            }
        });
    } else {
        $(location).attr('pathname', 'registration')
    }
    return false;
});

$(document).on('click', '.unsubscribe', function (event) {
    if (isAuthorizated == true) {
        var csrfToken = csrf;
        var csrfHeader = 'X-CSRF-TOKEN';
        var headers = {};
        headers[csrfHeader] = csrfToken;
        var id = event.target.id;
        $.ajax({
            url: '/communities/unsubscribe',
            type: "post",
            cache: false,
            data: {'communityTitle': id},
            headers: headers,
            success: function (response) {
                if (response === 'success') {
                    $(event.target).removeClass('unsubscribe').addClass('subscribe').text('Join')
                }
            }
        });
    }
    return false;
});
