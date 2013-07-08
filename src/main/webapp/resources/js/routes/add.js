jQuery(document).ready(function() {
	if (jQuery('#destAirportAutocomplete').length > 0) {
		jQuery("#destAirportAutocomplete").kendoAutoComplete({
			minLength : 3,
			height: 300,
			dataTextField : "name",
			dataValueField: "id",
			placeholder: 'Одредишни аеродром',
			template: '<h3>jQuery{ data.name}<span>jQuery{ data.city }, jQuery{ data.country }</span></h3>' ,
			dataSource : new kendo.data.DataSource({
				type : "json",
				serverPaging: true,
				serverFiltering : true,
				pageSize : 10,
				schema: {data : "data", total:"total"},
				transport : {
					read : {
						url:ctx+"/aerodromi",
						type:"GET",
						contentType : 'application/json',
					},
					cache: true,
					dataType: "json",
					parameterMap : function(options) {
						return {
							format:'json',
							startsWith : jQuery("#destAirportAutocomplete").data("kendoAutoComplete").value(),
							limit: 15
						};
					}
				}
			}),
			select: function (e) {
				var id = this.dataItem(e.item.index()).id;
				jQuery('#dest\\.id').val(id);

			}
		});
	}
	if (jQuery('#sourceAirportAutocomplete').length > 0) {
		jQuery("#sourceAirportAutocomplete").kendoAutoComplete({
			minLength : 3,
			height: 300,
			dataTextField : "name",
			dataValueField: "id",
			placeholder: 'Полазни аеродром',
			template: '<h3>jQuery{ data.name}<span>jQuery{ data.city }, jQuery{ data.country }</span></h3>' ,
			dataSource : new kendo.data.DataSource({
				type : "json",
				serverPaging: true,
				serverFiltering : true,
				pageSize : 15,
				schema: {data : "data", total:"total"},
				transport : {
					read : {
						url:ctx+"/aerodromi",
						type:"GET",
						contentType : 'application/json',
					},
					cache: true,
					dataType: "json",
					contentType : 'application/json',
					parameterMap : function(options) {
						return {
							format:'json',
							startsWith : jQuery("#sourceAirportAutocomplete").data("kendoAutoComplete").value(),
							limit: 15
						};
					}
				}
			}),
			select: function (e) {
				var id = this.dataItem(e.item.index()).id;
				jQuery('#source\\.id').val(id);
			}
		});
	}
	if (jQuery('#airlineAutocomplete').length > 0) {
		jQuery("#airlineAutocomplete").kendoAutoComplete({
			minLength : 3,
			height: 300,
			dataTextField : "name",
			dataValueField: "id",
			placeholder: 'Авиопревозник',
			template: '<h3>jQuery{ data.name}<span>jQuery{ data.iata}/jQuery{ data.icao}, jQuery{ data.country }</span></h3>' ,
			dataSource : new kendo.data.DataSource({
				type : "json",
				serverPaging: true,
				serverFiltering : true,
				pageSize : 15,
				schema: {data : "data", total: "total"},
				transport : {
					read : {
						url:ctx+"/avioprevoznici",
						type:"GET",
						contentType : 'application/json',
					},
					cache: true,
					dataType: "json",
					contentType : 'application/json',
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
				var id = this.dataItem(e.item.index()).id;
				jQuery('#alid').val(id);
			}
		});
	}
	

});