package me.byungjin.game.bullandcow;

import java.util.StringTokenizer;

import me.byungjin.game.Game;
import me.byungjin.game.bullandcow.Numbers.NumberResult;
import me.byungjin.network.Agent;
import me.byungjin.network.PROMISE;

public class BullsAndCows extends Game{
	private Numbers mineNumbers = null;
	private NumberGetCommand numberGetCommand;
	private boolean turn;
	
	public BullsAndCows(Agent agent) {
		super(agent);		
		start();
	}
	
	/**
	 * 내 숫자를 설정한 뒤, 준비 완료 신호를 보냄
	 * @param number
	 * @return
	 */
	public boolean setMyNumber(String number) {
		if(Numbers.isVaild(number)) {
			mineNumbers = new Numbers(number);	
			send(PROMISE.READY, "");
			return true;
		}
		return false;
	}
	/**
	 * 숫자를 던짐
	 * @param number
	 * @return
	 */
	public boolean throwNumber(String number) {
		if(Numbers.isVaild(number)) {
			send(PROMISE.THROW, number);
			return true;
		}
		return false;
	}
	public NumberResult returnThrowResult(String number) {
		return mineNumbers.judgeNumbers(number);
	}
	/**
	 * 숫자 횟득 명령어 생성
	 * @param e
	 */
	public void addNumberGetCommand(NumberGetCommand e) {
		numberGetCommand = e;
	}
	
	
	@Override
	public void command(Object source, String request) {
		StringTokenizer tokens = new StringTokenizer(request);
		tokens.nextToken();
		switch(PROMISE.valueOf(tokens.nextToken())) {
		case READY:
			turn = false;
			running = true;
			send(PROMISE.READY_TOO, "");
			break;
		case READY_TOO:
			turn = true;
			running = true;
			break;
		case THROW:
			String number = tokens.nextToken();
			if(numberGetCommand != null)
				numberGetCommand.execute(number);
			send(PROMISE.THROW_RESULT, returnThrowResult(number).toString());
			break;
		}
	}

	@Override
	public int isWinOrLose(Object team) {
		
		return 0;
	}

	@Override
	public void init() {
		
	}
}
