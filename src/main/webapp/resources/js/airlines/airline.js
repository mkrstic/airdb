var airlineId, airline, firstLoad = true;
function populateFieldsAirline() {
	var fields = ['id', 'name', 'country', 'iata', 'icao', 'callsign', 'active'];
	for (var i  in fields) {
		jQuery('#'+fields[i]).val(eval('airline.'+fields[i]));
	}
}
jQuery(document).ready(function() {
	airlineId = jQuery('#airline_id').val();
	jQuery.ajax({
		type: "GET",
		url:ctx+'/avioprevoznici/'+airlineId,
		contentType:'application/json',
		dataType:'json',
		data : {format:'json'},
		success: function(data) {
			airline = data;
			populateFieldsAirline();
			jQuery('#airlineName').html(airline.name);
			jQuery("#airlineInfoTemplate").tmpl(airline).appendTo("#tabstrip-1");
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
	});
	jQuery('#tabstrip').removeClass('hidden');
	var airlineRoutesDS = new kendo.data.DataSource({
		transport : {
			read : {
				url: ctx + "/avioprevoznici/"+airlineId+'/letovi',
				contentType : 'application/json',
				type: "GET"
			}, 
			parameterMap : function(options, operation) {
				return {
					format : 'json',
					limit: options.take,
					skip: options.skip,
					lazy: false
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
					alid : {type : "number"},
					price : {type : "number"},
					distance : {type : "distance"}
				}
			}
		},
		change: function(e) {
			if (this.data().length == 0 && firstLoad) {
				jQuery('#tabstrip-2').html('');
				jQuery('#tabstrip-2').css('list-style', 'none');
				jQuery("#routesBlankTemplate").tmpl().appendTo("#tabstrip-2");
			} else {
				firstLoad = false;
			}
		}
	}); 
	jQuery("#routesListView").kendoListView({
		dataSource: airlineRoutesDS,
		template: jQuery.template(jQuery("#routesListViewTemplate")),
		pageable: true,
		navigatable: true,
	});

	jQuery("#routesListViewPager").kendoPager({
		dataSource: airlineRoutesDS,
		buttonCount : 5
	});
	jQuery("#airlineRoutesGrid").kendoGrid({
		dataSource : airlineRoutesDS,
		pageable: {
            refresh: true,
            pageSizes: true
        },
		scrollable: false,
		rowTemplate: jQuery.template(jQuery('#airlineRoutesGridTemplate')),
	});	
	jQuery("#panelbar").kendoPanelBar({
        expandMode: "single",
    }).css({ margin: "20px auto" });
	jQuery('#checkAll').change(function() {
		if (jQuery(this).is(':checked')) {
			jQuery('#airlineRoutesGrid input[type=checkbox]').attr('checked', 'checked');
		} else {
			jQuery('#airlineRoutesGrid input[type=checkbox]').removeAttr('checked');
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
	jQuery('#removeAirlineBtn').click(function() {
		var confirmResult = confirm('Након ове акције, аеродром ће бити неповратно уклоњен из базе. Да ли желите да наставите?');
		if (confirmResult == true) {
			jQuery('#removeAirlineForm').submit();
		} 
	});
	jQuery('#active').kendoDropDownList();
});
