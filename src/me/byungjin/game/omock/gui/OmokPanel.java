package me.byungjin.game.omock.gui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JPanel;

import me.byungjin.game.omock.Omok;
import me.byungjin.manager.AssetManager;

public class OmokPanel extends JPanel {
	private Image boardImage;		
	private Omok omok;
	public OmokPanel() {
		super(null);
		setSize(717,717);
		AssetManager.readOmokIcons();
		boardImage = AssetManager.readBoard();				
		omok = new Omok();										
		BoardMouseListener mouseL = new BoardMouseListener(this);
		addMouseListener(mouseL);
		addMouseMotionListener(mouseL);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(boardImage, 0, 0, null);
	}

}
