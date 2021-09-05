package me.byungjin.hostman;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import me.byungjin.hostman.events.HostCloseServEvent;
import me.byungjin.hostman.events.HostOpenServEvent;
import me.byungjin.hostman.events.UserConnectionEndEvent;
import me.byungjin.hostman.events.UserEnterHostEvent;
import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.manager.SystemManager;
import me.byungjin.hostman.events.ConnectionInputEvent;

public class HostMan extends Thread implements Man{
	private String nick;
	private int identify;
	private ServerSocket sockServ;
	private boolean running;
	static private Vector<User> users = new Vector<User>();
	
	//Events
	static private UserConnectionEndEvent userConnectionEndEvent;
	static private ConnectionInputEvent userInputEvent;	
	private HostCloseServEvent hostCloseServEvent;
	private HostOpenServEvent hostOpenServEvent;
	private UserEnterHostEvent userEnterHostEvent;

	/**
	 * ���� ���� �� ��� �ʱ�ȭ
	 */
	public HostMan() throws Exception {		
		identify = 0;
		sockServ = new ServerSocket(ENVIRONMENT.PORT);		
	}	
	/**
	 * @return String - Host�� �ּ� ��ȯ
	 */
	static public String getHostIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		}catch(Exception e) {
			SystemManager.catchException(ENVIRONMENT.HOSTMAN, e);
			return null;
		}
	}	
	/**
	 * ���� ����Ǿ� �ִ� User���� ��ȯ
	 */
	static public Vector<User> getUserInHost(){
		return users;
	}
	/**
	 * User�鿡�� �ش� ���� ����
	 */
	static public void removeUserInHost(User target) {		
		users.removeElement(target);
		if(userConnectionEndEvent != null)
			userConnectionEndEvent.dispatch(target);
	}
	/**
	 * �����Ͱ� ������ ��� ȣ���
	 */
	static public void onGetDataFromUser(User user, String data) {
		SystemManager.message("GUEST"+ user.getIdentify() + " > " + data);
		if(userInputEvent != null)
			userInputEvent.dispatch(user, data);
	}
	
	//Register
	static public void registerUserInputEvent(ConnectionInputEvent event) {
		userInputEvent = event;
	}
	static public void registerUserConnectionEndEvent(UserConnectionEndEvent event) {
		userConnectionEndEvent = event;
	}	
	
	@Override
	public void run() {
		running = true;
		SystemManager.message(":Server Open!");
		if(hostOpenServEvent != null) hostOpenServEvent.dispatch(this);
		try {
			while(running && !sockServ.isClosed()) {			
				Socket sock = sockServ.accept();
				User user = new User(sock, identify++);				
				user.start();				
				//append User
				users.add(user);
				//dispatchEvent				
				if(userEnterHostEvent != null) userEnterHostEvent.dispatch(user);
				//print
				SystemManager.message(":guest enter - " + user.getIdentify());
			}	
		}catch(Exception e) {
			SystemManager.catchException(ENVIRONMENT.HOSTMAN, e);
			try {
				close();
			} catch (Exception e1) {
				SystemManager.catchException(ENVIRONMENT.HOSTMAN, e);
			}
		}			
	}
	/**
	 * Host ����
	 */
	public void close() throws Exception {
		running = false;
		cleanUpUsers();	
		sockServ.close();		
		SystemManager.message(":Server End!");
		//Event
		if(hostCloseServEvent != null) hostCloseServEvent.dispatch(this);
	}
	/**
	 * ��ϵ� User���� ��� cut ��ȣ�� ������, �����մϴ�.
	 */
	public void cleanUpUsers() {
		for(User u : users) {
			u.cut();
		}
	}		
	@Override
	public void sendNick() {		
		send(ENVIRONMENT.NICK, nick);
	}
	/**
	 * User���� ��� ������ ����
	 */
	@Override
	public void send(short tag, String data) {		
		for(User u : users) {
			u.send(tag, data);
		}
	}
	@Override
	public void work() {
		start();
	}
}
