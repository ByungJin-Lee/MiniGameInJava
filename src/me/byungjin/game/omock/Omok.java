package me.byungjin.game.omock;

import me.byungjin.game.Game;
import me.byungjin.game.Point;
import me.byungjin.game.gui.ConnectWindow;
import me.byungjin.network.Agent;

public class Omok extends Game {	
	private BadukBoard board;	
	private Point point;
	
	public Omok(Agent agent) {		
		super(agent);
		board = new BadukBoard();
		point = new Point();		
		this.agent = agent;
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
		return board.put(x, y, type);
	}
	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public void command(String request) {
		
	}

	@Override
	public boolean isWin(Object team) {	
		StoneType type = (StoneType)team;
		
		if(countStonesWithDirection(1, 1, type) + countStonesWithDirection(-1, -1, type) == 4
				|| countStonesWithDirection(-1, 1, type) + countStonesWithDirection(1, -1, type) == 4
				|| countStonesWithDirection(0, 1, type) + countStonesWithDirection(0, -1, type) == 4
				|| countStonesWithDirection(1, 0, type) + countStonesWithDirection(-1, 0, type) == 4) {			
			running = false;
			return true;
		}						
		return false;
	}

	@Override
	public boolean isLose(Object team) {
		running = false;
		return false;
	}
	@Override
	public void start() {
		new ConnectWindow(agent, running);
	}
}
