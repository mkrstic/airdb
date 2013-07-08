jQuery(document).ready(function() {
	jQuery.ajaxSetup({ scriptCharset: "utf-8" , contentType: "application/json"});
	kendo.culture("sr-Cyrl-RS");
	var highlightId = jQuery('#menu').data('highlight');
	jQuery('#menu li[class="current"]').removeClass('current');
	jQuery('#menu_'+highlightId).addClass('current');
	jQuery("#menu_autocompleteAirport").kendoAutoComplete({
		minLength : 3,
		height: 300,
		dataTextField : "name",
		dataValueField: "id",
		placeholder: 'Аеродром, град, држава...',
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
						startsWith : jQuery("#menu_autocompleteAirport").data("kendoAutoComplete").value(),
						skip : options.skip,
						limit: options.take,
					};
				}
			},
			requestStart : function() {
				jQuery('#menu_autocompleteAirport').addClass('loading');
			},
			change : function() {
				jQuery('#menu_autocompleteAirport').removeClass('loading');
			},
		}),
		select: function (e) {
			var dataItem = this.dataItem(e.item.index());
			window.location.href = ctx+"/aerodromi/"+dataItem.id;
		}
	});
	jQuery('#menu_autocompleteAirport').removeClass('hidden');
	
	
});
function truncate(str, limit) {
	if (!limit) {
		limit = 34;
	}
	var result;
	if (str.length > limit) {
		result = jQuery.trim(str).substring(0, limit).split(" ").slice(0, -1).join(" ") + "...";
	} else {
		result = str;
	}
	return result;
}
function flagUrl(code) {
	var url = ctx+'/resources/images/countries/32/'+code.toLowerCase()+'.png';
	return url;
}
