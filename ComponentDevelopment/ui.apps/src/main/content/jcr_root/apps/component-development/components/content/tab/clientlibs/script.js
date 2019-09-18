;
$(document).ready(function() {

	$('ul.tabs li').click(function() {

		var tab_id = $(this).attr('data-tab');

		// Removing 'current' class
		$('ul.tabs li').removeClass('current');
		$('.tab-content').removeClass('current');

		// Adding 'current' class
		$(this).addClass('current');
		$("#" + tab_id).addClass('current');
	});

});