package me.byungjin.game.omock;

public enum StoneType {	
	NONE, WHITE, BLACK;
	
	static public StoneType reverse(StoneType type) {
		return type == WHITE ? BLACK : WHITE;
	}
}
