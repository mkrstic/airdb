jQuery(document).ready(function() {
	var countriesDs = new kendo.data.DataSource({
		transport : {
			read : {
				url: ctx + "/drzave",
				type: "GET",
				contentType: "application/json",
			},
			parameterMap : function(options) {
				return {format:'json', limit:1000}
			},
		},
		type : "json",
		pageSize : 500,
		schema : {
			data: "data",
            total: "total"
		}
	}); 
	jQuery("#country").kendoDropDownList({
        dataTextField: "name",
        dataValueField: "name",
        dataSource: countriesDs,
        index: 0,
        template: jQuery.template(jQuery('#countryDropdownTemplate')),
    });
});