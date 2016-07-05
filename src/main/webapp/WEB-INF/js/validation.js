$(document).ready(function () {
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
                remote: "This username already exists."
            },
            email: {
                remote: "This email already exists."
            }
        }
    });
});