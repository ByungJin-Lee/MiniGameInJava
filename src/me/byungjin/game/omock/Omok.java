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
	public boolean put(int x, int y, StoneType type) {
		point.setPoint(x, y);
		if(board.put(x, y, type)) {
			turn = false;
			send(PROMISE.PUT, x + " " + y);
			return true;
		}
		return false;
	}
	public void putAlone(int x, int y, StoneType type) {
		point.setPoint(x, y);
		board.put(x, y, type);
	}
	public void predict(int x, int y) {
		send(PROMISE.PREDICT, x + " " + y);
	}
	public boolean isTurn() {
		return turn;
	}	
	public void addPredictCommand(StoneSetCommand cmd) {
		this.predictCommand = cmd;
	}
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
