package me.byungjin.game.omock.gui;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import me.byungjin.game.GameEndEvent;
import me.byungjin.network.Agent;
import resource.ResourceLoader;

public class OmokPanel extends JPanel {
	private Image boardImage;			
	public OmokPanel(Agent agent, Agent toServer, GameEndEvent l) {
		super(null);
		setSize(717,717);
		ResourceLoader.readOmokIcons();		
		boardImage = ResourceLoader.readBoard();						
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		OmokMouseListener mouseL = new OmokMouseListener(this, agent, toServer, l);
		addMouseMotionListener(mouseL);
		addMouseListener(mouseL);
	}	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(boardImage, 0, 0, null);
	}	
}
