package me.byungjin.game;

import me.byungjin.network.Agent;
import me.byungjin.network.PROMISE;
import me.byungjin.network.event.DataComeInEvent;

public abstract class Game {
	protected boolean running;
	protected Agent agent;	
	
	/**
	 * ���ӿ� ���� Network �Է�
	 * @param agent
	 */
	public Game(Agent agent) {
		this.agent = agent;
		running = false;
	}	
	/**
	 * �ǽð� ��ȣ�ۿ�
	 * @param request
	 */
	public abstract void command(Object source, String request);
	/**
	 * ���� �¸� Ȯ��(-1 : default, 0 : lose, 1 : win)
	 */
	public abstract int isWinOrLose(Object team);	
	/**
	 * ���� ������ Ȯ��
	 * @return
	 */
	public boolean isRunning() {
		return running;
	}
	/**
	 * ���� ����
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
	 * ���� �ʱ� ȯ�� ����
	 */
	public abstract void init();
	/**
	 * ������ ����
	 * @param type
	 * @param str
	 */
	public void send(PROMISE type, String str) {
		if(agent != null)
			agent.send(PROMISE.GAME, type + " " + str);
	}
}
