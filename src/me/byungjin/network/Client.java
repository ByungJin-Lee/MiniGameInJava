package me.byungjin.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.manager.SystemManager;
import me.byungjin.network.event.DataComeInEvent;

public class Client extends Thread implements Agent {
	private Socket sock;
	private PrintWriter sender;
	private BufferedReader reader;
	private DataComeInEvent dataComeInEvent;
	private int identify;
	private String nick;
	
	//for Access Server
	public Client(DataComeInEvent event) throws Exception {
		sock = new Socket(ENVIRONMENT.SERVER_IP, ENVIRONMENT.SERVER_PORT.getValue());		
		init(event);
	}
	//for Access Host
	public Client(String ip, DataComeInEvent event) throws Exception{
		sock = new Socket(ip, ENVIRONMENT.PORT.getValue());
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
			SystemManager.catchException(ENVIRONMENT.CLIENT, e);
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
			SystemManager.catchException(ENVIRONMENT.CLIENT, e);
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
		send(PROMISE.CHAT, nick + " " +str);
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
