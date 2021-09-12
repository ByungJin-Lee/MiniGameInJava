package me.byungjin.game.omock;

public class Stone {			
	public Stone() {
		type = StoneType.NONE;
	}	
	private StoneType type;
		
	public StoneType getType() {
		return type;
	}
	
	public boolean setStone(StoneType stone) {
		if(this.type == StoneType.NONE) {
			this.type = stone;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof StoneType) {
			if((StoneType)obj == type)
				return true;
		}
		return false;
	}
}
