var airportId, airport, firstLoad = true;
function populateFieldsAirport() {
	var fields = ['id', 'name', 'city', 'country', 'iata', 'icao', 'timezone', 'latitude', 'longitude'];
	for (var i  in fields) {
		jQuery('#'+fields[i]).val(eval('airport.'+fields[i]));
	}
}
jQuery(document).ready(function() {
	airportId = jQuery('#airport_id').val();
	jQuery.ajax({
		type: "GET",
		url:ctx+'/aerodromi/'+airportId,
		contentType:'application/json',
		dataType:'json',
		data: {format:'json'},
		success: function(data) {
			airport = data;
			Maps.airport = airport;
			populateFieldsAirport();
			loadNearbyTab();
			jQuery("#airportInfoTemplate").tmpl(airport).appendTo("#tabstrip-1");
			jQuery('#airportName').html(airport.name);
			jQuery('#airportCountryImg').attr('src',
					flagUrl(airport.countryCode)).attr('alt',
							airport.country).removeClass('hidden');
		}
	});
	jQuery("#tabstrip").kendoTabStrip({
		animation : {
			close : {
				duration : 100,
				effects : "fadeOut"
			},
			open : {
				duration : 300,
				effects : "fadeIn"
			}
		},
		activate: function(e) {
			if (e.item.getAttribute('id') == 'mapTab') {
				Maps.loadMaps('Maps.initializeAirportMap');
			} 
		}
	});
	jQuery('#tabstrip').removeClass('hidden');
	var dataSourceTab2 = new kendo.data.DataSource({
		type : 'json',
		serverPaging : false,
		serverFiltering : false,
		schema: {data : "data", total : 'total'},
		pageSize: 20,
		transport : {
			read : {
				url: ctx+'/aerodromi/'+airportId+'/aerodromi',
				type :"GET",
				contentType: 'application/json',
			},
			cache: true,
			dataType: "json",
			parameterMap : function(options) {
				return {format:'json',direction: 'out', limit: 1000};
			}
		},
		change: function(e) {
			Maps.airportsOut = this.data();
			if (this.data().length == 0 && firstLoad) {
				jQuery('#tabstrip-2').html('');
				var text = 'Нема летова у бази.';
				jQuery("#airportTemplateBlank").tmpl({text: text}).appendTo("#tabstrip-2");
				jQuery('#tabstrip-2').css('list-style', 'none');
				firstLoad = false;
			} 
		}
	});
	jQuery("#airportsOutListView").kendoListView({
		dataSource: dataSourceTab2,
		template: jQuery.template(jQuery("#airportInOutTemplate")),
		pageable: true,
		navigatable: true,
	});

	jQuery("#airportsOutPager").kendoPager({
		dataSource: dataSourceTab2,
		buttonCount : 5
	});
	var dataSourceTab3 = new kendo.data.DataSource({
		type : 'json',
		serverPaging : false,
		serverFiltering : false,
		schema: {data : "data", total : 'total'},
		pageSize: 20,
		transport : {
			read : {
				url: ctx+'/aerodromi/'+airportId+'/aerodromi',
				type:"GET",
				contentType: 'application/json',
			},
			cache: true,
			dataType: "json",
			parameterMap : function(options) {
				return {format:'json',direction: 'in',limit: 1000};
			}
		},
		change: function(e) {
			Maps.airportsIn = this.data();
			if (this.data().length == 0) {
				jQuery('#tabstrip-3').html('');
				var text = 'Нема летова у бази.';
				jQuery("#airportTemplateBlank").tmpl({text : text}).appendTo("#tabstrip-3");
				jQuery('#tabstrip-3').css('list-style', 'none');
			}
		}
	});
	jQuery("#airportsInListView").kendoListView({
		dataSource: dataSourceTab3,
		template: jQuery.template(jQuery("#airportInOutTemplate")),
		pageable: true,
		navigatable: true,
	});
	jQuery("#airportsInPager").kendoPager({
		dataSource: dataSourceTab3,
		buttonCount : 5
	});
	
	jQuery("#autocompleteAddAirport").kendoAutoComplete({
		minLength : 3,
		height: 300,
		dataTextField : "name",
		dataValueField: "id",
		placeholder: 'Тражи аеродром...',
		template: '<h3>jQuery{ data.name}<span>jQuery{ data.city }, jQuery{ data.country }</span></h3>' ,
		dataSource : new kendo.data.DataSource({
			type : "json",
			serverPaging: true,
			serverFiltering : true,
			pageSize : 10,
			schema: {data : "data"},
			transport : {
				read : {
					url:ctx+"/aerodromi",
					type:"GET",
					contentType: 'application/json',
				},
				cache: true,
				dataType: "json",
				parameterMap : function(options) {
					return {
						format:'json',
						startsWith : jQuery("#autocompleteAddAirport").data("kendoAutoComplete").value(),
						limit: 15
					};
				}
			}
		})
	});
	var airportRoutesDS = new kendo.data.DataSource({
		transport : {
			read : {
				url: ctx + "/aerodromi/"+airportId+'/letovi',
				type: "GET",
				contentType: 'application/json',
			}, 
			parameterMap : function(options, operation) {
				return {
					format:'json',
					limit: options.take,
					skip: options.skip,
					lazy: false,
					direction: 'both'
				};
			}
		},
		type : "json",
		pageSize : 20,
		serverPaging : true,
		schema : {
			data: "data",
            total: "total",
			model : {
				id : "id",
				fields : {
					id : {type : "number"},
					equipment : {type: "string"},
					airlineName : {type : "string"},
					airlineCountry : {type : "string"},
					price : {type : "number"},
					distance : {type : "distance"}
				}
			}
		}
	}); 
	jQuery("#airportRoutesGrid").kendoGrid({
		dataSource : airportRoutesDS,
		pageable: {
            refresh: true,
            pageSizes: true
        },
		scrollable: false,
		rowTemplate: jQuery.template(jQuery('#airportRoutesGridTemplate')),
	});	
	jQuery("#panelbar").kendoPanelBar({
        expandMode: "single",
    }).css({ margin: "20px auto" });
	jQuery('#checkAll').change(function() {
		if (jQuery(this).is(':checked')) {
			jQuery('#airportRoutesGrid input[type=checkbox]').attr('checked', 'checked');
		} else {
			jQuery('#airportRoutesGrid input[type=checkbox]').removeAttr('checked');
		}
		
	});
	jQuery('#removeSelectedRoutesBtn').click(function() {
		var isEmpty = true;
		jQuery('input:checkbox:checked.route-checkbox').each(function() {
			var id = jQuery(this).data('routeid');
			jQuery(this).attr('value', id);
			isEmpty = false;
		});
		if (isEmpty) {
			alert('Одаберите жељене летове.');
		} else {
			var confirmResult = confirm('Након ове акције, означени летови ће бити неповратно уклоњени из базе. Да ли желите да наставите?');
			if (confirmResult == true) {
				jQuery('#editRoutesForm').submit();
			} 
		}
	});
	jQuery('#removeAirportBtn').click(function() {
		var confirmResult = confirm('Након ове акције, аеродром ће бити неповратно уклоњен из базе. Да ли желите да наставите?');
		if (confirmResult == true) {
			jQuery('#removeAirportForm').submit();
		} 
	});
});
function loadNearbyTab() {
	var dataSourceTab4 = new kendo.data.DataSource({
		type : 'json',
		serverPaging : false,
		serverFiltering : false,
		schema: {
			data : function(response) {
				for (var i = 0; i < response.data.length; i++) {
					response.data[i].distance = Maps.findDistance(airport.latitude, airport.longitude, response.data[i].latitude, response.data[i].longitude);
				}
				return response.data;
			}, total:'total'
		},
		pageSize: 20,
		transport : {
			read : {
				url: ctx+'/aerodromi/'+airportId+'/blizu',
				type:"GET",
				contentType: 'application/json',
			},
			cache: true,
			dataType: "json",
			parameterMap : function(options) {
				return {format:'json',distance: '150',lat:airport.latitude,lng:airport.longitude,excludeFirst:true};
			}
		},
		change: function(e) {
			if (this.data().length == 0) {
				jQuery('#tabstrip-4').html('');
				var text = 'Нема познатих аеродрома у близини.';
				jQuery("#airportTemplateBlank").tmpl({text : text}).appendTo("#tabstrip-4");
				jQuery('#tabstrip-4').css('list-style', 'none');
			} 
		}
	});
	jQuery("#airportsNearbyListView").kendoListView({
		dataSource: dataSourceTab4,
		template: jQuery.template(jQuery("#airportNearbyTemplate")),
		pageable: true,
		navigatable: true,
	});
	jQuery("#airportsNearbyPager").kendoPager({
		dataSource: dataSourceTab4,
		buttonCount : 5
	});
}