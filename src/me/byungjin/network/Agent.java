package me.byungjin.network;

import java.util.StringTokenizer;

import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.manager.SystemManager;
import me.byungjin.network.event.DataComeInEvent;

public abstract class Agent extends Thread {
	protected boolean running;
	protected String nick;
	DataComeInEvent gameComeInEvent;
	DataComeInEvent chatComeInEvent;
	DataComeInEvent otherComeInEvent;
	
	public Agent() {
		gameComeInEvent = null;
		chatComeInEvent = null;
		otherComeInEvent = null;
	}
	
	/**
	 * ä�� �Է�
	 * @param str
	 */
	public abstract void chat(String str);
	/**
	 * ���� ����
	 */
	public abstract void open();
	/**
	 * ������ ������
	 * @param type
	 * @param str
	 */
	public abstract void send(PROMISE type, String str);
	/**
	 * �±� ���� ������ ������
	 * @param str
	 */
	public void sendRAW(String str) {
		SystemManager.message(ENVIRONMENT.CLIENT, str);
	}
	/**
	 * ���� ����
	 */
	public abstract void close();

	public void block(){
		close();
		interrupt();
	}

	/**
	 * ���� Ȯ��
	 * @return
	 */
	public boolean isRunning() {		
		return running;
	}
	/**
	 * ä�� �̺�Ʈ ���
	 */	
	public synchronized void addChatComeInEvent(DataComeInEvent event) {
		chatComeInEvent = event;		
	}
	/**
	 * �ٸ� �̺�Ʈ ���
	 */	
	public synchronized void addOtherComeInEvent(DataComeInEvent event) {
		otherComeInEvent = event;		
	}
	/**
	 * ���� �̺�Ʈ ���
	 */	
	public synchronized void addGameComeInEvent(DataComeInEvent event) {
		gameComeInEvent = event;		
	}
	/**
	 * �̺�Ʈ �����
	 * @param source
	 * @param data
	 */
	void comeInRouter(int identify, String data) {
		SystemManager.message(ENVIRONMENT.CLIENT, identify + " / " + data);
		StringTokenizer tokens = new StringTokenizer(data);
		
		switch(PROMISE.valueOf(tokens.nextToken())) {
		case CHAT:			
			if(chatComeInEvent != null)
				chatComeInEvent.dispatch(this, data);			
			break;
		case GAME:
			if(gameComeInEvent != null)
				gameComeInEvent.dispatch(this, data);			
			break;
		default:			
			if(otherComeInEvent != null) 
				otherComeInEvent.dispatch(this, data);			
			break;				
		}
	}
}
