var mapTabCreated = false, countryId, firstLoad = true, airports;
function loadMapTab() {
	Maps.resizeMap();
	Maps.centerMap(49.405256, -18.984375);
	if (mapTabCreated == true) {
		Maps.fitBounds();
	} else {
		Maps.runClusterer(airports);
		Maps.fitBounds();
		mapTabCreated = true;
	}
}
jQuery(document).ready(function() {
	countryId = jQuery('#country_id').val();
	jQuery.ajax({
		type: "GET",
		url:ctx+'/drzave/'+countryId,
		contentType:'application/json',
		dataType:'json',
		data: {format:'json'},
		success: function(response) {
			jQuery('#countryName').append(response.name);
		}
	});
	var dataSource = new kendo.data.DataSource({
		transport : {
			read : {
				url: ctx + '/drzave/'+countryId+'/aerodromi',
				type: "GET",
				contentType: "application/json",
			},
			parameterMap : function(options) {
				return {format:'json', getAll:true}
			},
		},
		type : "json",
		pageSize : 60,
		schema : {
			data: "data",
            total: "total",
		}, change: function() {
			if (firstLoad) {
				airports = this.data();
				jQuery('#count').html('Пронађено <span class="bold">'+airports.length+'</span> аеродрома.');
				firstLoad = false;
			}
			
		}
	}); 
	jQuery("#pager").kendoPager({
        dataSource: dataSource
    });
	jQuery("#listView").kendoListView({
        dataSource: dataSource,
        template: jQuery.template(jQuery("#airportsListViewTemplate").html())
    });
	jQuery('#arirportFilterTxt').keyup(function() {
		var newVal = jQuery(this).val();
		dataSource.filter({ field: "city", operator: "startswith", value: newVal });
	});
	jQuery('#listContent').removeClass('hidden');
	jQuery('#displayRadio').buttonset();
	jQuery('#listBtn').button({ icons: { primary: 'ui-list-icon'} });
	jQuery('#listBtn').attr('checked', 'checked').button('refresh');
	jQuery('#mapBtn').button({ icons: { primary: 'ui-map-icon'} });
	jQuery('#displayRadio input').change(function(e) {
		var btnId = e.target.id;
		if (btnId == 'mapBtn') {
			jQuery('#mapContent').slideToggle('fast', function() {
				loadMapTab();
			});
			jQuery('#listContent').hide();
		} else {
			jQuery('#listContent').slideToggle('fast');
			jQuery('#mapContent').hide();
		}
	});
	Maps.loadMaps('Maps.initializeAirportsListMap');
	jQuery('#mapContent').hide();
	jQuery('a[data-page].k-link').live('click', function() {
		jQuery('html, body').animate({scrollTop:'0px'}, 1200);
		return false;
	});
});