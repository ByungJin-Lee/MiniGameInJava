package me.byungjin.manager;

import java.util.ArrayList;

import me.byungjin.database.DBConnection;
import me.byungjin.database.LogSchema;
import me.byungjin.minigame.gui.MasterWindow;
import me.byungjin.minigame.gui.OmokWindow;

public class SystemManager {	
	static private DBConnection conn;
	
	public static void main(String[] args) {
		try {
			AssetManager.init();
			if(true) //TODO Boolean.parseBoolean(args[0])			
				AssetManager.getEnv(true); 								
			else 
				AssetManager.getEnv(false);			
			new OmokWindow();
		}catch(Exception e){
			catchException(ENVIRONMENT.SYSTEM, e);
		}			
	}
	
	static public void connectDB() {
		if(conn == null || !conn.isConnect)
			conn = new DBConnection();
	}
	static public void disconnectDB() {
		if(conn != null && conn.isConnect)
			conn.close();
	}
	static public ArrayList<LogSchema> getLog() {
		if(conn != null && conn.isConnect)
			return conn.getLog();
		return null;
	}	
	static public void catchException(ENVIRONMENT tag, Exception e) {		
		System.out.println(tag + " - " + e.getMessage());
		e.printStackTrace();
		log(tag, e.getMessage(), true);
	}
	static public void message(ENVIRONMENT tag, String str) {
		System.out.println(":" + tag + " - " + str);
		log(tag, str, false);
	}	
	static private void log(ENVIRONMENT tag, String str, boolean warning) {
		if(conn != null && conn.isConnect) {
			conn.log(tag, str, warning);
		}
	}
}
