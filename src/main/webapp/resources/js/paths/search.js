var source, dest;
jQuery(document).ready(function() {
	jQuery("#autocompleteAirportStart").kendoAutoComplete({
		minLength : 3,
		height: 300,
		dataTextField : "city",
		dataValueField: "id",
		placeholder: 'Полазни аеродром',
		template: jQuery.template(jQuery('#airportAutocompleteTemplate')),
		dataSource : new kendo.data.DataSource({
			type : "json",
			serverPaging: true,
			serverFiltering : true,
			pageSize : 20,
			schema: {data : "data",total:"total"},
			transport : {
				read : {
					url: ctx+"/aerodromi",
					type: "GET",
					contentType : 'application/json',
				},
				cache: true,
				dataType: "json",
				parameterMap : function(options) {
					return {
						format:'json',
						startsWith : jQuery("#autocompleteAirportStart").data("kendoAutoComplete").value(),
						skip : options.skip,
						limit: options.take,
					};
				}
			},
			requestStart : function() {
				jQuery('#autocompleteAirportStart').addClass('loading');
			},
			change : function() {
				jQuery('#autocompleteAirportStart').removeClass('loading');
			},
		}),
		select: function (e) {
			source = this.dataItem(e.item.index());
			if (dest == null) {
				Maps.clearOverlays();
				Maps.addMarker(source, Maps.greenMarkerImg);
				Maps.centerMap(source.latitude, source.longitude, 7);
			} else {
				triggerPathSearch();
			}
			
		}
	});
	jQuery("#autocompleteAirportEnd").kendoAutoComplete({
		minLength : 3,
		height: 300,
		dataTextField : "city",
		dataValueField: "id",
		placeholder: 'Одредишни аеродром',
		template: jQuery.template(jQuery('#airportAutocompleteTemplate')),
		dataSource : new kendo.data.DataSource({
			type : "json",
			serverPaging: true,
			serverFiltering : true,
			pageSize : 20,
			schema: {data : "data",total:"total"},
			transport : {
				read : {
					url: ctx+"/aerodromi",
					type: "GET",
					contentType : 'application/json',
				},
				cache: true,
				dataType: "json",
				parameterMap : function(options) {
					return {
						format:'json',
						startsWith : jQuery("#autocompleteAirportEnd").data("kendoAutoComplete").value(),
						skip : options.skip,
						limit: options.take,
					};
				}
			},
			requestStart : function() {
				jQuery('#autocompleteAirportEnd').addClass('loading');
			},
			change : function() {
				jQuery('#autocompleteAirportEnd').removeClass('loading');
			},
		}),
		select: function (e) {
			dest = this.dataItem(e.item.index());
			if (source == null) {
				Maps.clearOverlays();
				Maps.addMarker(dest, Maps.redMarkerImg);
				Maps.centerMap(dest.latitude, dest.longitude, 7);
			} else {
				triggerPathSearch();
			}
		}
	});
	jQuery("#pathType").kendoDropDownList({
		dataSource : [ {
			text : "Најмање преседања",
			value : "shortest"
		}, {
			text : "Најнижу цену",
			value : "cheapest-price"
		}, {
			text : "Најмање растојање",
			value : "cheapest-distance"
		}, {
			text : "Оптималан однос цена/растојање",
			value : "optimistic"
		},],
		dataTextField : "text",
		dataValueField: "value",
		index: 0,
		change: triggerPathSearch
	});
	jQuery("#maxLength").kendoDropDownList({
		dataSource : [ {
			text : "Без преседања",
			value : "1"
		}, {
			text : "Највише једно",
			value : "2"
		}, {
			text : "Највише два",
			value : "3"
		}, {
			text : "Највише пет",
			value : "6"
		}, {
			text : "Небитно",
			value : "10"
		} ],
		dataTextField : "text",
		dataValueField: "value",
		change: triggerPathSearch,
	}).data("kendoDropDownList").select(4);
	jQuery('#autocompleteAirportStart').parent().css('width', '180px');
	jQuery('#autocompleteAirportEnd').parent().css('width', '180px');
	jQuery('#pageContent').removeClass('hidden');
	Maps.loadMaps('Maps.initializeSearchPathMap');
	checkLocationHref();
});


