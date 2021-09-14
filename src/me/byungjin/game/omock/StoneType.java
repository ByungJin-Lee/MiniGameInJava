package me.byungjin.game.omock;

public enum StoneType {	
	NONE, WHITE, BLACK;
	
	public StoneType reverse(StoneType type) {
		return type == WHITE ? BLACK : WHITE;
	}
}
