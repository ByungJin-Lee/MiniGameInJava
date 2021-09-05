package me.byungjin.hostman;

public interface Man {
	void sendNick();
	void send(short tag, String data);
	void work();
}
