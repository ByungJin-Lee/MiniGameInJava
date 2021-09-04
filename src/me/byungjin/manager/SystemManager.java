package me.byungjin.manager;

public class SystemManager {	
	static public void catchException(short tag, Exception e) {		
		System.out.println(tag + " - " + e.getMessage());
	}
	static public void message(String str) {
		System.out.println(str);
	}
}