function triggerPathSearch() {
	if (!source || !dest) {
		return;
	} else if (source.id == dest.id) {
		alert('Полазни и крајњи аеродроми се морају разликовати');
		return;
	}
	var pathTypeVal = jQuery("#pathType").val();
	var pathType;
	var costProp = 'price';
	if (pathTypeVal.indexOf('-') != -1 ) {
		var arr = pathTypeVal.split('-');
		pathType = arr[0];
		costProp = arr[1];
	} else {
		pathType = pathTypeVal;
	}
	var maxDepth = jQuery('#maxLength').data('kendoDropDownList').value();
	jQuery.ajax({
		type: "GET",
		url:ctx + '/putanje/1/najkraca',
		contentType:'application/json',
		dataType:'json',
		data: {
			format:'json',
			type : pathType,
			costProp : costProp,
			maxDepth : maxDepth,
			sourceId : source.id,
			destId : dest.id,
			lazy : false
		},
		beforeSend : function() {
			jQuery('#loadingImg').removeClass('hidden');
		},
		success: function(response) {
			if (response.total == 0) {
				findAltPath(pathType, costProp, maxDepth);
			} else {
				showPaths(response);
			}	
		}
	});
}
function checkLocationHref() {
	var currLoc = window.location.href;
	var i = currLoc.indexOf('#');
	if (i != -1) {
		var ids = currLoc.substring(i+1,currLoc.length).split(',');
		jQuery.ajax({
			type: "GET",
			url:ctx+'/aerodromi/'+ids[0],
			contentType:'application/json',
			dataType:'json',
			data: {format:'json'},
			success: function(data1) {
				source = data1;
				jQuery.ajax({
					type: "GET",
					url:ctx+'/aerodromi/'+ids[1],
					contentType:'application/json',
					dataType:'json',
					data: {format:'json'},
					success: function(data2) {
						dest = data2;
						var autocompleteStart = jQuery("#autocompleteAirportStart").data("kendoAutoComplete");
						var autocompleteEnd = jQuery("#autocompleteAirportEnd").data("kendoAutoComplete");
						autocompleteStart.value(source.name);
						autocompleteEnd.value(dest.name);
						setTimeout(function() {
							triggerPathSearch();
						}, 500);
					}
				});
			}
		});
	}
}
function updateLocationBar() {
	var currLoc = window.location.href;
	var i = currLoc.indexOf('#');
	if (i == -1) i == currLoc.length;
	var newLoc = currLoc.substring(0,i) + "#" + source.id + "," + dest.id;
	window.location.replace(newLoc);
}
function findAltPath(pathType, costProp, maxDepth) {
	jQuery('#pathTemplateContainer').html('');
	jQuery("#altPathTemplate").tmpl().appendTo("#pathTemplateContainer");
	jQuery.ajax({
		type: "GET",
		url:ctx + '/putanje/1/alternativna',
		contentType:'application/json',
		dataType:'json',
		data: {
			format:'json',
			sourceLat: source.latitude,
			sourceLng: source.longitude,
			destLat: dest.latitude,
			destLng: dest.longitude,
			type : pathType,
			costProp : costProp,
			maxDepth : maxDepth,
			lazy : false,
			distanceKm : 100
		},
		beforeSend : function() {
			jQuery('#loadingImg').removeClass('hidden');
		},
		success: function(response) {
			showPaths(response);
			jQuery('#loadingImg').addClass('hidden');
		}
	});
}
function showPaths(response) {
	if (response.total > 0) {
		var path = response.data[0];
		var idsArr = [];
		for ( var i = 0; i < path.routes.length; i++) {
			idsArr.push(path.routes[i].alid);
		}
		jQuery.ajax({
			type : "GET",
			url : ctx + '/avioprevoznici/ucitaj',
			contentType : 'application/json',
			dataType : 'json',
			data : {format : 'json', ids : idsArr},
			success : function(airlines) {
//				if (airlines == null) {
//					alert('null');
//				}
				for ( var i = 0; i < path.routes.length; i++) {
					if (airlines[i] == null) {
						path.routes[i].alid = '#';
						path.routes[i].name = '';
					} else {
						path.routes[i].alid = airlines[i];
					}
				}
				jQuery('#pathTemplateContainer').html('');
				jQuery("#pathTemplate").tmpl({
					path : path,
					total : response.total,
					elapsed : response.elapsed,
					first : source,
					last : dest
				}).appendTo("#pathTemplateContainer");
				Maps.updateSearchPathMap(path, source, dest);
				jQuery('#loadingImg').addClass('hidden');
				updateLocationBar();
			}
		});
	} else {
		jQuery('#pathTemplateContainer').html('');
		jQuery("#noPathTemplate").tmpl().appendTo("#pathTemplateContainer");
		Maps.updateSearchPathMap(null);
		jQuery('#loadingImg').addClass('hidden');
	}
}
