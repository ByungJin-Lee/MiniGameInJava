package me.byungjin.game.omock;

public class Omok extends BadukBoard {
	private boolean running = false;
	public Omok() {		
		super();
		running = true;
	}
	
	public boolean checkWin(int x, int y) {
		StoneType placedType = stones[y][x].getType();
		
		if(countStonesWithDirection(x, y, 1, 1, placedType) + countStonesWithDirection(x, y, -1, -1, placedType) == 4
				|| countStonesWithDirection(x, y, -1, 1, placedType) + countStonesWithDirection(x, y, 1, -1, placedType) == 4
				|| countStonesWithDirection(x, y, 0, 1, placedType) + countStonesWithDirection(x, y, 0, -1, placedType) == 4
				|| countStonesWithDirection(x, y, 1, 0, placedType) + countStonesWithDirection(x, y, -1, 0, placedType) == 4) {
			running = false;
			return true;
		}					
		return false;
	}
	
	public int countStonesWithDirection(int x, int y, int dx, int dy, StoneType type) {		
		int i = 1, curX, curY;		
		while(true) {
			curX = x + dx * i;
			curY = y + dy * i;
			
			if(curX < 0 || curX >= WIDTH || curY >= HEIGHT || curY < 0)
				break;
			if(!stones[curY][curX].equals(type))				
				break;
			i++;
		}
		System.out.println(i - 1);
		return i - 1;			
	}
	public boolean isRunning() {
		return running;
	}
}
