package me.byungjin.game;

import me.byungjin.network.Agent;

public abstract class Game {
	protected boolean running;
	protected Agent agent;
	
	/**
	 * ���ӿ� ���� Network �Է�
	 * @param agent
	 */
	public Game(Agent agent) {
		running = false;
		this.agent = agent;
	}	
	/**
	 * �ǽð� ��ȣ�ۿ�
	 * @param request
	 */
	public abstract void command(String request);
	/**
	 * ���� �¸� Ȯ��
	 */
	public abstract boolean isWin(Object team);
	/**
	 * ���� �й� Ȯ��
	 */
	public abstract boolean isLose(Object team);
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
	public abstract void start();
}
