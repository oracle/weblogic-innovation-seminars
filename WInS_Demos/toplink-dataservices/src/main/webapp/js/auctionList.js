/******************************************************************************
 * Copyright (c) 2013, Oracle. All rights reserved.
 ******************************************************************************/

auctionController = function(spec) {

	/**
	 * This prefix is prepended to the auction ID when creating and accessing a HTML widget.
	 */
	var AUCTION_ID_PREFIX = "Auction_";

	var that = {
		url : spec.url,
		auctionDisplay : spec.auctionDisplay,
		bidsByAuctionId : {},
		byId : {},
		selectedThumbnail : null
	};

	/**
	 * Registers the given list of auctions and add a thumbnail to the navigator.
	 *
	 * @param {array} auctions The list of auctions
	 */
	that.addAuctions = function(auctions) {

		// Sort the users alphabetically
		auctions.sort(function(auction1, auction2) {
			return auction1.id - auction2.id;
		});

		// Add a thumbnail for each auction item
		for (var index = 0; index < auctions.length; index++) {
			var auction = auctions[index];
			that.byId[auction.id] = auction;
			that.addAuctionThumbnail(auction, index == 0);
		}
	};

	/**
	 * Creates a thumbnail of the given auction and adds it to the navigator pane.
	 *
	 * @param {object} auction A single auction
	 * @param {boolean} Determines whether the thumbnail should be displayed as being selected or not
	 */
	that.addAuctionThumbnail = function(auction, selected) {

		var thumbnail = $("<img>");
		thumbnail.attr("class", "thumdnail");
		thumbnail.attr("src", auction.image);
		thumbnail.attr("alt", auction.name);
		thumbnail.attr("id", AUCTION_ID_PREFIX + auction.id);

		if (selected) {
			that.selectedThumbnail = thumbnail;
			thumbnail.attr("selected", "");
		}

		thumbnail.click(that.thumbnailClicked);
		thumbnail.appendTo("#thumdnail-container");
	};

	/**
	 * Notified when a mouse click is done on an auction item.
	 *
	 * @param event The mouse click event
	 */
	that.thumbnailClicked = function(event) {

		// Keep track of the selected thumbnail and update it's selected state
		that.selectedThumbnail.removeAttr("selected");
		that.selectedThumbnail = $(event.target);
		that.selectedThumbnail.attr("selected", "");

		// Update the selected auction and display its information
		var auctionId = that.selectedThumbnail.attr("id");
		auctionId = auctionId.substring(AUCTION_ID_PREFIX.length);
		that.auctionDisplay.displayAuction(that.byId[auctionId]);
	};

	/**
	 * Initializes the list of auctions.
	 */
	that.initialize = function() {
		$.ajax({
			url : that.url + "/query/Auction.open",
			type : "GET",
			timeout: 20000,
			success : function(auctions) {
				$("hr.separator").show(300);
				$("#thumdnail-container")
					.hide(10, function() {
						that.addAuctions(auctions);
						if (auctions.length > 0) {
							that.auctionDisplay.displayAuction(auctions[0]);
						}
					})
					.fadeIn(300, function() {
						$("#editor").show();
					});
				$("#errorMessage").hide();
				$("#loadingMessage").hide();
			},
			error : function(message) {
				console.log("The auction items could not be retrieved. Error: " + JSON.stringify(message));
				$("#loadingMessage").hide();
				$("#errorMessage").show();
			}
		});
	};

	return that;
};