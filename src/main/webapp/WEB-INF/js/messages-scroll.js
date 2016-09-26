$(document).ready(function () {
    var count = 0;
    var $messages = $('#messages');
    // Get first 20 messages
    getMessages();
    // Scroll to bottom after page load
    $messages.scrollTop($messages.get(0).scrollHeight);
    // Set listener to chat scroller
    $messages.scroll(function () {
        if ($messages.scrollTop() == 0) {
            getMessages.call(this);
        }
    });

    function getMessages() {
        var scrollHeight = $(this).get(0).scrollHeight;
        var csrfToken = csrf;
        var csrfHeader = 'X-CSRF-TOKEN';
        var headers = {};
        headers[csrfHeader] = csrfToken;
        $.ajax({
            url: $(location).attr('pathname') + '/messages',
            type: 'post',
            data: {'startRowPosition': count},
            headers: headers,
            success: function (messages) {
                if (!$.isEmptyObject(messages)) {
                    $.each(messages.reverse(), function (i, message) {
                        $messages.prepend(
                            $('<div>').attr('id', message.id).attr('class', 'panel panel-default').append(
                                $('<div>').attr('class', 'panel-body').append(
                                    $('<strong>').text(message.user.username),
                                    $('<small>').text(message.receivedTime),
                                    $('<p>').text(message.message)
                                )
                            )
                        )
                    });
                    var currentScrollHeight = $messages.get(0).scrollHeight;
                    var scrollPosition = currentScrollHeight - scrollHeight;
                    $messages.scrollTop(scrollPosition);
                    count = count + 20;
                }
            }
        })
    }
});
