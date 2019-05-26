window.onload=function() {

    var accordion = document.getElementsByClassName("accordion");
	for (var i = 0; i < accordion.length; i++) {
  		accordion[i].onclick = function() {
    		this.classList.toggle("active");
    		var panel = this.nextElementSibling;
    		if (panel.style.maxHeight){
      			panel.style.maxHeight = null;
    		} else {
      			panel.style.maxHeight = panel.scrollHeight + "px";
   			} 
  		}
	}
}