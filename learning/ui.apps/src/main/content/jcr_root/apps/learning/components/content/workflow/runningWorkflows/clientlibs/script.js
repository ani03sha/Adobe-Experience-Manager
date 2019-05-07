$(document).ready(function() {

    $('body').hide().fadeIn(2000);

	$('#submit').click(function() {
    	var failure = function(err) {
            alert("Unable to retrive data: " + err);
   		};

    //Use JQuery AJAX request to post data to a Sling Servlet
    $.ajax({
         type: 'GET',    
         url:'/content/learning/workflow.workflow.html',
         success: function(msg) {
            alert(msg);
         }
     });
  });

});