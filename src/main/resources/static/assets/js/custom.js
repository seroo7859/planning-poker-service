$(document).ready(function() {
	// Scrolly Spy
	// https://jsfiddle.net/mekwall/up4nu/

	var headings = $("#content").find("h1[id^='_'], h2[id^='_'], h3[id^=_], h4[id^=_], h5[id^=_], h6[id^=_]");
	var headingIds = headings.get().map(heading => heading.id);
	var headingScrollPositions = headings.get().map(heading => $(heading).offset().top);
	var tocItems = $("#header > #toc").find("a[href^='#_']");
	var lastTocItem = undefined;
	
	$(window).scroll(function(event) {
   		let currentScrollPosition = $(this).scrollTop();
   		let headingScrollPositionResults = [];
   		let headingIndexResults = [];
   		   		
   		headingScrollPositions.forEach((value, index) => {
   			if(value > currentScrollPosition) {
   				headingScrollPositionResults.push(value);
   				headingIndexResults.push(index);
   			}
   		});

   		let currentHeadingScrollPosition = headingScrollPositionResults[0];
   		let currentHeadingIndex = headingIndexResults[0];
   		
   		for(let i=0; i<headingScrollPositionResults.length; i++) {
   			for(let j=i+1; j<headingScrollPositionResults.length-1; j++) {
   				if(headingScrollPositionResults[j] < currentHeadingScrollPosition) {
   					currentHeadingScrollPosition = headingScrollPositionResults[j];
   					currentHeadingIndex = headingIndexResults[j];
   				}
   			}
   		}
   		
   		let currentTocItem = tocItems.removeClass("active").filter("a[href='#" + headingIds[currentHeadingIndex] + "']");
   		if(currentTocItem.length == 1) {
   			currentTocItem.addClass("active");
	   		lastTocItem = currentTocItem;
	   		
	   		let pos = $("#toc.toc2")[0].scrollHeight * currentHeadingScrollPosition / $(document).height();
	   		if(pos > $(window).height()/2) {
	   			$("#toc.toc2").scrollTop(pos - $(window).height()/2 - $(window).height()/6);
   			}
   		} else {
			if(lastTocItem != undefined) lastTocItem.addClass("active");
   		}
	});

	// Smooth scrolling
	// https://www.w3schools.com/howto/howto_css_smooth_scroll.asp
    // https://blog.kulturbanause.de/2015/12/animiertes-scrolling-smooth-scrolling-jquery/
    $('a[href*="#"]').on('click', function(event) {
    	if(this.hash === "") return;
    	event.preventDefault();
      	let target = this.hash;
      	$('html, body').stop().animate({
        	'scrollTop': $(target).offset().top
      	}, 600, 'swing', function() {
        	window.location.hash = target;
      	});
    });

	// Set Host
	$('#host').text(window.location.host);
});
