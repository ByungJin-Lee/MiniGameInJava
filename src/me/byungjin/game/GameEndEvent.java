package me.byungjin.game;

public interface GameEndEvent {
	public void dispatch(GameKind kind, boolean win);
}
