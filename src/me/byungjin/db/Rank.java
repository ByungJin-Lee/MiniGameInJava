package me.byungjin.db;

import me.byungjin.game.GameKind;

public class Rank {
	private boolean first;
	private int Victory, Lose;
	private String id;
	private GameKind kind;
	public Rank(String id, GameKind kind, int Victory, int Lose) {
		this.Victory = Victory;
		this.id = id;
		this.kind = kind;
		this.Lose = Lose;
		first = false;
	}
	
	public Rank(String id, GameKind kind) {
		this(id, kind, 0, 0);
		first = true;
	}
	public boolean isFirst() {
		return this.first;
	}
	public GameKind getGameKind() {
		return this.kind;
	}
	public String getId() {
		return this.id;
	}	
	public int getVictory() {
		return this.Victory;
	}
	public int getLose() {
		return this.Lose;
	}
	public void win() {
		this.Victory++;
	}
	public void lose() {
		this.Lose++;
	}
	@Override
	public String toString() {	
		return id + " " + kind + " " + Victory + " " + Lose;
	}
}
