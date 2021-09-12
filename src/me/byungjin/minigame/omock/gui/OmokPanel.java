package me.byungjin.minigame.omock.gui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JPanel;

import me.byungjin.manager.AssetManager;
import me.byungjin.minigame.omock.Omok;

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
