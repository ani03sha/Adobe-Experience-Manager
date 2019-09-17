;
$(document).ready(function() {
	var col = document.getElementsByClassName("collapsible");
	for (var i = 0; i < col.length; i++) {
		col[i].addEventListener("click", function() {
			this.classList.toggle("active");
			var content = this.nextElementSibling;
			if (content.style.maxHeight) {
				content.style.maxHeight = null;
			} else {
				content.style.maxHeight = content.scrollHeight + "px";
			}
		});
	}
});