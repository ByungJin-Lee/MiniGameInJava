package me.byungjin.db;

import java.sql.Time;

public class LogSchema {	
	
	public LogSchema(String source, String content, boolean warning,  Time time) {
		this.source = source;
		this.warning = warning;
		this.content = content;
		this.time = time;
	}
	
	private String source;
	private boolean warning;
	private String content;
	private Time time;
	
	public String getSource() {
		return source;
	}

	public boolean isWarning() {
		return warning;
	}

	public String getContent() {
		return content;
	}

	public Time getTime() {
		return time;
	}

	public static String[] getHeader() {
		return new String[] {"Source", "Warning", "Content", "Time"};
	}
	@Override
	public String toString() { 
		return source + " " + warning + " " + content + " " + time.toString();
	}
}
