package me.byungjin.hostman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import me.byungjin.hostman.events.ConnectionInputEvent;
import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.manager.SystemManager;

public class ClientMan extends Thread{	
	private Socket sock;
	private String ip;
	private String nick;
	private BufferedReader reader;
	private PrintWriter writer;
	
	//Events
	private ConnectionInputEvent hostInputEvent;	
	/**
	 * ���� ���� �� ��� �ʱ�ȭ
	 * @param sock
	 * @throws Exception
	 */
	public ClientMan(String ip) throws Exception {		
		sock = new Socket(ip, ENVIRONMENT.PORT);
		if(this.sock == null) 			
			throw new Exception("This socket is empty!");
		
		this.reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		this.writer = new PrintWriter(sock.getOutputStream());
	}
	/**
	 * ���濡�� cut�� ������, ������ �����մϴ�.
	 */
	public void cut() {
		writer.println(ENVIRONMENT.TAG_CONNECTION_CUT);
		writer.flush();
		end();
	}
	/**
	 * ������ cut�� ������ �ʰ�, ������ ��� �����մϴ�.
	 */
	public void end() {		
		try {						
			reader.close();
			writer.close();
			sock.close();
		} catch (IOException e) {
			SystemManager.catchException(ENVIRONMENT.CLIENTMAN, e);
		}	
	}
	public void send(short tag, String data) {
		if(sock.isConnected())
			writer.write(tag + " " + data);
	}
	
	public void setConnectionInputEvent(ConnectionInputEvent hostInputEvent) {
		this.hostInputEvent = hostInputEvent;
	}
	
	@Override
	public void run() {
		String data;
		SystemManager.message(":Client Open!");
		try {
			while(sock.isConnected()) {					
				if((data = reader.readLine()) != null);
					if(hostInputEvent != null) hostInputEvent.dispatch(this, data);
			}
		}catch (Exception e) { 			
			SystemManager.catchException(ENVIRONMENT.CLIENTMAN, e);
			end();
		}
		SystemManager.message(":Client End!");
	}	
}
