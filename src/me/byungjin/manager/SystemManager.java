package me.byungjin.manager;

public class SystemManager {	
	static public void catchException(short tag, Exception e) {		
		System.out.println(tag + " - " + e.getMessage());
		e.printStackTrace();
	}
	static public void message(short tag, String str) {
		System.out.println(":" + tag + " - " + str);
	}	
}
