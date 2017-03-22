package org.team5940.log_viewer.logs;

import java.util.Comparator;

public class LogLineComparator implements Comparator<LogLine> {

	@Override
	public int compare(LogLine arg0, LogLine arg1) {
		if(arg0.getTime() > arg1.getTime()) {
			return 1;
		} else if(arg0.getTime() == arg1.getTime()){
			return 0;
		} else {
			return -1;
		}
	}
	
}
