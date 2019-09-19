$(function () {
    var appCover = $('#app-cover'),
        app = $('#app'),
        yesButton = $('#yes-button'),
        noButton = $('#no-button'),
        layer = $('.layer'),
        layer2 = $('#layer2'),
        layer3 = $('#layer3'),
        emailAddressInput = $('#email-address'),
        reasonInput = $('#reason'),
        backButton = $('.back-button'),
        form = $('form'),
        activeLayer, _layer, _email, _reason, regex, wElm, blink, closeButton, message = "Thank you";

    yesButton.on('click', showEmailForm);
    noButton.on('click', showReasonForm);
    backButton.on('click', goBack);
    form.submit(function (e) {
        submitForm(e);
    });

    function reviveForm() {
        app.removeClass('flipped');
        appCover.fadeIn(0);
        appCover.removeClass('hide');
    }

    function hideForm() {
        appCover.addClass('hide');
        appCover.fadeOut(300);

        setTimeout(function () {
            layer.removeClass('active');
            $('.working').removeClass('active done').fadeOut(0);
            $('.blink').text('Working ...');
        }, 800);

        closeButton.off('click');

        setTimeout(function () {
            reviveForm();
        }, 2000);
    }

    function showEmailForm() {
        activeLayer = 1;
        closeButton = layer2.find('.close-button');

        closeButton.on('click', hideForm);

        layer.removeClass('active');

        $('.working').removeClass('active done').fadeOut(0);
        $('.blink').text('Working ...');

        layer2.addClass('active');
        app.addClass('flipped');
        setTimeout(function () {
            emailAddressInput.val('').focus();
        }, 1100);
    }

    function showReasonForm() {
        activeLayer = 2;
        closeButton = layer3.find('.close-button');

        closeButton.on('click', hideForm);

        layer.removeClass('active');
        layer3.addClass('active');
        app.addClass('flipped');
        setTimeout(function () {
            reasonInput.val('').focus();
        }, 1100);
    }

    function goBack() {
        app.removeClass('flipped');
        setTimeout(function () {
            layer.removeClass('active');
            if (activeLayer == 1)
                emailAddressInput.val('');
            else
                reasonInput.val('');
        }, 1100);
        closeButton.off('click');
    }

    function sendData(_layer) {
        wElm = _layer.find('.working');
        blink = _layer.find('.blink');

        wElm.fadeIn(100);
        setTimeout(function () {
            wElm.addClass('active');
        }, 250);
        /*
            AJAX Call to send data to server
        */
        var email = $('#email-address').val();
        var reason = $('#reason').val();
        $.ajax({
                url: "/bin/subscribe",
            	method: "POST",
            	data: "email=" + email + "&reason=" + reason,
                success: function(result){
                    $("#div1").html(result);
                }
        });

        setTimeout(function () {
            wElm.addClass('done');
            blink.text('Done');
        }, 3250);

        setTimeout(function () {
            blink.addClass('hide');
        }, 4450);
        setTimeout(function () {
            blink.text(message);
        }, 4850);
        setTimeout(function () {
            blink.removeClass('hide');
        }, 5200);

        if (activeLayer == 1)
            emailAddressInput.val('');
        else
            reasonInput.val('');
    }

    function submitForm(e) {
        e.preventDefault();
        if (activeLayer == 1) {
            _layer = layer2;
            _email = emailAddressInput.val().trim();
            if (_email.length > 0) {
                regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
                if (regex.test(_email))
                    sendData(_layer);
                else {
                    emailAddressInput.focus();
                    return false;
                }
            } else {
                emailAddressInput.focus();
                return false;
            }
        } else {
            _layer = layer3;
            _reason = reasonInput.val().trim();
            if (_reason.length > 0)
                sendData(_layer);
            else {
                reasonInput.focus();
                return false;
            }
        }
    }
});