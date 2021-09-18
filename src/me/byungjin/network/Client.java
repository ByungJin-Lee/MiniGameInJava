package me.byungjin.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.manager.SystemManager;
import me.byungjin.network.event.ClientEvent;
import me.byungjin.network.event.DataComeInEvent;

public class Client extends Agent {
	private Socket sock;
	private PrintWriter sender;
	private BufferedReader reader;	
	private ClientEvent clientExitEvent;
	private int identify = -1;	
	
	/**
	 * 중앙 서버 접근용 클라이언트
	 * @param event
	 * @throws Exception
	 */
	public Client() throws Exception {
		sock = new Socket(ENVIRONMENT.SERVER_IP, ENVIRONMENT.SERVER_PORT.getValue());		
		init();
	}
	/**
	 * 사용자 서버 접근용 클라이언트
	 * @param ip
	 * @param event
	 * @throws Exception
	 */
	public Client(String ip) throws Exception{
		sock = new Socket(ip, ENVIRONMENT.PORT.getValue());
		init();
	}
	/**
	 * 서버 내에서 관리되는 클라이언트 자원
	 * @param sock
	 * @param event
	 * @param identify
	 * @throws Exception
	 */
	public Client(Socket sock, 
			DataComeInEvent chatEvent, 
			DataComeInEvent otherEvent, 
			DataComeInEvent gameEvent,
			ClientEvent clientExitEvent, 
			int identify) throws Exception {
		this.sock = sock;
		this.clientExitEvent = clientExitEvent;
		this.identify = identify;
		this.chatComeInEvent = chatEvent;
		this.otherComeInEvent = otherEvent;
		this.gameComeInEvent = gameEvent;
		init();
	}
	
	private void init() throws Exception {					
		sender = new PrintWriter(sock.getOutputStream(), true);
		reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	}	
	public int getIdentify() {
		return identify;
	}
	@Override
	public void run() {
		running = true;
		SystemManager.message(ENVIRONMENT.CLIENT, " connect : " + identify);
		String buffer;
		try {
			while(sock.isConnected()) {
				if((buffer = reader.readLine()) != null)
					comeInRouter(identify, buffer);
			}							
		}catch(Exception e) {
			close();
			SystemManager.catchException(ENVIRONMENT.CLIENT, e);			
		}
		running = false;
		if(clientExitEvent != null)
			clientExitEvent.dispatch(this);
		SystemManager.message(ENVIRONMENT.CLIENT, "disconnect : " + identify);
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
		sendRAW(type + " " + str);
	}
	@Override
	public void sendRAW(String str) {		
		super.sendRAW(str);
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
		}catch(Exception e) {			
			SystemManager.catchException(ENVIRONMENT.CLIENT, e);
		}
	}	
}
