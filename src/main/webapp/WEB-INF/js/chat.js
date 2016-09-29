$(document).ready(function () {
    var count = 0;
    var path = websocketPath;
    var sock;
    var stomp;
    var newChannelSubscription;
    var chatSubscription;
    var chatSubscribe;
    var $messages = $('#messages');
    var currentCommunity = function () {
        var pathArray = $(location).attr('pathname').split('/');
        return pathArray[1];
    };
    var currentChannel = function () {
        var pathArray = $(location).attr('pathname').split('/');
        return pathArray[3];
    };

    loadLastOpenedChannel();
    stompConnect();
    // Set listener to chat scroller
    $messages.scroll(function () {
        if ($messages.scrollTop() == 0) {
            getMessages();
        }
    });

    $('#channelList').find('a').click(function () {
        var url = $(this).attr('href');
        chatSubscription.unsubscribe();
        window.history.pushState({url: url}, '', url);
        getMessagesFromChannel();
        chatSubscribe();
        return false;
    });

    window.onpopstate = function (e) {
        if (e.state) {
            console.log(e.state.url);
            chatSubscription.unsubscribe();
            getMessagesFromChannel();
            chatSubscribe();
        }
    };

    function loadLastOpenedChannel() {
        var csrfToken = csrf;
        var csrfHeader = 'X-CSRF-TOKEN';
        var headers = {};
        headers[csrfHeader] = csrfToken;
        $.ajax({
            url: $(location).attr('pathname') + '/get_last_channel',
            type: 'post',
            headers: headers,
            success: function (channel) {
                if (channel != null) {
                    var url = $(location).attr('pathname') + '/channels/' + channel.title;
                    window.history.pushState({url: url}, null, url);
                    // Get first 20 messages
                    getMessagesFromChannel();
                }
            }
        });
    }

    function getMessagesFromChannel() {
        count = 0;
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
                    $messages.empty();
                    $.each(messages.reverse(), function (i, message) {
                        $messages.prepend(
                            $('<div>').attr('id', message.id).attr('class', 'panel panel-default').append(
                                $('<div>').attr('class', 'panel-body').append(
                                    $('<strong>').text(message.user.username),
                                    $('<small>').text(message.receivedTime),
                                    $('<p>').text(message.message)
                                )
                            ).fadeIn(600)
                        )
                    });
                    count = count + 20;
                    // Scroll to bottom after page load
                    $messages.scrollTop($messages.get(0).scrollHeight);
                }
            }
        });
    }

    function getMessages() {
        var scrollHeight = $messages.get(0).scrollHeight;
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

    function stompConnect() {
        sock  = new SockJS(path);
        stomp = Stomp.over(sock);
        stomp.connect('guest', 'guest', stompSuccessCallback, stompFailureCallback);
    }

    function stompSuccessCallback(frame) {
        newChannelSubscription = stomp.subscribe('/topic/' + currentCommunity() + '/newchannel', handleNewChannel);
        chatSubscribe = function () {
            chatSubscription = stomp.subscribe('/topic/' + currentCommunity() + '/' + currentChannel(), handleChat)
        };
        chatSubscribe();
    }

    function stompFailureCallback(error) {
        console.log('STOMP: ' + error);
        setTimeout(stompConnect, 10000);
        console.log('STOMP: Reconnecting in 10 seconds');
    }

    function handleNewChannel(incoming) {
        var channel = JSON.parse(incoming.body);
        $('#channelList').append(
            $('<li>').append(
                $('<a>').attr('href', '#').text(channel.title)
            )
        )
    }

    function handleChat(incoming) {
        var message = JSON.parse(incoming.body);
        var $messages = $('#messages');
        $messages.append(
            $('<div>').attr('id', message.id).attr('class', 'panel panel-danger').append(
                $('<div>').attr('class', 'panel-body').append(
                    $('<strong>').text(message.user.username),
                    $('<small>').text(message.receivedTime),
                    $('<p>').text(message.message)
                )
            ).fadeIn(600).delay(5000).queue(function () {
                $(this).attr('class', 'panel panel-default').clearQueue()
            })
        ).animate({
            scrollTop: $messages.get(0).scrollHeight
        }, 400)
    }
})