/******************************************************************************
 * Copyright (c) 2013, Oracle. All rights reserved.
 ******************************************************************************/

userController = function(spec) {

	var that = {
		url : spec.url,
		auctionDisplayController : spec.auctionDisplayController,
		byId : {}
	};

	/**
	 * Adds the given list of users
	 *
	 * @param {array} users The users to add
	 */
	that.addUsers = function(users) {

		// Sort the users alphabetically
		users.sort(function(user1, user2) {
			return user1.name.localeCompare(user2.name);
		});

		// Add each user to the User drop down
		for (var index = 0; index < users.length; index++) {
			that.addUser(users[index]);
		}
	};

	/**
	 * Adds the given user to the Users drop down.
	 *
	 * @param user {object} The user to register
	 */
	that.addUser = function(user) {

		that.byId[user.id] = user;

		var option = $("<option>");
		option.attr("value", user.id);
		option.append(user.name);
		option.appendTo("#userSelect");
	};

	/**
	 * Initializes the list of users.
	 */
	that.initialize = function() {

		$.ajax({
			url: that.url + '/query/User.all',
			type : "GET",
			timeout: 12000,
			success: function(users) {
				that.addUsers(users);
				$("#userSelect").selectpicker();
			},
			error : function(message) {
				console.log("Could not retrieve the list of users. Error: " + JSON.stringify(message));
			}
		});
	};

	/**
	 * Posts a bid by sending to the server the bid information.
	 */
	that.postBid = function() {

		// Create the bid based on the current information entered by the user
		var bid = that.createBid();

		// Invalid bid
		if (isNaN(bid.bid)) {
			alert("The bid is invalid. Please make a valid bid.");
		}
		// If the bid value is bigger than the current bid, then post it
		else if (that.auctionDisplayController.currentBid == null ||
		         that.auctionDisplayController.currentBid.bid < bid.bid) {

			// Convert the bid object to its string representation
			var payload = JSON.stringify(bid);
			console.debug("Sending: " + payload);

			// Post the bid request to the server
			$.ajax({
				url : that.url + "/entity/Bid",
				type : "post",
				data : payload,
				dataType: "json",
				contentType : "application/json; charset=utf-8",
				done : function(event) {
					console.debug("Result: " + JSON.stringify(event));
				},
				error : function(event) {
					console.log("Could not post the bid. Error: " + JSON.stringify(event));
				}
			});

			// Clear the bid
			jQuery("#bidText").val("");

			// Display the new bid
			that.auctionDisplayController.addBid(bid);
			$("#auction_currentBid").text(bid.bid);
			$("#auction_user").text(that.auctionDisplayController.usersById[bid.userId].name);
			$("#postBidButton").attr("disabled", "");
		}
		else if (that.auctionDisplayController.currentBid != null) {

			var price = that.auctionDisplayController.currentBid.bid;

			// Bid too low
			if (bid.bid < that.auctionDisplayController.currentBid.bid) {
				alert("The bid is too low. Make a bid bigger than " + price + ".");
			}
			// Bid is same than current bid
			else if (bid.bid == that.auctionDisplayController.currentBid.bid) {
				alert("The bid is the same as the current bid. Make a bid bigger than " + price + ".");
			}
		}

		$("#bidText").focus();
	};

	/**
	 * Creates a new object containing the bid information.
	 *
	 * @returns {object} The information related to a bid
	 */
	that.createBid = function() {

		var bid = $("#bidText").val();
		bid = parseFloat(parseFloat(bid).toFixed(2));

		return {
			userId    : parseInt($("#userSelect").find(":selected").attr("value")),
			bid       : bid,
			auctionId : that.auctionDisplayController.currentAuction.id,
			time      : new Date().getTime()
		};
	};

	return that;
};