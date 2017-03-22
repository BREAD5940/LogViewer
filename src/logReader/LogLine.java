package logReader;

import java.util.ArrayList;

public class LogLine {
	private String stamp1;
	private String stamp2;
	private String stamp3;
	private String stamp4;

	public LogLine(ArrayList<String> out) {
		stamp1 = out.get(0);
		stamp2 = out.get(1);
		stamp3 = out.get(2);
		try {
			stamp4 = out.get(3);
		} catch (IndexOutOfBoundsException e) {
			stamp4 = null;
		}
	}
	public String getLine() {
		return '<' + stamp1 + "><" + stamp2 + "><" + stamp3 + ">" + ((stamp4 != null)?("<" + stamp4 + ">"):"");
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
			
		}
		return "Not a valid line number.";
	}
	
	
}
