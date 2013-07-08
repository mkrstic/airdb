var map = null;
var mapClusterer = null;
var apiKey = "AIzaSyClgQbLYXyAI3ie3GdzaFedmspbLzwttaE";
var Maps = {
		airportsIn : null,
		airportsOut : null,
		airport :null,
		mapScriptLoaded : false,
		overlays : [],
		mapBounds : null,
		markers : [],
		greenMarkerImg : ctx+'/resources/images/markers/green/02.png',
		redMarkerImg : ctx+'/resources/images/markers/red/01.png',
		otherMarkerImg : ctx+'/resources/images/markers/airport.png',

		createMap : function(centerPosition, zoomSize) {
			if (!centerPosition) {
				centerPosition = new google.maps.LatLng(49.405256, -18.984375); 
			}
			if (!zoomSize) {
				zoomSize = 2;
			}
			map = new google.maps.Map(document.getElementById('map'), {
				zoom : zoomSize,
				center: centerPosition,
				mapTypeId : google.maps.MapTypeId.TERRAIN,
				disableDefaultUI : true,
				streetViewControl: false,
				panControl: true,
				zoomControl : true,
				zoomControlOptions: {
					style: google.maps.ZoomControlStyle.SMALL
				}
			});
		},

		loadMaps : function(callbackFn, fitBounds) {
			if (this.mapScriptLoaded) {
				this.resizeMap();
				if (fitBounds) {
					this.fitBounds();
				}
			} else {
				var script = document.createElement("script");
				script.type = "text/javascript";
				script.src = "http://maps.googleapis.com/maps/api/js?key="+apiKey+"&sensor=false&libraries=drawing&language=sr&callback="+callbackFn;
				document.body.appendChild(script);
			}
		},

		fitBounds : function() {
			map.fitBounds(this.mapBounds);
		},

		initializeAirportsListMap : function() {
			this.createMap();		
			this.markers = new Array();
			this.mapScriptLoaded = true;
		},

		runClusterer: function(airports) {
			if (!airports) {
				return;
			}
			var infowindow = new google.maps.InfoWindow();
			var template = '<div id="infoWindow"><p>Аеродром <span class="bold">%name%, %city%</span></p><p>Држава: %country%</p><br/><a href="%ctx%/aerodromi/%id%" style="color:#A82A15;text-decoration:none;">Детаљан опис</a></div>';
			this.markers = [];
			mapClusterer = new MarkerClusterer(map);
			jQuery("#mapLoadingImg").show();
			google.maps.event.addListener(mapClusterer, 'clusteringend', function(cluster) {
				if (Maps.markers.length == 0) {
					jQuery("#mapLoadingImg").hide();
				}
			});
			var onePercent = Math.round(airports.length/100);
			this.mapBounds = new google.maps.LatLngBounds ();
			for (var i = 0; i < airports.length; i++) {
				var latlng = new google.maps.LatLng(airports[i].latitude, airports[i].longitude);
				this.mapBounds.extend(latlng);
				var marker = new google.maps.Marker({
					position: latlng,
					title: airports[i].city+', '+airports[i].country,
					icon : this.otherMarkerImg
				});
				marker.content = template.replace('%name%', airports[i].name)
				.replace('%city%', airports[i].city).replace('%country%',
						airports[i].country).replace('%id%', airports[i].id)
						.replace('%ctx%', ctx);
				; 
				google.maps.event.addListener(marker, 'click', function() {
					infowindow.setContent(this.content);
					infowindow.open(this.getMap(), this);
				});
				this.markers.push(marker);
			}
			setTimeout(function(){
				for (var i = 0; i < 300	 && Maps.markers.length > 0; i++) {
					mapClusterer.addMarker(Maps.markers.shift(), false);
				}
				if(Maps.markers.length > 0){
					setTimeout(arguments.callee, 25);
				} 
			}, 25);
		},

		initializeAirportMap : function() {
			this.mapBounds = new google.maps.LatLngBounds ();
			var position = new google.maps.LatLng(this.airport.latitude, this.airport.longitude);
			var zoomSize = 10;
			this.createMap(position, zoomSize);
			var marker = this.addMarker(this.airport, this.otherMarkerImg);
			map.setCenter(position);
			map.setZoom(8);
			google.maps.event.trigger(marker, 'click');
			this.mapBounds.extend(position);
			var blueIcon = ctx+'/resources/images/markers/marker_blue_24x20.png';
			var greenIcon = ctx+'/resources/images/markers/marker_green_24x20.png';
			var yellowIcon = ctx+'/resources/images/markers/marker_yellow_24x20.png';
			var airportsBoth = [];
			for ( var i = 0; i < this.airportsIn.length; i++) {
				for ( var j = 0; j < this.airportsOut.length; j++) {
					if (this.airportsIn[i].id == this.airportsOut[j].id) {
						airportsBoth.push(jQuery.extend(true, {}, this.airportsIn[i]));
						this.airportsIn.splice(i, 1);
						this.airportsOut.splice(j, 1);
						break;
					}
				}
			}
			this.addLinkMarkers(this.airport, airportsBoth, blueIcon, this.mapBounds);
			this.addLinkMarkers(this.airport, this.airportsIn, yellowIcon, this.mapBounds);
			this.addLinkMarkers(this.airport, this.airportsOut, greenIcon, this.mapBounds);
			if (!this.mapBounds.isEmpty()) {
				this.mapBounds.extend(position);
				this.fitBounds();
			}
			
			this.mapScriptLoaded = true;
		},

		initializeRouteMap: function() {
			this.createMap();
			this.clearOverlays();
			this.mapBounds = new google.maps.LatLngBounds();
			this.addMarker(route.source, this.greenMarkerImg);
			this.addMarker(route.dest, this.redMarkerImg);
			var line = this.drawLine(route.source.latitude, route.source.longitude, route.dest.latitude, route.dest.longitude, true);
			line.setMap(map);
			this.fitBounds();
			this.mapScriptLoaded = true;
		},

		initializeSearchPathMap : function() {
			this.clearOverlays();
			this.mapBounds = new google.maps.LatLngBounds();
			var position = new google.maps.LatLng(36.522587, -34.804688);
			var zoomSize = 2;
			this.createMap(position, zoomSize);
		},

		clearOverlays : function() {
			while(this.overlays[0]) {
				this.overlays.pop().setMap(null);
			}
		},

		drawLine: function(lat1, lon1, lat2, lon2, useArrow, color) {
			var endpoints = [new google.maps.LatLng(lat1, lon1), new google.maps.LatLng(lat2, lon2)];
			var strokeColor = "#FF0000";
			if (color) {
				strokeColor = color;
			}
			var borderPath;
			if (useArrow) {
				var lineSymbol = {path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW};
				borderPath = new google.maps.Polyline({
					path: endpoints,
					strokeColor: strokeColor,
					strokeOpacity: 0.5,
					strokeWeight: 1.9,
					geodesic: true,
					icons: [{
						icon: lineSymbol,
						offset: '0%',
						repeat: '100px'
					}]
				});
			} else {
				borderPath = new google.maps.Polyline({
					path: endpoints,
					strokeColor: "#FF0000",
					strokeOpacity: 0.8,
					strokeWeight: 1,
					geodesic: true,
				});
			}
			return borderPath;
		},

		resizeMap : function(fitBounds) {
			try {
				google.maps.event.trigger(map,"resize");
				if (fitBounds) {
					this.fitBounds();
				}
			} catch (err) {} 

		},

		addMarker : function (airport, iconImg) {
			var latlng = new google.maps.LatLng(airport.latitude, airport.longitude);
			var marker = new google.maps.Marker({
				position: latlng,
				map: map,
				title: airport.city+', '+airport.country,
				animation: google.maps.Animation.DROP,
				icon : iconImg,
			});
			this.overlays.push(marker);
			this.mapBounds.extend(latlng);
			var infowindow = new google.maps.InfoWindow({
				content: jQuery('#infoWindowAirportTemplate').tmpl(airport).html()
			});
			google.maps.event.addListener(marker, 'click', function() {
				infowindow.open(map, marker);
			});
			return marker;
		},
		addLinkMarkers : function (airport, airports, icon, bounds, useArrow) {
			for (var i = 0; i < airports.length; i++) {
				var latlng = new google.maps.LatLng(airports[i].latitude, airports[i].longitude);
				bounds.extend(latlng);
				var marker = this.addMarker(airports[i], icon);
				if (useArrow) {
					marker.overlay = this.drawLine(airports[i].latitude, airports[i].longitude, airport.latitude, airport.longitude, true);
				} else {
					marker.overlay = this.drawLine(airports[i].latitude, airports[i].longitude, airport.latitude, airport.longitude, false);
				}
				google.maps.event.addListener(marker, 'mouseover', function() {
					this.overlay.setMap(this.getMap());
				});
				google.maps.event.addListener(marker, 'mouseout', function() {
					this.overlay.setMap(null);
				});
			}
		},

		updateSearchPathMap : function(path, altStart, altDest) {
			this.clearOverlays();
			if (path == null) {
				return;
			}
			var infowindow = new google.maps.InfoWindow();
			var bounds = new google.maps.LatLngBounds ();
			if (altStart && (altStart.id != path.start.id)) {
				var veryFirstRoute = {source: altStart, dest: path.start, otherColor: '#0000FF'};
				path.routes.splice(0, 0, veryFirstRoute);
			}
			if (altDest && (altDest.id != path.end.id)) {
				var veryLastRoute = {source: path.end, dest: altDest, otherColor: '#0000FF'};
				path.routes.push(veryLastRoute);
			}
			var newPaths = [path.routes[0].source];
			for (var ii = 0; ii < path.routes.length; ii++) {
				newPaths.push(path.routes[ii].dest);
				
			}
			for (var i = 0; i < path.routes.length; i++) {
				var route = path.routes[i];
				var arr = [route.source, route.dest];
				var line;
				if (route.otherColor){
					line = this.drawLine(arr[0].latitude, arr[0].longitude, arr[1].latitude, arr[1].longitude, true, route.otherColor);
				} else {
					line = this.drawLine(arr[0].latitude, arr[0].longitude, arr[1].latitude, arr[1].longitude, true);
				}
				line.setMap(map);
				this.overlays.push(line);
			}
			for (var j = 0; j < newPaths.length; j++) {
				var airport = newPaths[j];
				var latlng = new google.maps.LatLng(airport.latitude, airport.longitude);
				bounds.extend(latlng);
				var iconImage = null;
				if (j == 0) {
					iconImage = this.greenMarkerImg;
				} else if (j == newPaths.length-1) {
					iconImage = this.redMarkerImg;
				} else {
					iconImage = this.otherMarkerImg;
				}
				var marker = new google.maps.Marker({
					position: latlng,
					map: map,
					title: airport.city + ', ' + airport.country,
					icon: iconImage,
					animation: google.maps.Animation.DROP,
				});
				this.overlays.push(marker);
				marker.content = jQuery('#infoWindowAirportTemplate').tmpl(airport).html(); 
				google.maps.event.addListener(marker, 'click', function() {
					infowindow.setContent(this.content);
					infowindow.open(this.getMap(), this);
				});
			}
			map.fitBounds(bounds);
		},

		centerMap: function(lat, lng, zoomSize) {
			map.setCenter(new google.maps.LatLng(lat, lng));
			if (zoomSize) {
				map.setZoom(zoomSize);
			}
		},

		toRad: function (val) {
			return val * Math.PI / 180;
		},

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
		}
};