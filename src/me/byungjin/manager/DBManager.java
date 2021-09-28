package me.byungjin.manager;

import java.util.ArrayList;

import me.byungjin.db.DBConnection;
import me.byungjin.db.LogSchema;

public class DBManager {
	static private DBConnection conn;
	
	static public DBConnection connectDB() {
		if(conn == null || !conn.isConnect)
			conn = new DBConnection();
		return conn;
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
	static public DBConnection getDBConn(){
		return conn;
	}
	static public void log(ENVIRONMENT tag, String str, boolean warning) {
		if(conn != null && conn.isConnect) {
			conn.log(tag, str, warning);
		}
	}
	static public boolean isConnection() {
		if(conn != null && conn.isConnect)
			return true;
		return false;
	}
}
