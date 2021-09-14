package me.byungjin.game.omock.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import me.byungjin.game.gui.MiniDialog;
import me.byungjin.game.omock.BadukBoard;
import me.byungjin.game.omock.Omok;
import me.byungjin.game.omock.StoneType;

public class OmokMouseListener extends MouseAdapter {
	private int x, y;
	private StoneType type = StoneType.WHITE;
	private StoneLabel stone;
	private Omok omok;
	
	public OmokMouseListener(OmokPanel panel) {
		omok = new Omok(null);
		stone = new StoneLabel(type);		
		panel.add(stone);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(!omok.isRunning()) return;
		
		x = (e.getX() - 7) / BadukBoard.STONE_SIZE;
		y = (e.getY() - 7) / BadukBoard.STONE_SIZE;
				
		if( x < 0 || x > 18 || y < 0 || y > 18) return;
		
		stone.set(x, y);		
	}
	@Override
	public void mouseClicked(MouseEvent e) {				
		if(!omok.isRunning()) return;
		
		if(omok.put(x, y, type)) {
			if(omok.isWin(type))
				new MiniDialog(new JPanel(), "Omok");
			stone.put(x, y);
			stone = new StoneLabel(type = type.reverse(type));
			((JPanel)e.getSource()).add(stone);
		}							
	}
}
