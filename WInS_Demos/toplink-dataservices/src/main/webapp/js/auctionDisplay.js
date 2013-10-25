/******************************************************************************
 * Copyright (c) 2013, Oracle. All rights reserved.
 ******************************************************************************/

auctionDisplayController = function(spec) {

	var that = {
		url : spec.url,
		socketUrl : spec.socketUrl,
		currentAuction : {},
		currentBid : {},
		usersById : {},
		bids : {}
	};

	/**
	 * Sets the bids for the selected auction item.
	 *
	 * @param {array} bids The list bids for a particular auction item
	 */
	that.setBids = function(bids) {
		that.bids = bids;
		that.currentBid = bids[0];
	};

	/**
	 * Caches the list of users.
	 *
	 * @param {array} users The list of users
	 */
	that.addUsers = function(users) {
		for (var index = 0; index < users.length; index++) {
			var user = users[index];
			that.usersById[user.id] = user;
		}
	};

	/**
	 * Displays the list of bids that have been made by all users.
	 */
	that.displayBids = function() {

		// Update the history of bids and skip the first one,
		// it is used as the current bid
		var bidList = $("#bidList");
		bidList.empty();

		var count = Math.min(that.bids.length, 11);
		for (var index = 1; index < count; index++) {

			var bid = that.bids[index];

			var bidItem = $("<li>");
			bidItem.append(that.usersById[bid.userId].name);
			bidItem.append(": ");

			var bidBid = $("<span>");
			bidBid.append(formatCurrency(bid.bid));
			bidItem.append(bidBid);

			bidList.append(bidItem);
		}
	};

	/**
	 * Adds a new bid to the list and update the web page.
	 *
	 * @param {object} bid The bid to add
	 */
	that.addBid = function(bid) {

		if (!that.exists(bid)) {
			that.bids.splice(0, 0, bid);
			that.currentBid = bid;
			that.displayBids();
			that.displayCurrentBid();
		}
	};

	/**
	 * Updates the information for the current bid.
	 */
	that.displayCurrentBid = function() {
		$("#auction_currentBid").text(formatCurrency(that.currentBid.bid));
		$("#auction_user").text(that.usersById[that.currentBid.userId].name);
	};
	
	function formatCurrency(num) {
	    num = isNaN(num) || num === '' || num === null ? 0.00 : num;
	    return "$" + parseFloat(num).toFixed(2);
	}

	/**
	 * Determines whether the given bid is already in the list of bids or not.
	 *
	 * @param {object} The bid to check if it's in the list of bids
	 * @return {boolean} <code>true</code> if the given bid is already part of the list of bids;
	 * <code>false</code> otherwise
	 */
	that.exists = function(bid) {
		return that.bids[0].userId    == bid.userId &&
		       that.bids[0].bid       == bid.bid    &&
		       that.bids[0].auctionId == bid.auctionId;
	};

	/**
	 * Updates the editor area by displaying the information related to the given auction item.
	 *
	 * @param auction The item to display its information
	 */
	that.displayAuction = function(auction) {

		that.currentAuction = auction;
		that.currentBid = null;

		// Retrieve the users and wait for the response, listing the bids depend on it
		$.ajax({
			url : that.url + "/query/User.all",
			type : 'GET',
			async : false,
			success : function(userArray) {
				that.addUsers(userArray);
			},
			error : function(message) {
				console.log("Could not retrieve the list of users. Error: " + JSON.stringify(message));
			}
		});

		// Retrieve the bids for the auction
		$.ajax({
    		url : that.url + "/query/Bid.forAuctionIdOrdered;auctionId=" + auction.id,
			type : 'GET',
    		success: function(bids) {
    			console.log("Bids for Auction " + auction.id + ": " + JSON.stringify(bids));
    			that.setBids(bids);
    			that.attachQueryChangeListener();
    			that.refreshInformation();
    		},
			error : function(message) {
				that.unregister();
				console.log("Could not retrieve the bids for " + auction.name + ". Error: " + JSON.stringify(message));
			}
		});
	};

	/**
	 * Updates the web page to display the information for the select auction item.
	 */
	that.refreshInformation = function() {

		$("#editor")
			.fadeOut(250, function() {
				$("#auction_image").attr("src", that.currentAuction.image);
				$("#auction_name span").text(that.currentAuction.name);
				$("#auction_description span").text(that.currentAuction.description);
				$("#auction_startPrice").text(formatCurrency(that.currentAuction.startPrice));
				$("#bidText").val("");
				that.displayBids();
				that.displayCurrentBid();
			})
			.fadeIn(250);
	};

	/**
	 * Registers an event to the WebSocket in order to be notified when something
	 * has changed on the server and update the web page.
	 */
	that.attachQueryChangeListener = function() {

		// Close the existing WebSocket if open
		that.unregister();

		console.debug("Websocket: registering listener");

		var pathname = document.location.pathname;
		var webcontext = pathname.split('/')[1];
		var url = 'ws://' + document.location.host + "/" + webcontext + that.socketUrl;
		var clazz = window["WebSocket"] || window["MozWebSocket"];

		if (clazz) {

			that.websocket = new clazz(url);
			that.websocket.onopen = function() {
				var message = JSON.stringify({auction: that.currentAuction.id});
				that.websocket.send(message);
			};

			that.websocket.onmessage = function (event) {

				console.debug("Websocket.onmessage received: " + event.data);
				var message = JSON.parse(event.data);
				var entityType = message.object.type;

				if (entityType == "bid" &&
				    message.type == "ADD") {

					that.addBid(message.object);
				}
				else {
					console.log("Ignoring message: " + JSON.stringify(message));
				}
			};
		}
		else {
			console.debug("Cannot register a query change listener.");
		}
	};

	/**
	 * Closes the WebSocket if it's alive.
	 */
	that.unregister = function() {
		if (that.websocket != null) {
			console.debug("Websocket: closing");
			that.websocket.close();
			that.websocket = null;
		}
	};

	/**
	 * Make sure to close the WebSocket if it's alive when the web page is unloaded.
	 */
	that.initialize = function() {
		window.onbeforeunload = that.unregister;
	};

	return that;
};