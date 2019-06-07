  $( function() { 
    var tags = [ 
    "learning/components/content/datasource/simpleDataSource", 
    "learning/components/content/exporter/simpleExporter", 
    "learning/components/content/multifield/simpleMultifield", 
    "learning/components/content/multifield/tab", 
    "learning/components/content/report/componentReport", 
    "learning/components/content/text/simpleText", 
    "learning/components/content/user/userInfo", 
    "learning/components/content/video/youtube", 
    "learning/components/content/workflow/runningWorkflows"
    ]; 

    $( "#componentPath" ).autocomplete({ 
      source: tags 
	}); 
});