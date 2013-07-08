var mapTabCreated = false, firstLoad = true;
function loadMapTab() {
	Maps.resizeMap();
	Maps.centerMap(49.405256, -18.984375);
	if (mapTabCreated == false) {
		jQuery.ajax({
			type: "GET",
			url:ctx+'/aerodromi',
			contentType:'application/json',
			dataType:'json',
			data: {format:'json',getAll : true},
			success: function(response) {
				Maps.runClusterer(response.data);
				mapTabCreated = true;
			}
		});
		
	} 
}
jQuery(document).ready(function() {
	var dataSource = new kendo.data.DataSource({
		transport : {
			read : {
				url: ctx + "/drzave",
				type: "GET",
				contentType: "application/json",
			},
			parameterMap : function(options) {
				return {format:'json', getAll:false,limit:1000};
			},
		},
		type : "json",
		pageSize : 60,
		schema : {
			data: "data",
            total: "total"
		},
		change: function(e) {
			setTimeout(function() {
				jQuery("img.lazy").lazyload({
					effect : 'fadeIn'
				});
			}, '100');
			if (firstLoad) {
				firstLoad = false;
				var totalCountries = this.data().length;
				setTimeout(function() {
					jQuery.ajax({
						type: "GET",
						url:ctx+'/aerodromi/ukupno',
						data: {format:'json'},
						contentType:'application/json',
						dataType:'json',
						success: function(totalAirports) {
							jQuery('#count').html('Пронађено <span class="bold">'+totalAirports+'</span> аеродрома у <span class="bold">'+totalCountries+'</span> различитих држава. Одаберите државу.');
						}
					});
				}, '300');
			}
		}
	}); 
	jQuery("#pager").kendoPager({
        dataSource: dataSource
    });
	jQuery("#listView").kendoListView({
        dataSource: dataSource,
        template: jQuery.template(jQuery("#countriesListViewTemplate").html())
    });
	jQuery('#countryFilterTxt').keyup(function() {
		var newVal = jQuery(this).val();
		dataSource.filter({ field: "name", operator: "contains", value: newVal });
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