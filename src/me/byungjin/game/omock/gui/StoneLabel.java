package me.byungjin.game.omock.gui;

import java.awt.Dimension;

import javax.swing.JLabel;

import me.byungjin.game.omock.BadukBoard;
import me.byungjin.game.omock.StoneType;
import me.byungjin.manager.AssetManager;

public class StoneLabel extends JLabel {
	public static Dimension size = new Dimension(35, 35);
	private static int xl = size.width / 2 - 9;
	private static int yl = size.height / 2 - 11;
	private StoneType type;
	public StoneLabel(StoneType type) {
		this.type = type;
		setIcon(type == StoneType.WHITE ? AssetManager.ICON_WHITE_STONE_OP : AssetManager.ICON_BLACK_STONE_OP);			
		setSize(size);
	}
	
	public void set(int x, int y) {
		setLocation(x * BadukBoard.STONE_SIZE + xl, y * BadukBoard.STONE_SIZE + yl);		
	}
	public void put(int x, int y) {
		set(x, y);
		setIcon(type == StoneType.WHITE ? AssetManager.ICON_WHITE_STONE : AssetManager.ICON_BLACK_STONE);	
	}
}
