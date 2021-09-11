package me.byungjin.manager;

public class Environment {				
	static public final short SYSTEM = 99;
	static public final short HOSTMAN = 100;
	static public final short CLIENTMAN = 101;
	static public final short USER = 102;
	static public final short SERVER = 103;
	static public final short GUEST = 104;
	static public final short DB = 105;
	static public final short ENVIRONMENT = 106;
	static public final short ASSETSMANAGER = 107;
	static public final short M_SERVER = 108;
	
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
