package me.byungjin.manager;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import me.byungjin.game.gui.*;
import resource.ResourceLoader;

public class SystemManager {			
	public static void main(String[] args) {
		try {
			ResourceLoader.init();
			if(Boolean.parseBoolean(args[0])){
				ResourceLoader.getEnv(true);				
				new ServerWindow();
			}
			else{
				ResourceLoader.getEnv(false);
				new ClientWindow();
			}						
		}catch(Exception e){
			catchException(ENVIRONMENT.SYSTEM, e);
		}			
	}
		
	static public void catchException(ENVIRONMENT tag, Exception e) {		
		System.out.println("warning! " + tag + " - " + e.getMessage());				
		DBManager.log(tag, "" + e.getMessage(), true);
	}
	static public void message(ENVIRONMENT tag, String str) {
		System.out.println(": " + tag + " - " + str);
		DBManager.log(tag, str, false);
	}		
	
	static public String getTime() {
		return LocalTime.now().format(DateTimeFormatter.ofPattern("a h:m:s"));
	}
}
