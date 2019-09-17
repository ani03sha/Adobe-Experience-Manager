;
$(document).ready(function() {

	$('.openmodale').click(function(e) {
		e.preventDefault();
		$('.modale').addClass('opened');
	});
	$('.closemodale').click(function(e) {
		e.preventDefault();
		$('.modale').removeClass('opened');
	});
});