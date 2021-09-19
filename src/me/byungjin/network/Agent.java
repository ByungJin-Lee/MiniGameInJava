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
	 * 채팅 입력
	 * @param str
	 */
	public abstract void chat(String str);
	/**
	 * 연결 시작
	 */
	public abstract void open();
	/**
	 * 데이터 보내기
	 * @param type
	 * @param str
	 */
	public abstract void send(PROMISE type, String str);
	/**
	 * 태그 없이 데이터 보내기
	 * @param str
	 */
	public void sendRAW(String str) {
		SystemManager.message(ENVIRONMENT.CLIENT, str);
	}
	/**
	 * 연결 종료
	 */
	public abstract void close();

	public void block(){
		close();
		interrupt();
	}

	/**
	 * 연결 확인
	 * @return
	 */
	public boolean isRunning() {		
		return running;
	}
	/**
	 * 채팅 이벤트 등록
	 */	
	public synchronized void addChatComeInEvent(DataComeInEvent event) {
		chatComeInEvent = event;		
	}
	/**
	 * 다른 이벤트 등록
	 */	
	public synchronized void addOtherComeInEvent(DataComeInEvent event) {
		otherComeInEvent = event;		
	}
	/**
	 * 게임 이벤트 등록
	 */	
	public synchronized void addGameComeInEvent(DataComeInEvent event) {
		gameComeInEvent = event;		
	}
	/**
	 * 이벤트 라우터
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
