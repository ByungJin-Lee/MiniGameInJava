package me.byungjin.game;

public enum GameKind {	
	
	OMOK(0), NOT_YET(1), END(2);
	
	private short value = 0;
	GameKind(int i) {
		this.value = (short)i;
	}
	public GameKind next(boolean plus) {
		if(plus) {
			if(this.value + 1 < END.value)
				return GameKind.values()[this.value + 1];
		}
		else {
			if(this.value - 1 >= 0)
				return GameKind.values()[this.value - 1];
		}
		return this;
	}
	
	public int toValue() {
		return this.value;
	}
	
	public String switchString() {
		switch(this) {
		case OMOK: 
			return "오목";
		case NOT_YET:
			return "미구현";
		}
		return null;
	}
}
