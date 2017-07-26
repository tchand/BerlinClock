package com.ubs.opsit.interviews.impl;

import java.util.Arrays;

import org.junit.Assert;

import com.ubs.opsit.interviews.TimeConverter;

public class TimeConverterImpl implements TimeConverter {
	enum LightColor {
		OFF("O"), YELLOW("Y"),RED("R");
		
		private String colorCode;
		
		LightColor(String colorCode) {
			this.colorCode = colorCode;
		}
		
		public String getColorCode(){
			return colorCode;
		}
	}
	private static final String LINE_SEPARATOR = "\r\n";
	@Override
	public String convertTime(String aTime) {
		Assert.assertNotNull("Time can't be null", aTime);
		String[] timeArray = aTime.split(":");
		Assert.assertEquals("Invalid Time format!", 3, timeArray.length);
		 int[] parts = Arrays.asList(timeArray).stream().mapToInt(Integer::parseInt).toArray();
	        return  getSecondsLightColorCode(parts[2]) + LINE_SEPARATOR +
	                getTopHoursLightsColorCodes(parts[0]) + LINE_SEPARATOR +
	                getBottomHoursLightsColorCodes(parts[0]) + LINE_SEPARATOR +
	                getTopMinutesLightsColorCodes(parts[1]) + LINE_SEPARATOR +
	                getBottomMinutesLightsColorCodes(parts[1]);
	    }

	    protected String getSecondsLightColorCode(int number) {
	        if (number % 2 == 0) 
	        	return LightColor.YELLOW.getColorCode(); // As it blinks every two seconds.
	        else 
	        	return LightColor.OFF.getColorCode();
	    }

	    protected String getTopHoursLightsColorCodes(int number) {
	        return getOnOffLightColorCodes(4, getTopNumberOfOnSigns(number));
	    }

	    protected String getBottomHoursLightsColorCodes(int number) {
	        return getOnOffLightColorCodes(4, number % 5);
	    }

	    protected String getTopMinutesLightsColorCodes(int number) {
	        return getOnOff(11, getTopNumberOfOnSigns(number), LightColor.YELLOW.getColorCode()).replaceAll("YYY", "YYR"); // replace all to change every quarter to Red.
	    }

	    protected String getBottomMinutesLightsColorCodes(int number) {
	        return getOnOff(4, number % 5, LightColor.YELLOW.getColorCode());
	    }

	    // Default value for onSign would be useful
	    private String getOnOffLightColorCodes(int lamps, int onSigns) {
	        return getOnOff(lamps, onSigns, LightColor.RED.getColorCode());
	    }
	    private String getOnOff(int lamps, int onSigns, String onSign) {
	        String out = "";
	        // String multiplication would be useful
	        for (int i = 0; i < onSigns; i++) {
	            out += onSign;
	        }
	        for (int i = 0; i < (lamps - onSigns); i++) {
	            out += LightColor.OFF.getColorCode();
	        }
	        return out;
	    }

	    private int getTopNumberOfOnSigns(int number) {
	        return (number - (number % 5)) / 5;
	    }
}
