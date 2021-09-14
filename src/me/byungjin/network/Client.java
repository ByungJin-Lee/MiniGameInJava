package me.byungjin.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.manager.SystemManager;
import me.byungjin.network.event.ClientEvent;
import me.byungjin.network.event.DataComeInEvent;

public class Client extends Thread implements Agent {
	private Socket sock;
	private PrintWriter sender;
	private BufferedReader reader;
	private DataComeInEvent dataComeInEvent;
	private ClientEvent clientExitEvent;
	private int identify = -1;
	private String nick;
	
	/**
	 * 중앙 서버 접근용 클라이언트
	 * @param event
	 * @throws Exception
	 */
	public Client(DataComeInEvent event) throws Exception {
		sock = new Socket(ENVIRONMENT.SERVER_IP, ENVIRONMENT.SERVER_PORT.getValue());		
		init(event);
	}
	/**
	 * 사용자 서버 접근용 클라이언트
	 * @param ip
	 * @param event
	 * @throws Exception
	 */
	public Client(DataComeInEvent event, String ip) throws Exception{
		sock = new Socket(ip, ENVIRONMENT.PORT.getValue());
		init(event);
	}
	/**
	 * 서버 내에서 관리되는 클라이언트 자원
	 * @param sock
	 * @param event
	 * @param identify
	 * @throws Exception
	 */
	public Client(Socket sock, DataComeInEvent event, ClientEvent clientExitEvent, int identify) throws Exception {
		this.sock = sock;
		this.clientExitEvent = clientExitEvent;
		this.identify = identify;
		init(event);
	}
	
	private void init(DataComeInEvent event) throws Exception {
		dataComeInEvent = event;				
		sender = new PrintWriter(sock.getOutputStream());
		reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	}	
	public int getIdentify() {
		return identify;
	}
	@Override
	public void run() {
		SystemManager.message(ENVIRONMENT.CLIENT, identify + " connect");
		String buffer;
		try {
			while(sock.isConnected()) {
				if((buffer = reader.readLine()) != null)
					dataComeInEvent.dispatch(this, buffer);
			}							
		}catch(Exception e) {
			close();
			SystemManager.catchException(ENVIRONMENT.CLIENT, e);
			if(clientExitEvent != null)
				clientExitEvent.dispatch(this);
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
	public void send(PROMISE type, String str) {
		if(sock.isConnected())
			sendRAW(type + " " + str);
	}
	@Override
	public void sendRAW(String str) {
		if(sock.isConnected())
			sender.println(str);
	}
	@Override
	public void open() {
		start();
	}
	@Override
	public void close() {
		try {
			sender.close();
			reader.close();
			sock.close();
			SystemManager.message(ENVIRONMENT.CLIENT, identify + " disconnect");
		}catch(Exception e) {			
			SystemManager.catchException(ENVIRONMENT.CLIENT, e);
		}
	}
}
