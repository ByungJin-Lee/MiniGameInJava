package me.byungjin.game.omock.gui;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import me.byungjin.game.omock.Omok;
import me.byungjin.game.omock.StoneType;
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
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		BoardMouseListener mouseL = new BoardMouseListener(this);
		addMouseListener(mouseL);
		addMouseMotionListener(mouseL);
	}
	public Omok getOmok() {
		return omok;
	}
	public boolean isStone(int x, int y) {
		return omok.isStone(x, y);
	}
	public boolean putStone(int x, int y, StoneType type) {
		return omok.setStone(x, y, type);
	}
	public boolean checkWin(int x, int y) {
		return omok.checkWin(x, y);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(boardImage, 0, 0, null);
	}
	public boolean isRunning() {
		return omok.isRunning();
	}

}
