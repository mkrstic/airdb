var Maps = {
	airport : null,
	airportsIn : null,
	airportsOut : null,
	
	findDistance: function (lat1, lng1, lat2, lng2) {
		var R = 6371; // km
		var dLat = this.toRad(lat2 - lat1);
		var dLon = this.toRad(lng2 - lng1);
		var lat1 = this.toRad(lat1);
		var lat2 = this.toRad(lat2);
		var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2)
		* Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
		var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		var d = R * c;
		return Math.round(d);
	},
	loadMaps : function(str) {
		jQuery('#map').html('<span class="map-error-text">Мапа није доступна. Проверите интернет везу.</span>');
	},
	resizeMap : function() {
		
	},
	centerMap : function(x, y) {
		
	},
	fitBounds : function() {
		
	},
	runClusterer: function() {
		
	},
	clearOverlays : function() {
		
	},
	addMarker : function() {
		
	},
	updateSearchPathMap : function() {
		
	}
	
};