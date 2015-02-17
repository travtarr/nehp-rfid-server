App.ItemsRoute = App.AuthenticatedRoute.extend({
	model: function(){
		return this.store.find('item');
	},
	
	actions: {
		/**
		 * Exports the current visible items in the table to an excel file.
		 */
		excelFilter: function(){
			var excel="<table>";
			var el = this;
			// Header
			$(el).find('thead').find('tr').each(function() {
				excel += "<tr>";
				$(this).filter(':visible').find('th').each(function(index,data) {
					if ($(this).css('display') != 'none'){					
						if(defaults.ignoreColumn.indexOf(index) == -1){
							excel += "<td>" + parseString($(this))+ "</td>";
						}
					}
				});	
				excel += '</tr>';						
				
			});					
			
			// Row Vs Column
			var rowCount=1;
			$(el).find('tbody').find('tr').each(function() {
				excel += "<tr>";
				var colCount=0;
				$(this).filter(':visible').find('td').each(function(index,data) {
					if ($(this).css('display') != 'none'){	
						if(defaults.ignoreColumn.indexOf(index) == -1){
							excel += "<td>"+parseString($(this))+"</td>";
						}
					}
					colCount++;
				});															
				rowCount++;
				excel += '</tr>';
			});					
			excel += '</table>';
			
			var excelFile = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:excel' xmlns='http://www.w3.org/TR/REC-html40'>";
			excelFile += "<head>";
			excelFile += "<!--[if gte mso 9]>";
			excelFile += "<xml>";
			excelFile += "<x:ExcelWorkbook>";
			excelFile += "<x:ExcelWorksheets>";
			excelFile += "<x:ExcelWorksheet>";
			excelFile += "<x:Name>";
			excelFile += "{worksheet}";
			excelFile += "</x:Name>";
			excelFile += "<x:WorksheetOptions>";
			excelFile += "<x:DisplayGridlines/>";
			excelFile += "</x:WorksheetOptions>";
			excelFile += "</x:ExcelWorksheet>";
			excelFile += "</x:ExcelWorksheets>";
			excelFile += "</x:ExcelWorkbook>";
			excelFile += "</xml>";
			excelFile += "<![endif]-->";
			excelFile += "</head>";
			excelFile += "<body>";
			excelFile += excel;
			excelFile += "</body>";
			excelFile += "</html>";

			var base64data = "base64," + $.base64.encode(excelFile);
			window.open('data:application/vnd.ms-excel;filename=exportData.xls;' + base64data);
		},
		excel: function(data){
			
		},
		/**
		 * Exports all items loaded from the server into an excel file.
		 */
		allexcel: function(){
			var excel = "<table>";
			
			// Header
			excel += "<tr>";
			App.Item.eachAttribute(function(name) {
				excel += "<td>" + name + "</td>";
			});
			excel += '</tr>';					

			this.store.find('item').then(function(items){
				// Each Row
				items.forEach(function(item){
					jsonItem = item.toJSON();
					excel += "<tr>";
					// Each Column
					$.each(jsonItem, function(attrib, value){
						// Filter out null values
						if (value === null){
							excel += "<td></td>";
						} else {
							excel += "<td>"+ value +"</td>";
						}
					});			
					excel += '</tr>';
				});
	
				excel += '</table>';
				
				var excelFile = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:excel' xmlns='http://www.w3.org/TR/REC-html40'>";
				excelFile += "<head>";
				excelFile += "<!--[if gte mso 9]>";
				excelFile += "<xml>";
				excelFile += "<x:ExcelWorkbook>";
				excelFile += "<x:ExcelWorksheets>";
				excelFile += "<x:ExcelWorksheet>";
				excelFile += "<x:Name>";
				excelFile += "{worksheet}";
				excelFile += "</x:Name>";
				excelFile += "<x:WorksheetOptions>";
				excelFile += "<x:DisplayGridlines/>";
				excelFile += "</x:WorksheetOptions>";
				excelFile += "</x:ExcelWorksheet>";
				excelFile += "</x:ExcelWorksheets>";
				excelFile += "</x:ExcelWorkbook>";
				excelFile += "</xml>";
				excelFile += "<![endif]-->";
				excelFile += "</head>";
				excelFile += "<body>";
				excelFile += excel;
				excelFile += "</body>";
				excelFile += "</html>";
	
				var base64data = "base64," + $.base64.encode(excelFile);
				window.open('data:application/vnd.ms-excel;filename=exportData.xls;' + base64data);
			});
		},
		excelDuration: function() {
			$.get("/service/reports/duration/hours").done(function(data) {
				window.open('data:application/vnd.ms-excel;filename=exportData.xls;' + data);
			});
			
		}
	}
});