package me.byungjin.telecommnuication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import me.byungjin.manager.Environment;
import me.byungjin.manager.SystemManager;
import me.byungjin.telecommnuication.event.DataComeInEvent;

public class Client extends Thread implements Agent {
	private Socket sock;
	private PrintWriter sender;
	private BufferedReader reader;
	private DataComeInEvent dataComeInEvent;
	private int identify;
	private String nick;
	
	//for Access Server
	public Client(DataComeInEvent event) throws Exception {
		sock = new Socket(Environment.SERVER_IP, Environment.SERVER_PORT);		
		init(event);
	}
	//for Access Host
	public Client(String ip, DataComeInEvent event) throws Exception{
		sock = new Socket(ip, Environment.PORT);
		init(event);
	}
	//used in Server
	public Client(Socket sock, DataComeInEvent event, int identify) throws Exception {
		this.sock = sock;
		init(event);
	}
	
	private void init(DataComeInEvent event) throws Exception {
		dataComeInEvent = event;				
		sender = new PrintWriter(sock.getOutputStream());
		reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	}
	
	public void end() {
		try {
			sender.close();
			reader.close();
			sock.close();
		}catch(Exception e) {			
			SystemManager.catchException(Environment.GUEST, e);
		}
	}		
	public int getIdentify() {
		return identify;
	}
	@Override
	public void run() {
		String buffer;
		try {
			while(sock.isConnected()) {
				if((buffer = reader.readLine()) != null)
					dataComeInEvent.dispatch(this, buffer);
			}							
		}catch(Exception e) {
			SystemManager.catchException(Environment.GUEST, e);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Client) {
			if(((Client)obj).getIdentify() == identify)
				return true;
		}
		return false;
	}
	@Override
	public void chat(String str) {
		send(Promise.CHAT, nick + " " +str);
	}
	@Override
	public void work() {
		start();
	}
	@Override
	public void send(short tag, String str) {
		if(sock.isConnected())
			sender.println(tag + " " + str);
	}
	@Override
	public void sendRAW(String str) {
		if(sock.isConnected())
			sender.println(str);
	}
}
