package me.byungjin.game.omock.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import me.byungjin.game.gui.MiniDialog;
import me.byungjin.game.omock.BadukBoard;
import me.byungjin.game.omock.StoneType;

public class BoardMouseListener extends MouseAdapter {
	private int x, y;
	private StoneType type = StoneType.WHITE;
	private StoneLabel stone;
	private OmokPanel source;
	
	public BoardMouseListener(OmokPanel panel) {
		source = panel;
		stone = new StoneLabel(type);		
		source.add(stone);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(!source.isRunning()) return;
		
		x = (e.getX() - 7) / BadukBoard.STONE_SIZE;
		y = (e.getY() - 7) / BadukBoard.STONE_SIZE;
				
		if( x < 0 || x > 18 || y < 0 || y > 18) return;
		
		stone.set(x, y);		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!source.isRunning()) return;
		
		if(source.putStone(x, y, type)) {
			if(source.checkWin(x, y)) {				
				new MiniDialog(new JPanel(), "Omok");
			}		
			stone.put(x, y);
			type = type == StoneType.WHITE ? StoneType.BLACK : StoneType.WHITE;
			stone = new StoneLabel(type);
			source.add(stone);						
		}							
	}
}
