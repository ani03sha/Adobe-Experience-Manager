$(document).ready(
			function() {
				$('.red-quark-content-packager .file-upload input').change(
						function() {
							$('.red-quark-content-packager .file-upload p').text(
									this.files.length + " file(s) selected");
						});
			});

	function validate(file) {
		var ext = file.split(".");
		ext = ext[ext.length - 1].toLowerCase();
		var arrayExtensions = [ "xls", "xlsx" ];
		$('#uploadFileBtn').removeAttr("disabled");

		if (arrayExtensions.lastIndexOf(ext) == -1) {
			alert("Wrong file type. Upload only files with extensions .xls or .xlsx");
			$("#fileToUpload").val("");
			$('#uploadFileBtn').attr('disabled', 'disabled');
		}
	}
