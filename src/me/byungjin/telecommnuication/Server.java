package me.byungjin.telecommnuication;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import me.byungjin.manager.Environment;
import me.byungjin.manager.SystemManager;
import me.byungjin.telecommnuication.event.ClientEvent;
import me.byungjin.telecommnuication.event.DataComeInEvent;

public class Server extends Thread implements Agent {
	private boolean running;
	private ServerSocket sockServ;	
	private int identify = 0;
	private Vector<Client> clients;
	private String nick;
	
	//Events
	private DataComeInEvent dataComeInEvent;	
	private ClientEvent clientExitEvt;
	private ClientEvent clientEnterEvt;
	
	public Server(DataComeInEvent event, String nick) throws Exception {		
		this.nick = nick;
		sockServ = new ServerSocket(nick == null ? Environment.SERVER_PORT : Environment.PORT);
		clients = new Vector<Client>();
		dataComeInEvent = event;
		identify = 0;
	}
	
	@Override
	public void run() {
		running = true;						
		while(running) {
			try {
				Socket sock = sockServ.accept();
				Client client = new Client(sock, dataComeInEvent, identify);
				clients.add(client);
				if(clientEnterEvt != null) clientEnterEvt.dispatch(client);
				identify++;
			}catch(Exception e) {
				SystemManager.catchException(Environment.SERVER, e);
			}
		}
		
	}	
	public void removeClient(Client client) {
		clients.remove(client);
	}	
	public void broadcast(String str) {
		
	}
	public void sendRAWExcpetionSpecial(Client c, String str) {
		for(Client cl : clients) {
			if(cl.equals(c)) continue;
			cl.sendRAW(str);
		}
	}
	@Override
	public void chat(String str) {
		send(Promise.CHAT, nick + " " + str);
	}
	@Override
	public void work() {
		start();
	}
	@Override
	public void send(short tag, String str) {
		for(Client c : clients) {
			c.send(tag, str);
		}
	}
	@Deprecated
	@Override
	public void sendRAW(String str) {
		
	}	
}
