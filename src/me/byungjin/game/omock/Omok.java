package me.byungjin.game.omock;

import java.util.StringTokenizer;

import me.byungjin.game.Game;
import me.byungjin.game.Point;
import me.byungjin.network.Agent;
import me.byungjin.network.Client;
import me.byungjin.network.PROMISE;

public class Omok extends Game {	
	private StoneType mine;
	private boolean turn;
	private BadukBoard board;	
	private Point point;
	private StoneSetCommand predictCommand;
	private StoneSetCommand putCommand;
			
	public Omok(Agent agent) {		
		super(agent);
		board = new BadukBoard();
		point = new Point();
		start();
	}
	/**
	 * ���� ��ǥ���� Ȯ��.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isSame(int x, int y) {
		if(point.isSame(x, y))	return true;
				
		return false;
	}
	/**
	 * ���� ������ Ȯ���ϰ� ��ġ ������
	 * @param x
	 * @param y
	 */
	public boolean isNull(int x, int y) {
		if(board.isEmpty(x, y)) {
			point.setPoint(x, y);
			return true;
		}
		return false;
	}
	/**
	 * ���� ���� ������ ������.
	 */
	public void putWith() {
		board.put(point.getX(), point.getY(), mine);
		turn = false;
		send(PROMISE.PUT, point.getX() + " " + point.getY());		
	}	
	/**
	 * ���� ������ �������� �������� ����.		
	 * @param x
	 * @param y
	 * @param type
	 */
	public void putAlone() {		
		board.put(point.getX(), point.getY(), StoneType.reverse(mine));
	}
	/**
	 * �ٵϵ� ���� ��ġ ����
	 * @param x
	 * @param y
	 */
	public void predict() {
		send(PROMISE.PREDICT, point.getX() + " " + point.getY());
	}
	/**
	 * ���� �� Ȯ��
	 * @return
	 */
	public boolean isTurn() {
		return turn;
	}
	/**
	 * �ٵϵ��� ���� ���� ������ Ȯ�εǴ� ��� �� �ൿ
	 * @param cmd
	 */
	public void addPredictCommand(StoneSetCommand cmd) {
		this.predictCommand = cmd;
	}
	/**
	 * �ٵϵ��� ���� ������ Ȯ�εǴ� ��� �� �ൿ
	 * @param cmd
	 */
	public void addPutCommand(StoneSetCommand cmd) {
		this.putCommand = cmd;
	}

	@Override
	public void command(Object source, String request) {		
		StringTokenizer tokens = new StringTokenizer(request);
		tokens.nextToken();
		switch(PROMISE.valueOf(tokens.nextToken())) {
		case READY:			
			send(PROMISE.READY_TOO, "");
			mine = StoneType.WHITE;
			turn = true;
			running = true;
			break;
		case READY_TOO:			
			mine = StoneType.BLACK;
			turn = false;
			running = true;			
			break;
		case PREDICT:
			if(predictCommand != null)
				predictCommand.execute(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()));
			break;
		case PUT:
			if(putCommand != null)
				putCommand.execute(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()));
			turn = true;
			break;
		default:
			break;
		}
		
	}		
	/**
	 * �ش� ������ �������� �ٵϾ��� ������ Ȯ����
	 * @param dx
	 * @param dy
	 * @param type
	 * @return
	 */
	public int countStonesWithDirection(int dx, int dy, StoneType type) {		
		int i = 1, curX, curY;
		while(true) {
			curX = point.getX() + dx * i;
			curY = point.getY() + dy * i;
			
			if(curX < 0 || curX >= board.WIDTH || curY >= board.HEIGHT || curY < 0)
				break;
			if(!(board.getType(curX, curY) == type))
				break;
			
			i++;
		}				
		return i - 1;			
	}	
	@Override
	public int isWinOrLose(Object team) {	
		StoneType type = (StoneType)team;
		
		if(countStonesWithDirection(1, 1, type) + countStonesWithDirection(-1, -1, type) == 4
				|| countStonesWithDirection(-1, 1, type) + countStonesWithDirection(1, -1, type) == 4
				|| countStonesWithDirection(0, 1, type) + countStonesWithDirection(0, -1, type) == 4
				|| countStonesWithDirection(1, 0, type) + countStonesWithDirection(-1, 0, type) == 4) {			
			running = false;
			return mine == type ? 1 : 0;
		}						
		return -1;
	}	
	@Override
	public void init() {
		if(agent instanceof Client)
			send(PROMISE.READY, "");
	}
}
