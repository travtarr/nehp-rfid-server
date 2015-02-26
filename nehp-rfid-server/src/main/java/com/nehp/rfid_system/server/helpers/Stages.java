package com.nehp.rfid_system.server.helpers;

public class Stages {
	
	public static final String STAGE1 = "INTEGRATION BEGIN";
	public static final String STAGE2 = "INTEGRATION COMPLETE";
	public static final String STAGE3 = "PARTS KITTED";
	public static final String STAGE4 = "MANUFACTURING BEGIN";
	public static final String STAGE5 = "MANUFACTURING COMPLETE";
	public static final String STAGE6 = "QA/QC COMPLETE";
	public static final String STAGE7 = "SHIPPED";
	public static final String STAGE0 = "ON HOLD";
	
	
	public static final String[] STAGE_POST_STATUS = 
		{ "ON HOLD", 
		"INTEGRATING", 
		"KITTING",
		"AWAITING MFG",
		"MANUFACTURING",
		"QA/QC",
		"SHIPPING",
		"COMPLETE" };
	
	public static final String[] STAGES = { STAGE0, STAGE1, STAGE2, STAGE3, STAGE4,
			STAGE5, STAGE6, STAGE7 };
	
	public static final int NUM_STAGES = STAGES.length;
	public static final int STAGE1_NUM = 1;
	public static final int STAGE2_NUM = 2;
	public static final int STAGE3_NUM = 3;
	public static final int STAGE4_NUM = 4;
	public static final int STAGE5_NUM = 5;
	public static final int STAGE6_NUM = 6;
	public static final int STAGE7_NUM = 7;
	public static final int STAGE0_NUM = 0;
	public static final int STAGE_NOT_SET_NUM = -1;
}