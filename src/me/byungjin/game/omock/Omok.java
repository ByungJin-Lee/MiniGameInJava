package me.byungjin.game.omock;

import java.util.StringTokenizer;

import me.byungjin.game.Game;
import me.byungjin.game.GameEndEvent;
import me.byungjin.game.GameKind;
import me.byungjin.game.Point;
import me.byungjin.game.gui.PopUpDialog;
import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.manager.SystemManager;
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
	private GameEndEvent endEvent;
			
	public Omok(Agent agent) {		
		super(agent);
		board = new BadukBoard();
		point = new Point();
		start();
	}
	/**
	 * 같은 좌표인지 확인.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isSame(int x, int y) {
		if(point.isSame(x, y))	
			return true;
				
		return false;
	}
	public void setPoint(int x, int y) {
		point.setPoint(x, y);
	}
	/**
	 * 현재 지점을 확인하고 위치 설정함
	 * @param x
	 * @param y
	 */
	public boolean isEmpty(int x, int y) {
		if(board.isEmpty(x, y))			
			return true;
		return false;
	}
	/**
	 * 돌을 놓고 서버에 전달함.
	 */
	public void putWith() {
		board.put(point.getX(), point.getY(), mine);
		send(PROMISE.PUT, point.getX() + " " + point.getY());
		turn = false;
		if(isWinOrLose(mine) != -1)
			return;		
		if(agent == null || !agent.isRunning())
			endEvent.dispatch(GameKind.OMOK, true);		
	}	
	/**
	 * 돌을 놓지만 서버에는 전달하지 않음.		
	 * @param x
	 * @param y
	 * @param type
	 */
	public void putAlone() {		
		board.put(point.getX(), point.getY(), StoneType.reverse(mine));
		isWinOrLose(StoneType.reverse(mine));
	}
	/**
	 * 바둑돌 예상 위치 전송
	 * @param x
	 * @param y
	 */
	public void predict() {
		send(PROMISE.PREDICT, point.getX() + " " + point.getY());
	}
	/**
	 * 현재 턴 확인
	 * @return
	 */
	public boolean isTurn() {
		return turn;
	}
	/**
	 * 바둑돌을 놓을 예상 지점이 확인되는 경우 할 행동
	 * @param cmd
	 */
	public void addPredictCommand(StoneSetCommand cmd) {
		this.predictCommand = cmd;
	}
	/**
	 * 바둑돌을 놓은 지점이 확인되는 경우 할 행동
	 * @param cmd
	 */
	public void addPutCommand(StoneSetCommand cmd) {
		this.putCommand = cmd;
	}
	public void setGameEndEvent(GameEndEvent e) {
		this.endEvent = e;
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
	 * 해당 기점을 기준으로 바둑알의 갯수를 확인함
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
			
			if(mine == type) {
				endEvent.dispatch(GameKind.OMOK, true);				
			}else {
				endEvent.dispatch(GameKind.OMOK, false);				
			}
			
			return mine == type ? 1 : 0;
		}						
		return -1;
	}	
	@Override
	public void init() {
		Thread wa = new Thread(new Runnable() {
			@Override
			public void run() {
				while(!running) {
					try {
						Thread.sleep(100);
						if(agent instanceof Client)
							send(PROMISE.READY, "");
					}catch(Exception e){
						SystemManager.catchException(ENVIRONMENT.GUI, e);
					}
				}
			}
		});
		wa.start();
	}
}
