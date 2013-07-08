var route, airline;
jQuery(document).ready(function() {
	routeId = jQuery('#route_id').val();
	jQuery('#routeTitle').append(routeId);
	jQuery.ajax({
		type: "GET",
		url:ctx+'/letovi/'+routeId,
		contentType:'application/json',
		dataType:'json',
		data: {format:'json',lazy: false},
		success: function(data) {
			route = data;
			populateFieldsRoute();
			if (!route.alid) {
				jQuery('#airlineInfoWrapper').addClass('hidden');
			} else {
				jQuery.ajax({
					type: "GET",
					url:ctx+'/avioprevoznici/'+route.alid,
					contentType:'application/json',
					dataType:'json',
					data:{format:'json'},
					success: function(data) {
						airline = data;
						setAirlineLink(airline);
						jQuery('#airlineEditWrapper').addClass('hidden');
						jQuery("#routeInfoTemplate").tmpl({route : route, airline : airline}).appendTo("#tabstrip-1");
					}
				});
			}
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
				Maps.loadMaps('Maps.initializeRouteMap', true);
			} 
		}
	});
	jQuery('#tabstrip').removeClass('hidden');
	jQuery("#airlineAutocomplete").kendoAutoComplete({
		minLength : 3,
		height: 300,
		dataTextField : "name",
		dataValueField: "id",
		placeholder: 'Авиопревозник',
		template: jQuery.template(jQuery('#airlineAutocompleteTemplate')),
		dataSource : new kendo.data.DataSource({
			type : "json",
			serverPaging: true,
			serverFiltering : true,
			pageSize : 15,
			schema: {data : "data", total: "total"},
			transport : {
				read : {
					type :"GET",
					url: ctx+"/avioprevoznici",
					contentType:'application/json',
				},
				cache: true,
				dataType: "json",
				parameterMap : function(options) {
					return {
						format:'json',
						startsWith : jQuery("#airlineAutocomplete").data("kendoAutoComplete").value(),
						limit: 15
					};
				}
			}
		}),
		select: function (e) {
			var selectedAirline = this.dataItem(e.item.index());
			var id = selectedAirline.id;
			jQuery('#alid').val(id);
			setAirlineLink(selectedAirline);
			jQuery('#airlineEditWrapper').addClass('hidden');
			jQuery('#airlineInfoWrapper').removeClass('hidden');
			
		}
	});
	jQuery('#removeRouteBtn').click(function() {
		var confirmResult = confirm('Након ове акције, лет ће бити неповратно уклоњен из базе. Да ли желите да наставите?');
		if (confirmResult == true) {
			jQuery('#removeRouteForm').submit();
		} 
	});
	jQuery('#airlineEditBtn').click(function() {
		jQuery('#airlineInfoWrapper').addClass('hidden');
		jQuery('#airlineEditWrapper').removeClass('hidden');
	});
	jQuery('#airlineCancelBtn').click(function() {
		jQuery('#airlineEditWrapper').addClass('hidden');
		jQuery('#airlineInfoWrapper').removeClass('hidden');
	});
	
});
function populateFieldsRoute() {
	var fields = ['id', 'alid', 'equipment', 'price', 'distance'];
	for (var i in fields) {
		jQuery('#'+fields[i]).val(eval('route.'+fields[i]));
	}
	jQuery('#source\\.id').val(route.source.id);
	jQuery('#dest\\.id').val(route.dest.id);
	setAirportLink(route.source, '#sourceAirportLink');
	setAirportLink(route.dest, '#destAirportLink');
}
function setAirportLink(airport, id) {
	var title = airport.city;
	if (airport.iata) {
		title += " (" + airport.iata + ")";
	} else if (airport.icao) {
		title += " (" + airport.icao + ")"; 
	} 
	title += ", " + airport.country;
	jQuery(id).attr('href', ctx+'/aerodromi/' + airport.id).html(title);
}
function setAirlineLink(selectedAirline) {
	var title = selectedAirline.name;
	if (selectedAirline.country) {
		title += ", " + selectedAirline.country;
	}
	jQuery('#airlineLink').attr('href', ctx+'/avioprevoznici/'+selectedAirline.id).html(title);
}