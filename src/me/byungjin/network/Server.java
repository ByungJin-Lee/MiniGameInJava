package me.byungjin.network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.manager.SystemManager;
import me.byungjin.network.event.ClientEvent;
import me.byungjin.network.event.DataComeInEvent;

public class Server extends Agent {	
	private ServerSocket sockServ;	
	private int identify = 0;
	private Vector<Client> clients;
		
	//Events	
	private ClientEvent clientExitEvt;
	private ClientEvent clientEnterEvt;
	
	public Server(String nick) throws Exception {		
		this.nick = nick;
		sockServ = new ServerSocket(nick == null ? ENVIRONMENT.SERVER_PORT.getValue() : ENVIRONMENT.PORT.getValue());
		clients = new Vector<Client>();		
		identify = 0;
		clientExitEvt = new ClientEvent() {
			@Override
			public void dispatch(Client source) {
				clients.remove(source);
			}
		};
	}
	
	@Override
	public void run() {
		running = true;
		SystemManager.message(ENVIRONMENT.SERVER, "start running");
		try {
			while(running) {
				Socket sock = sockServ.accept();
				Client client = new Client(sock, 
						chatComeInEvent, 
						otherComeInEvent,
						gameComeInEvent,
						clientExitEvt,
						identify);
				clients.add(client);
				client.open();
				if(clientEnterEvt != null) clientEnterEvt.dispatch(client);
				SystemManager.message(ENVIRONMENT.SERVER, " Client connect : " + identify);
				identify++;
			}
		}catch(Exception e) {			
			SystemManager.catchException(ENVIRONMENT.SERVER, e);
		}finally {
			close();			
			SystemManager.message(ENVIRONMENT.SERVER, "close");
		}		
	}	
	public void removeClient(Client client) {
		clients.remove(client);
	}	
	public void broadcast(String str) {
		
	}
	public void sendRAWExceptionSpecial(Client c, String str) {
		for(Client cl : clients) {
			if(cl.equals(c)) continue;
			cl.sendRAW(str);
		}
	}
	public int getNumberOfClients(){
		return clients.size();
	}
	@Override
	public void chat(String str) {
		send(PROMISE.CHAT, str);
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
			if(!sockServ.isClosed())
				sockServ.close();			
		}catch(Exception e) {
			SystemManager.catchException(ENVIRONMENT.SERVER, e);
		}
		interrupt();
		running = false;
	}	
	@Override
	public synchronized void addChatComeInEvent(DataComeInEvent event) {
		super.addChatComeInEvent(event);
		for(Agent a : clients) {
			a.addChatComeInEvent(event);
		}
	}
	@Override
	public synchronized void addOtherComeInEvent(DataComeInEvent event) {
		super.addOtherComeInEvent(event);
		for(Agent a : clients) {
			a.addOtherComeInEvent(event);
		}
	}
	@Override
	public synchronized void addGameComeInEvent(DataComeInEvent event) {
		super.addGameComeInEvent(event);
		for(Agent a : clients) {
			a.addGameComeInEvent(event);
		}
	}
}
