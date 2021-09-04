package me.byungjin.hostman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import me.byungjin.hostman.events.ConnectionInputEvent;
import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.manager.SystemManager;

public class User extends Thread{
	private int identify;
	private Socket sock;
	private String nick;
	private BufferedReader reader;
	private PrintWriter writer;
	
	/**
	 * 유저 생성 및 멤버 초기화
	 * @param sock
	 * @throws Exception
	 */
	public User(Socket sock, int id) throws Exception {
		this.identify = id;
		this.sock = sock;
		if(this.sock == null) 			
			throw new Exception("This user's Socket is empty!");		
		
		this.reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		this.writer = new PrintWriter(sock.getOutputStream());
	}
	/**
	 * 상대방에게 cut을 보내고, 연결을 중지합니다.
	 */
	public void cut() {
		writer.println(ENVIRONMENT.TAG_CONNECTION_CUT);
		writer.flush();
		end();
	}
	/**
	 * 상대방의 cut을 보내지 않고, 연결을 모두 중지합니다.
	 */
	public void end() {		
		try {			
			reader.close();			
			writer.close();
			sock.close();
		} catch (IOException e) {
			SystemManager.catchException(ENVIRONMENT.USER, e);
		}finally {
			HostMan.removeUserInHost(this);
		}		
	}	
	public void send(short tag, String data) {
		if(sock.isConnected())
			writer.write(tag + " " + data);
	}
	
	@Override
	public void run() {
		String data;
		try {
			while(sock.isConnected()) {						
				if((data = reader.readLine()) != null) {
					HostMan.onGetDataFromUser(this, data);
					if(Short.parseShort(data.split(" ")[0]) == ENVIRONMENT.TAG_CONNECTION_CUT)
						end();
				}				
			} 
		}
		catch (Exception e) { 
			SystemManager.catchException(ENVIRONMENT.USER, e);
			end();
		}
	}
	/**
	 * 비교
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof User) {
			if(((User)obj).getIdentify() == this.identify) 
				return true;			
		}
		return false;
	}
	
	public int getIdentify() {
		return identify;
	}
}
