package me.byungjin.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Environment {
	private static File env;
	
	static public boolean getEnvironment(boolean admin) {
		try {
			env = new File("./env.txt");
			System.out.println(env.exists());
			if(env.exists()) {
				read(admin);
				return true;
			}else {
				env.createNewFile();
				init(admin);
				return false;
			}
		}catch(Exception e) {
			SystemManager.catchException(ENVIRONMENT, e);
			return false;
		}		
	}
	static private void init(boolean admin) throws Exception{
		PrintWriter writer = new PrintWriter(new FileWriter(env));
		writer.println("SERVER_IP=0");
		
		if(!admin) return;
		
		writer.println("DB_IP=0");
		writer.println("DB_PORT=0");
		writer.println("DB_ID=0");
		writer.println("DB_PW=0");
		writer.close();
	}
	
	
	static private void read(boolean admin) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(env));
		SERVER_IP = reader.readLine();		
		SERVER_IP = SERVER_IP.substring(SERVER_IP.indexOf("=") + 1);
		
		if(!admin) return;
		
		DB_IP = reader.readLine();
		DB_IP = DB_IP.substring(DB_IP.indexOf("=") + 1);
		DB_PORT = reader.readLine();
		DB_PORT = DB_PORT.substring(DB_PORT.indexOf("=") + 1);
		DB_ID = reader.readLine();
		DB_ID = DB_ID.substring(DB_ID.indexOf("=") + 1);
		DB_PW = reader.readLine();		
		DB_PW = DB_PW.substring(DB_PW.indexOf("=") + 1);
		reader.close();
	}	
	static public void save() {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(env));
			writer.println("SERVER_IP=" + SERVER_IP);
			writer.println("DB_IP=" + DB_IP);
			writer.println("DB_PORT=" + DB_PORT);
			writer.println("DB_ID=" + DB_ID);
			writer.println("DB_PW=" + DB_PW);
			writer.close();
		}catch(Exception e) {
			SystemManager.catchException(ENVIRONMENT, e);
		}
	}
	static public void show() {
		System.out.println(SERVER_IP);
		System.out.println(DB_IP);
		System.out.println(DB_PORT);
		System.out.println(DB_ID);
		System.out.println(DB_PW);
	}
	
	
	static public final short HOSTMAN = 100;
	static public final short CLIENTMAN = 101;
	static public final short USER = 102;
	static public final short SERVER = 103;
	static public final short GUEST = 104;
	static public final short DB = 105;
	static public final short ENVIRONMENT = 106;
	
	static public final short MINI = 200;
	static public final short MINI_CATCHMIND = 201;	
	static public final short MINI_TETRIS = 203;
	
	
	//About Server
	static public String SERVER_IP = "0";
	
	//About DB
	static public String DB_IP = "0";
	static public String DB_PORT = "0";
	static public String DB_ID = "0";
	static public String DB_PW = "0";
	
	
	static public final short SERVER_PORT = 3188;
	static public final short LOGIN = 600;	
	static public final short REGISTER = 601;	
	
	//About Server Result
	static public final short LOGIN_SUCCESS = 650;
	static public final short LOGIN_FAIL = 651;
	
	static public final short SAME_IDENTIFY = 652;
	static public final short REGISTER_SUCCESS = 653;	
	
	//About Configuration
	static final public short PORT = 3182;
	
	//About Connection
	static final public short TAG_CONNECTION_CUT = 666; 

}
