package me.byungjin.game;

import me.byungjin.network.Agent;
import me.byungjin.network.PROMISE;
import me.byungjin.network.event.DataComeInEvent;

public abstract class Game {
	protected boolean running;
	protected Agent agent;	
	
	/**
	 * 게임에 사용될 Network 입력
	 * @param agent
	 */
	public Game(Agent agent) {
		this.agent = agent;
		running = false;
	}	
	/**
	 * 실시간 상호작용
	 * @param request
	 */
	public abstract void command(Object source, String request);
	/**
	 * 게임 승리 확인(-1 : default, 0 : lose, 1 : win)
	 */
	public abstract int isWinOrLose(Object team);	
	/**
	 * 게임 진행중 확인
	 * @return
	 */
	public boolean isRunning() {
		return running;
	}
	/**
	 * 게임 시작
	 */
	public void start() {
		if(agent == null || !agent.isRunning()) return;
		
		agent.addGameComeInEvent(new DataComeInEvent() {
			@Override
			public void dispatch(Object source, String data) {
				command(source, data);
			}
		});
		
		init();		
	}
	/**
	 * 게임 초기 환경 설정
	 */
	public abstract void init();
	/**
	 * 데이터 전송
	 * @param type
	 * @param str
	 */
	public void send(PROMISE type, String str) {
		if(agent != null)
			agent.send(PROMISE.GAME, type + " " + str);
	}
}
