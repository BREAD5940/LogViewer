package org.team5940.log_viewer.logs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LogReaderOpen {
	
	public static void readFiles() {
		File folderName = new File("C:\\Users\\Raian\\Documents\\Projects\\logs");
		String line;
		
		try {
			ArrayList<LogLine> logs = new ArrayList<LogLine>();
			ArrayList<File> logFiles = new ArrayList<File>();
			findLogFiles(logFiles,folderName);
			for(File logFile: logFiles) {
				FileReader fileReader = new FileReader(logFile);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				line = bufferedReader.readLine();
				while(line != null) {
					ArrayList<String> out = new ArrayList<String>();
					int opens = 0;
					for(int i = 0; i < line.length(); i++) {
						char c = line.charAt(i);
						if(c == '<') opens++;
						else if(c == '>') opens--;
						if(opens == 0 && i > 0) {
							out.add(line.substring(1, i));
							line = line.substring(i+1);
							i = -1;
							
						}
					}
					LogLine logLine = new LogLine(out);
					logs.add(logLine);
					line = bufferedReader.readLine();
				}
				bufferedReader.close();
			}
			logs.sort(new LogLineComparator());
			for(LogLine logLine:logs) {
				System.out.println(logLine.getLine());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void findLogFiles(ArrayList<File> logFiles, File folder) {
		File[] arrayFiles = folder.listFiles();
		for(File file: arrayFiles) {
			if(file.isFile()){
				logFiles.add(file);
			} else if(file.isDirectory()) {
				findLogFiles(logFiles, file);
			}
		}
	}
	public ArrayList<LogLine> returnLogLines(File folder) {		

		String line;
		try {
			ArrayList<LogLine> logs = new ArrayList<LogLine>();
			ArrayList<File> logFiles = new ArrayList<File>();
			findLogFiles(logFiles,folder);
			for(File logFile: logFiles) {
				FileReader fileReader = new FileReader(logFile);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				line = bufferedReader.readLine();
				while(line != null) {
					ArrayList<String> out = new ArrayList<String>();
					int opens = 0;
					for(int i = 0; i < line.length(); i++) {
						char c = line.charAt(i);
						if(c == '<') opens++;
						else if(c == '>') opens--;
						if(opens == 0 && i > 0) {
							out.add(line.substring(1, i));
							line = line.substring(i+1);
							i = -1;
							
						}
					}
					LogLine logLine = new LogLine(out);
					logs.add(logLine);
					line = bufferedReader.readLine();
				}
				bufferedReader.close();
			}
			logs.sort(new LogLineComparator());
			return logs;
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<LogLine>();
	}
		
}
