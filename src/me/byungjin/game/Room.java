package me.byungjin.game;

import me.byungjin.manager.NetworkManager;

public class Room {
	private String name;
	private boolean pw;
	private String password;
	private GameKind kind;
	private String ip;	
	private String innerIp;	
	
	public Room(String ip, String name, GameKind kind) {
		this.ip = ip;
		this.pw = false;
		this.password = "";
		this.name = name;
		this.kind = kind;
	}	
	
	public Room(String ip, String name, String pw, GameKind kind) {
		this(ip, name, kind);
		this.password = pw;
		this.pw = true;
	}	
	
	public void setInnerIp(String innerIp) {
		this.innerIp = innerIp;
	}
	public String getInnerIp() {
		return this.innerIp;
	}
	
	public String getRoomName() {
		return this.name;
	}
	public String getPassWord() {
		return this.password;
	}
	public boolean isPassword() {
		return this.pw;
	}
	public GameKind getGameKind() {
		return this.kind;
	}
	public String getIP() {
		return this.ip;
	}
	public String toItemStr() {
		return ip + " " + name + " " + kind;
	}	
	public String toItemStrLan() {
		return innerIp + " " + name + " " + kind;
	}
	
	public boolean isLan(String ip) {
		return this.ip.equals(ip);
	}
	
	@Override
	public String toString() {	
		String local = NetworkManager.getNetworkIp();
		if(!pw) {
			return local + " " + name + " " + kind + " " + "!!!!!";
		}else {
			return local + " " + name + " " + kind + " " + password; 
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Room) {
			if(((Room) obj).name.equals(name))
				return true;
		}
		return false;
	}	
}
