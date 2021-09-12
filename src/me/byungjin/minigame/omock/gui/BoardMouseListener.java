package me.byungjin.minigame.omock.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import me.byungjin.minigame.omock.BadukBoard;
import me.byungjin.minigame.omock.StoneType;

public class BoardMouseListener extends MouseAdapter {
	private int x, y;
	private boolean turn = true;
	private StoneLabel stone;
	private OmokPanel source;
	
	public BoardMouseListener(OmokPanel panel) {
		source = panel;
		stone = new StoneLabel(StoneType.WHITE);
		stone.set(9, 9);
		source.add(stone);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		x = (e.getX() - 7) / BadukBoard.STONE_SIZE;
		y = (e.getY() - 7) / BadukBoard.STONE_SIZE;
		
		stone.set(x, y);
		source.revalidate();
		source.repaint();
		
		System.out.println("x " + x + " y " + y);
	}
	@Override
	public void mouseClicked(MouseEvent e) {		
		stone = new StoneLabel(turn ? StoneType.BLACK : StoneType.WHITE);
		stone.set(9, 9);
		source.add(stone);		
		turn = !turn;
	}
}
