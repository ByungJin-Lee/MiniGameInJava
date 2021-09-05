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
	 * 소켓 생성 및 멤버 초기화
	 */
	public HostMan() throws Exception {		
		identify = 0;
		sockServ = new ServerSocket(ENVIRONMENT.PORT);		
	}	
	/**
	 * @return String - Host의 주소 반환
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
	 * 현재 연결되어 있는 User들을 반환
	 */
	static public Vector<User> getUserInHost(){
		return users;
	}
	/**
	 * User들에서 해당 유저 삭제
	 */
	static public void removeUserInHost(User target) {		
		users.removeElement(target);
		if(userConnectionEndEvent != null)
			userConnectionEndEvent.dispatch(target);
	}
	/**
	 * 데이터가 들어오는 경우 호출됨
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
	 * Host 종료
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
	 * 등록된 User에게 모두 cut 신호를 보내고, 종료합니다.
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
	 * User에게 모두 데이터 전송
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
