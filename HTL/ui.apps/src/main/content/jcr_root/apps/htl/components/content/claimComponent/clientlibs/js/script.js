function createUUID() {

	var s = [];
	var hexDigits = "0123456789abcdef";

	for (var i = 0; i < 36; i++) {
		s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	}

	s[14] = "4"; // Bits 12-15 of the time_hi_and_version field to 0100.
	s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // Bits 6-7 of the
														// clock_seq_hi_and_reserved
														// to 01
	s[8] = s[13] = s[18] = s[23] = "-";

	var uuid = s.join("");

	return uuid;
}

$(document).ready(
		function() {

			$('body').hide().fadeIn(5000);

			$('#submit').click(
					function() {
						var failure = function(err) {
							alert("Unable to retrieve data " + err);
						};

						// Get the user defined values that represent claim data
						// to persist in it!
						var firstName = $('#FirstName').val();
						var lastName = $('#LastName').val();
						var date = $('#DateId').val();
						var category = $('CatId').val();
						var state = $('StateId').val();
						var details = $('#Explain').val();
						var city = $('#City').val();
						var address = $('#Address').val();
						var claimId = createUUID();

						// Use JQuery AJAX request to post data
						$.ajax({
							type : 'POST',
							url : '/bin/claim',
							data : 'id=' + claimId + '&firstName=' + firstName
									+ '&lastName=' + lastName + '&address='
									+ address + '&cat=' + category + '&state='
									+ state + '&details=' + details + '&date='
									+ date + '&city=' + city,
							success : function(msg) {
								var json = jQuery.parseJSON(msg);
								var msgId = json.id;
								var lastName = json.lastName;
								var firstName = json.firstName;

								$('#ClaimNum').val(msgId);
								$('#json').val(
										'Filed by: ' + firstName + " "
												+ lastName);
							}
						});
					});
		});