package me.byungjin.network;

public interface Agent {
	void chat(String str);
	void open();
	void send(PROMISE type, String str);
	void sendRAW(String str);
	void close();
}
