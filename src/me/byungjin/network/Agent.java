package me.byungjin.network;

public interface Agent {
	void chat(String str);
	void work();
	void send(short tag, String str);
	void sendRAW(String str);
}
