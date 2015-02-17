package com.nehp.rfid_system.server.resources;

import io.dropwizard.hibernate.UnitOfWork;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;

import com.codahale.metrics.annotation.Timed;
import com.nehp.rfid_system.server.auth.annotation.RestrictedTo;
import com.nehp.rfid_system.server.core.Authority;
import com.nehp.rfid_system.server.core.Item;
import com.nehp.rfid_system.server.data.ItemDAO;

@Path("reports")
public class ReportsResource {

	private final static String[] headerDuration = {"Drawing / Item","Revision",
		"Group","Integration","Parts Allocation", "Manufacturing", "QA/QC", "Total"};
	
	private final ItemDAO itemDAO;
	
	public ReportsResource(ItemDAO itemDAO){
		this.itemDAO = itemDAO;
	}
	
	@GET
	@Timed
	@UnitOfWork
	@Path("/duration/hours")
	@RestrictedTo(Authority.ROLE_USER)
	public Response getByDuration(){
		List<Item> items = itemDAO.getItemsAll().get();
		if (items.size() < 1)
			return Response.status( Response.Status.NO_CONTENT ).build();
		
		String excelData = "<table>";
		
		// Headers
		excelData += "<tr>";
		for (String str : headerDuration) {
			excelData += "<td>" + str + "</td>";
		}
		excelData += "</tr>";
		
		// Rows
		for (Item item : items) {
			excelData += "<tr>";
			
			// Drawing / Item
			excelData += getCell(item.getItemId());
			// Revision
			excelData += getCell(item.getCurrentRevision());
			// Group
			String group = "";
			if (item.getGroup() != null)
				group = item.getGroup().toString();
			excelData += getCell( group );
			// Integration
			excelData += getCell( getDurationHours(item.getStage1Date(), item.getStage2Date()));
			// Parts Allocation
			excelData += getCell( getDurationHours(item.getStage2Date(), item.getStage3Date()));
			// Manufacturing
			excelData += getCell( getDurationHours(item.getStage4Date(), item.getStage5Date()));
			// QA/QC
			excelData += getCell( getDurationHours(item.getStage6Date(), item.getStage7Date()));
			// Total
			excelData += getCell( getDurationHours(item.getStage1Date(), item.getStage7Date()));
			
			excelData += "</tr>";
		}
		excelData += "</table>";
		
		return Response.status( Response.Status.OK ).entity( finalizeExcelData(excelData) ).build();
	}
	
	/**
	 * Compiles the excel file.
	 * 
	 * @param body
	 * @return
	 */
	private String finalizeExcelData(String body){
		String excelFile = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:excel' xmlns='http://www.w3.org/TR/REC-html40'>";
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
		excelFile += body;
		excelFile += "</body>";
		excelFile += "</html>";

		String encoded = "base64," + Base64.encodeBase64String(excelFile.getBytes(Charset.forName("US-ASCII")));
		
		return encoded;
	}
	
	/**
	 * Gets the number of hours between two dates.
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	private String getDurationHours(Date start, Date end) {
		if (start == null || end == null)
			return "N/A";
		DateTime dStart = new DateTime(start.getTime(), DateTimeZone.UTC);
		DateTime dEnd = new DateTime(end.getTime(), DateTimeZone.UTC);
		return String.valueOf(Hours.hoursBetween(dStart, dEnd).getHours());
	}
	
	/**
	 * Simple helper method to add table cell tags before and after a string.
	 * 
	 * @param cell
	 * @return
	 */
	private String getCell(String cell){
		return "<td>" + cell + "</td>";
	}
}
