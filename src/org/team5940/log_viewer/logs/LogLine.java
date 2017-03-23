package org.team5940.log_viewer.logs;

import java.util.ArrayList;

public class LogLine {
	private String stamp1;
	private String stamp2;
	private String stamp3;
	private String stamp4;
	private String stamp5;

	public LogLine(ArrayList<String> out) {
		stamp1 = out.get(0);
		stamp2 = out.get(1);
		stamp3 = out.get(2);
		stamp4 = out.get(3);
		try {
			stamp5 = out.get(4);
		} catch (IndexOutOfBoundsException e) {
			stamp5 = null;
		}
	}
	public String getLine() {
		return '<' + stamp1 + "><" + stamp2 + "><" + stamp3 + "><" + stamp4 + '>' + ((stamp5 != null)?("<" + stamp5 + ">"):"");
	}
	public long getTime() {
		return Long.parseLong(stamp1);
	}
	public String getStamp(int i) {
		switch(i) {
		case 1:
			return stamp1;
		case 2:
			return stamp2;
		case 3:
			return stamp3;
		case 4:
			return stamp4;
		case 5:
			return stamp5;
			
		}
		return "Not a valid line number.";
	}
	
	
}
