$(document).ready(function () {
    var count = 40;
    // Set listener to window scroller
    $(window).scroll(function () {
        if ($(window).scrollTop() == $(document).height() - $(window).height()) {
            var csrfToken = csrf;
            var csrfHeader = 'X-CSRF-TOKEN';
            var headers = {};
            headers[csrfHeader] = csrfToken;
            $.ajax({
                url: '/communities',
                type: 'post',
                data: {'startRowPosition': count},
                headers: headers,
                success: function (communities) {
                    if (!$.isEmptyObject(communities)) {
                        $.each(communities, function (i, community) {
                            $('#communities').append(
                                $('<div>').attr('class', 'col-lg-3').append(
                                    $('<div>').attr('class', 'panel panel-default').append(
                                        $('<div>')
                                            .attr('class', 'panel-body')
                                            .html('&#8220' + community.description + '&#8221'),
                                        $('<div>').attr('class', 'panel-footer').append(
                                            $('<i>').attr('class', 'material-icons')
                                                .text('group'),
                                            $('<a>').attr('href', '/' + community.title)
                                                .text(community.title)
                                        )
                                    )
                                )
                            )
                        });
                        count = count + 40;
                    }
                }
            })
        }
    })
});