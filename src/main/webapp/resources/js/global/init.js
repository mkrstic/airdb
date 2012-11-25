if (navigator.onLine) {
	document.write('\x3Cscript type="text/javascript" src="' + ctx
			+ '/resources/js/global/maps.js' + '">\x3C/script>')
} else {
	document.write('\x3Cscript type="text/javascript" src="' + ctx
			+ '/resources/js/global/maps_no_connection.js' + '">\x3C/script>')
}