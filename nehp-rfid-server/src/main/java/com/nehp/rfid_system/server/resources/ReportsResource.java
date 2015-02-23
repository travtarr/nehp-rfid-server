package com.nehp.rfid_system.server.resources;

import io.dropwizard.hibernate.UnitOfWork;

import java.nio.charset.Charset;
import java.util.ArrayList;
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
import com.nehp.rfid_system.server.core.StageLog;
import com.nehp.rfid_system.server.data.ItemDAO;
import com.nehp.rfid_system.server.data.StageLogDAO;
import com.nehp.rfid_system.server.helpers.Stages;

@Path("reports")
public class ReportsResource {

	private final static String[] headerDuration = {"Drawing / Item","Revision",
		"Group","Integration","Parts Kitting", "Awaiting Manufacture", "Manufacturing", "QA/QC", "Shipping", 
		"On Hold", "Total"};
	
	private final ItemDAO itemDAO;
	private final StageLogDAO stageDAO;
	
	public ReportsResource(ItemDAO itemDAO, StageLogDAO stageDAO){
		this.itemDAO = itemDAO;
		this.stageDAO = stageDAO;
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
			excelData += getCell(item.getRevision());
			// Group
			String group = "";
			if (item.getGroup() != null)
				group = item.getGroup().toString();
			excelData += getCell( group );
			List<String> durations = getDurations(item);
			for ( String dur : durations ){
				excelData += getCell( dur );
			}			
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
	 * Goes through the log of the given Item and totals the durations of each
	 * stage.
	 * 
	 * @param item
	 * @return
	 */
	private List<String> getDurations(Item item) {
		if (item.getCurrentStage() == null)
			return null;
		
		List<StageLog> stageLogs = stageDAO.getByItem(item.getId()).get();
		Integer[] durations = new Integer[] {0,0,0,0,0,0,0,0};
		int previousStage = -1;
		Date previousDate = null;
		// This assumes the stageLog is in ascending order by id
		// May need to specify an ORDER BY in the query
		if (stageLogs.size() > 1) {
			for (StageLog log : stageLogs){
				int curStage = log.getStage();
				Date curDate = log.getStageDate();
				
				// Verify it is not the first record in the log
				if ( previousDate != null && curDate != null ){
					// Integration (2-1)
					if ((previousStage == Stages.STAGE1_NUM && curStage == Stages.STAGE2_NUM) ||
							(previousStage == Stages.STAGE1_NUM && curStage == Stages.STAGE0_NUM)) {
						durations[0] += this.getDurationHours(previousDate, curDate);
					}
					// Kitting (3-2)
					if ((previousStage == Stages.STAGE2_NUM && curStage == Stages.STAGE3_NUM) ||
							(previousStage == Stages.STAGE2_NUM && curStage == Stages.STAGE0_NUM)) {
						durations[1] += this.getDurationHours(previousDate, curDate);
					}
					// Awaiting Manufacture (4-3)
					if ((previousStage == Stages.STAGE3_NUM && curStage == Stages.STAGE4_NUM) ||
							(previousStage == Stages.STAGE3_NUM && curStage == Stages.STAGE0_NUM)) {
						durations[2] += this.getDurationHours(previousDate, curDate);
					}
					// Manufacturing (5-4)
					if ((previousStage == Stages.STAGE4_NUM && curStage == Stages.STAGE5_NUM) ||
							(previousStage == Stages.STAGE4_NUM && curStage == Stages.STAGE0_NUM)) {
						durations[3] += this.getDurationHours(previousDate, curDate);
					}
					if ((previousStage == Stages.STAGE5_NUM && curStage == Stages.STAGE6_NUM) ||
							(previousStage == Stages.STAGE5_NUM && curStage == Stages.STAGE0_NUM)) {
						durations[4] += this.getDurationHours(previousDate, curDate);
					}
					// Shipping (7-6)
					if ((previousStage == Stages.STAGE6_NUM && curStage == Stages.STAGE7_NUM) ||
							(previousStage == Stages.STAGE6_NUM && curStage == Stages.STAGE0_NUM)) {
						durations[5] += this.getDurationHours(previousDate, curDate);
					}
					// On Hold (0 - every stage)
					if (previousStage == Stages.STAGE0_NUM) {
						durations[6] += this.getDurationHours(previousDate, curDate);
					}
					
					// Total
					durations[7] += this.getDurationHours(previousDate, curDate);
					
				} 
				previousStage = log.getStage();
				previousDate = log.getStageDate();
			}
		}

		// Place all summed durations into string list
		List<String> durationString = new ArrayList<String>();
		for (int dur : durations) {
			durationString.add(String.valueOf(dur));
		}
		return durationString;
	}
	
	/**
	 * Gets the number of hours between two dates.
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	private Integer getDurationHours(Date start, Date end) {
		if (start == null || end == null)
			return 0;
		DateTime dStart = new DateTime(start.getTime(), DateTimeZone.UTC);
		DateTime dEnd = new DateTime(end.getTime(), DateTimeZone.UTC);
		Integer hours = Hours.hoursBetween(dStart, dEnd).getHours();
		return hours != null ? hours : 0;
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
