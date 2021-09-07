package me.byungjin.hostman;

public interface Man {	
	void chat(String data);
	void send(short tag, String data);
	void work();	
	void setNick(String nick);
}
