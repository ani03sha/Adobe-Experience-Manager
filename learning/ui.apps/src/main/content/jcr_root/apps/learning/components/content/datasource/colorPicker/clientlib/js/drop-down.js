$(document).on("click", ".bgColor ul", function(e) {
    touchDialog.colorDropDown();
});

$(document).on("foundation-contentloaded", function(e) {
    touchDialog.colorDropDown();
});