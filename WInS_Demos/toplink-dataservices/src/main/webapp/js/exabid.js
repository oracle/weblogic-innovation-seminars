/******************************************************************************
 * Copyright (c) 2013, Oracle. All rights reserved.
 ******************************************************************************/

/**
 * Executed after the document is ready (i.e. has been loaded).
 */
jQuery(document).ready(function() {
	initialize();
});

/**
 * Changes the cursor to the wait cursor while a long task is being executed.
 */
jQuery(document).ajaxStart(function() {
   document.body.style.cursor = 'wait';
});


/**
 * Changes the cursor back once the task has completed.
 */
jQuery(document).ajaxComplete(function() {
    document.body.style.cursor = 'default';
});


/**
 * Changes the cursor back when the task has encountered an error.
 */
jQuery(document).ajaxError(function() {
    document.body.style.cursor = 'default';
});

/**
 * Initializes the eXaBid demo.
 */
function initialize() {

	var auctionDisplay = auctionDisplayController({
		url : "persistence/v1.0/auction",
		socketUrl : "/auction/live-bids"
	});

	var auctionList = auctionController({
		url : "persistence/v1.0/auction",
		auctionDisplay : auctionDisplay,
	});

	var users = userController({
		url : "persistence/v1.0/auction",
		auctionDisplayController : auctionDisplay
	});

	auctionList.initialize();
	auctionDisplay.initialize();
	users.initialize();

	updateUI(users);
};

/**
 * Updates the layout and widgets.
 */
function updateUI(users) {

	// Register a listener on the Bid button to post the bid
	$("#postBidButton").click(function() {
		users.postBid();
	});

	// Change the enablement state of the "Place Bid" button
	// based on the input text's content
	$("#bidText")
		.on('keyup change', function(event) {
			if (event.target.value.length == 0) {
				$("#postBidButton").attr("disabled", "");
			}
			else {
				$("#postBidButton").removeAttr("disabled");
			}
		});

	// Change the default action of the ENTER key in order
	// to post a bid without reloading the entire page
	$("input")
		.keydown(function(e) {
			// Enter pressed
			if (e.which == 13) {
				e.preventDefault();
				return false;
			}
		})
		.keyup(function(e) {
			// Enter pressed
			if (e.which == 13) {
				e.preventDefault();
				users.postBid();
				return false;
			}
		});

	// Align both labels
	var label1 = $("#userSelectLabel");
	var label2 = $("#bidTextLabel");

	var width = 5 + Math.max(label1.width(), label2.width());
	label1.width(width);
	label2.width(width);

	// Hide the panes, if everything is loaded, they'll become visible
	$("#errorMessage").hide(0);
	$("#editor").hide(0);
	$("#thumbnail-container").hide(0);
	$("hr.separator").hide(0);
}