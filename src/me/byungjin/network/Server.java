package me.byungjin.network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.manager.SystemManager;
import me.byungjin.network.event.ClientEvent;
import me.byungjin.network.event.DataComeInEvent;

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
		sockServ = new ServerSocket(nick == null ? ENVIRONMENT.SERVER_PORT.getValue() : ENVIRONMENT.PORT.getValue());
		clients = new Vector<Client>();
		dataComeInEvent = event;
		identify = 0;
	}
	
	@Override
	public void run() {
		running = true;
		SystemManager.message(ENVIRONMENT.SERVER, "start running");
		try {
			while(running) {
				Socket sock = sockServ.accept();
				Client client = new Client(sock, 
						dataComeInEvent, 
						clientExitEvt, 
						identify);
				clients.add(client);
				if(clientEnterEvt != null) clientEnterEvt.dispatch(client);
				SystemManager.message(ENVIRONMENT.SERVER, " Client connect : " + identify);
				identify++;
			}
		}catch(Exception e) {			
			SystemManager.catchException(ENVIRONMENT.SERVER, e);
		}				
		close();
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
		send(PROMISE.CHAT, nick + " " + str);
	}
	@Override
	public void send(PROMISE type, String str) {
		for(Client c : clients) {			
			c.send(type, str);
		}
	}
	@Deprecated
	@Override
	public void sendRAW(String str) {
		
	}

	@Override
	public void open() {
		start();
	}

	@Override
	public void close() {
		try {
			for(Client c : clients) {
				c.close();				
			}
			sockServ.close();
			SystemManager.message(ENVIRONMENT.SERVER, "close");
		}catch(Exception e) {
			SystemManager.catchException(ENVIRONMENT.SERVER, e);
		}
	}	
}
