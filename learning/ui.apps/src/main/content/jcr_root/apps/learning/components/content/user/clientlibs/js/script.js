$(document).ready(function() {

    $('body').hide().fadeIn(3000);

    $('#submit').click(function() {

        var failure = function(err) {

            alert('Unable to retrieve data: ' + err); 
        };

        $.ajax({

            type: 'GET',
            url: '/bin/learning/userInfo',
            success: function(msg) {

                $('#json').val(msg);
            }
        });
    });
});