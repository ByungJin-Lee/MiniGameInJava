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
import resource.ResourceLoader;

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
				set(x, y);
			}
		});			
		omok.addPutCommand(new StoneSetCommand() {
			@Override
			public void execute(int x, int y) {
				set(x, y);
				omok.putAlone();
				put();				
			}			
		});
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(!omok.isRunning() || !omok.isTurn()) return;
		
		x = (e.getX() - 7) / BadukBoard.STONE_SIZE;
		y = (e.getY() - 7) / BadukBoard.STONE_SIZE;
				
		if( x < 0 || x > 18 || y < 0 || y > 18) return;
		
		System.out.println("pos " + x + " " + y);
				
		set(x, y);
		omok.predict();
	}
	@Override
	public void mouseClicked(MouseEvent e) {				
		if(!omok.isRunning() || !omok.isTurn()) return;			
		
		if(omok.isEmpty(x, y)) {
			set(x,y);
			omok.putWith();
			put();
		}		
	}
	
	public void put() {
		stone.put();
		type = StoneType.reverse(type);
		stone = new StoneLabel(type);
		panel.add(stone);
		ResourceLoader.playWav("game/stone_put_wav.WAV");
		if(omok.isWinOrLose(type) > -1)
			new MiniDialog(new JPanel(), "Omok");				
	}
	
	public void set(int x, int y) {		
		stone.set(x, y);
		omok.setPoint(x, y);	
	}
}
