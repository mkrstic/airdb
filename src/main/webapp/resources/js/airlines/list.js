jQuery(document).ready(function() {
	var dataSource = new kendo.data.DataSource({
		transport : {
			read : {
				url: ctx + "/avioprevoznici",
				type: "GET",
				contentType : 'application/json'
			},
			parameterMap : function(options) {
				return {format:'json',limit: options.take, skip: options.skip};
			}
		},
		type : "json",
		pageSize : 60,
		serverPaging: true,
		serverFiltering: true,
		schema : {
			data: "data",
            total: "total",
		},
	}); 
	jQuery("#pager").kendoPager({
        dataSource: dataSource
    });
	jQuery("#listView").kendoListView({
        dataSource: dataSource,
        template: jQuery.template(jQuery("#airlinesListViewTemplate").html())
    });
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
					url: ctx+"/avioprevoznici",
					type:"GET",
					contentType : 'application/json',
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
			window.location.href = ctx+'/avioprevoznici/'+id;
		}
	});
	jQuery.ajax({
		type: "GET",
		url:ctx+'/avioprevoznici/ukupno',
		data: {format:'json'},
		contentType:'application/json',
		dataType:'json',
		success: function(total) {
			jQuery('#count').html('Пронађено <span class="bold">'+total+'</span> авиопревозника.');
		}
	});
	jQuery('a[data-page].k-link').live('click', function() {
		jQuery('html, body').animate({scrollTop:'0px'}, 1200);
		return false;
	});
	jQuery('#pageContent').removeClass('hidden');
});