package me.byungjin.game;

import me.byungjin.network.Agent;

public abstract class Game {
	protected boolean running;
	protected Agent agent;
	
	/**
	 * 게임에 사용될 Network 입력
	 * @param agent
	 */
	public Game(Agent agent) {
		running = false;
		this.agent = agent;
	}	
	/**
	 * 실시간 상호작용
	 * @param request
	 */
	public abstract void command(String request);
	/**
	 * 게임 승리 확인
	 */
	public abstract boolean isWin(Object team);
	/**
	 * 게임 패배 확인
	 */
	public abstract boolean isLose(Object team);
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
	public abstract void start();
}
