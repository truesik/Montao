$(document).ready(function () {
    var path = websocketPath;
    var sock;
    var stomp;
    var newChannelSubscription;
    var currentCommunity = function () {
        var pathArray = $(location).attr('pathname').split('/');
        return pathArray[1];
    };
    var currentChannel = function () {
        var pathArray = $(location).attr('pathname').split('/');
        return pathArray[3];
    };

    stompConnect();

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
});
