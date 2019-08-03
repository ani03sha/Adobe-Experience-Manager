window.onload = function() {

    var div = document.getElementById('jsComponent');

    div.onmouseover = function() {
        this.style.backgroundColor = 'green';
        var h2s = this.getElementByTagName = ('h2');
        h2s[0].style.backgroundColor = 'Aqua';
    };

    div.onmouseout = function() {
        this.style.backgroundColor = 'transparent';
        var h2S = this.getElementByTagName = ('h2');
        h2s[0].style.backgroundColor = 'transparent';
    };
}
