package me.byungjin.game.omock.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import me.byungjin.game.gui.MiniDialog;
import me.byungjin.game.omock.BadukBoard;
import me.byungjin.game.omock.Omok;
import me.byungjin.game.omock.StoneSetCommand;
import me.byungjin.game.omock.StoneType;
import me.byungjin.network.Agent;

public class OmokMouseListener extends MouseAdapter {
	private int x, y;
	private StoneType type = StoneType.WHITE;
	private StoneLabel stone;
	private Omok omok;
	private JPanel panel;
	
	public OmokMouseListener(OmokPanel panel, Agent agent) {
		omok = new Omok(agent);
		stone = new StoneLabel(type);
		this.panel = panel;
		panel.add(stone);
		omok.addPredictCommand(new StoneSetCommand() {		
			@Override
			public void execute(int x, int y) {
				stone.set(x, y);
			}
		});			
		omok.addPutCommand(new StoneSetCommand() {
			@Override
			public void execute(int x, int y) {
				omok.putAlone(x, y, type);
				put(x, y);
			}			
		});
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(!omok.isRunning() || !omok.isTurn()) return;
		
		x = (e.getX() - 7) / BadukBoard.STONE_SIZE;
		y = (e.getY() - 7) / BadukBoard.STONE_SIZE;
				
		if( x < 0 || x > 18 || y < 0 || y > 18) return;
		
		stone.set(x, y);		
		omok.predict(x, y);
	}
	@Override
	public void mouseClicked(MouseEvent e) {				
		if(!omok.isRunning() || !omok.isTurn()) return;			
		
		if(omok.put(x, y, type))
			put(x, y);
	}
	
	public void put(int x, int y) {		
		if(omok.isWinOrLose(type) > -1)
			new MiniDialog(new JPanel(), "Omok");
		stone.put(x, y);
		stone = new StoneLabel(type = StoneType.reverse(type));
		panel.add(stone);		
	}
}
