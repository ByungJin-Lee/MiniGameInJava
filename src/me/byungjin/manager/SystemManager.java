package me.byungjin.manager;

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
				new LoginWindow();
			}
		}catch(Exception e){
			catchException(ENVIRONMENT.SYSTEM, e);
		}			
	}
		
	static public void catchException(ENVIRONMENT tag, Exception e) {		
		System.out.println("warning! " + tag + " - " + e.getMessage());
//		e.printStackTrace();
		DBManager.log(tag, e.getMessage(), true);
	}
	static public void message(ENVIRONMENT tag, String str) {
		System.out.println(": " + tag + " - " + str);
		DBManager.log(tag, str, false);
	}		

}
