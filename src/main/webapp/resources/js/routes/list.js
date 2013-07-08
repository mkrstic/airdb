jQuery(document).ready(function() {
	var dataSource = new kendo.data.DataSource({
		transport : {
			read : {
				url: ctx + "/letovi",
				type: "GET",
				contentType : 'application/json'
			}, 
			parameterMap : function(options, operation) {
				return {
					format:'json',
					sortBy: 'alid',
					limit: options.take,
					skip: options.skip,
					lazy: false,
				};
			}
		},
		type : "json",
		pageSize : 18,
		serverPaging : true,
		schema : {
			data: "data",
            total: "total",
		}
	}); 
	jQuery("#pager").kendoPager({
        dataSource: dataSource
    });
	jQuery("#listView").kendoListView({
        dataSource: dataSource,
        template: jQuery.template(jQuery("#routesListViewTemplate").html())
    });
	jQuery.ajax({
		type: "GET",
		url:ctx+'/letovi/ukupno',
		data: {format:'json'},
		contentType:'application/json',
		dataType:'json',
		success: function(totalRoutes) {
			jQuery.ajax({
				type: "GET",
				url:ctx+'/aerodromi/ukupno',
				data: {format:'json'},
				contentType:'application/json',
				dataType:'json',
				success: function(totalAirports) {
					jQuery('#count').html('Пронађено <span class="bold">'+totalRoutes+'</span> летова на укупно <span class="bold">'+totalAirports+'</span> аеродрома.');
				}
			});
		}
	});
	jQuery('a[data-page].k-link').live('click', function() {
		jQuery('html, body').animate({scrollTop:'0px'}, 1200);
		return false;
	});
});