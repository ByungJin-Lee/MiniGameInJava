package me.byungjin.manager;

import java.util.ArrayList;

import me.byungjin.db.DBConnection;
import me.byungjin.db.LogSchema;
import me.byungjin.game.gui.ServerWindow;
import me.byungjin.game.gui.ChatWindow;
import me.byungjin.game.gui.OmokWindow;

public class SystemManager {	
	static private DBConnection conn;
	
	public static void main(String[] args) {
		try {
			AssetManager.init();
			if(true) //TODO Boolean.parseBoolean(args[0])			
				AssetManager.getEnv(true); 								
			else 
				AssetManager.getEnv(false);
			new ChatWindow();
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
