jQuery(document).ready(function() {
	jQuery('#active').kendoDropDownList().width(300);
	var countriesDs = new kendo.data.DataSource({
		transport : {
			read : {
				url: ctx + "/drzave",
				type: "GET",
				contentType: "application/json",
			},
			parameterMap : function(options) {
				return {format:'json', getAll:false, skip:0, limit:1000};
			},
		},
		type : "json",
		serverPaging: false,
		serverFiltering : false,
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
        template : jQuery.template(jQuery('#countryDropdownTemplate'))
    });
});