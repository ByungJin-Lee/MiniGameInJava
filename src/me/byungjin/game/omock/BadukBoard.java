package me.byungjin.game.omock;

public class BadukBoard {
	public static final int STONE_SIZE = 37; 
	protected final int WIDTH = 19;
	protected final int HEIGHT = 19;
	protected Stone[][] stones;
	
	public BadukBoard() {
		makeBoard();
	}
	/**
	 * type�� ���� �ش� ��ġ�� ���´�.
	 * @param x
	 * @param y
	 * @param type
	 * @return
	 */
	public boolean setStone(int x, int y, StoneType type) {
		if(!isStone(x, y)) return false;
		stones[y][x].setStone(type);		
		return true;
	}
	/**
	 * �ش� ��ġ�� Stone�� �ִ��� Ȯ��
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isStone(int x, int y) {		
		if(stones[y][x].equals(StoneType.NONE))
			return true;
		return false;
	}
	public void makeBoard() {
		stones = new Stone[HEIGHT][WIDTH];		
		for(int i = 0; i < HEIGHT; i++)
			for(int j = 0; j < WIDTH; j++)
				stones[i][j] = new Stone();
	}
}
