var touchDialog = {
    colorDropDown : function() {
        var dropdown = $('.bgColor ul');
        var button = $('.bgColor button');
        var childlist = dropdown.children();
        if (childlist.length > 1) {
            for (i = 0; i < childlist.length; i++) {
                var attr = childlist[i].getAttribute("data-value");
                if (attr) {  
                    $(childlist[i]).css("background-color", attr);
                    $(childlist[i]).css("border-style", "solid");
                }
                
                if($(childlist[i]).hasClass("is-highlighted")&& attr){
                    $(button).css("background-color", attr);
                }
                if(!attr)
                    $(button).css("background-color", "transparent");
                
            }
            
        }
    }
};
