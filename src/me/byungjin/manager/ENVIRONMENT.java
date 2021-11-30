package me.byungjin.manager;

public enum ENVIRONMENT {
	SYSTEM(0), 
	SERVER(1), 
	CLIENT(2), 
	DB(3), 
	EVVIRONMENT(4), 
	RESOURCELOADER(5), 
	GUI(6), 
	ROOM(7),
	SERVER_PORT(3188), 
	PORT(3182);
	
	private short value;
	private ENVIRONMENT(int val) {
		value = (short)val;
	}
	public short getValue() {
		return value;
	}
	
	//About Server
	static public String SERVER_IP = "0";	
	//About DB
	static public String DB_IP = "0";
	static public String DB_PORT = "0";
	static public String DB_ID = "0";
	static public String DB_PW = "0";
}
