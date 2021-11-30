package me.byungjin.game.gui.item;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import me.byungjin.game.Room;
import resource.ResourceLoader;

public class RoomItem extends JPanel {
	private int index = 0;
	private static Cursor hand = new Cursor(Cursor.HAND_CURSOR);
	
	public RoomItem(Room r, int i, MouseListener l) {
		this.index = i;
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), BorderFactory.createEmptyBorder(8, 8, 8, 8)));
		setBackground(Color.white);
		setCursor(hand);		
				
		setLayout(new GridLayout(2, 1));
		
		JLabel title = new JLabel("[" + r.getGameKind().switchString() + "] " + r.getRoomName());
		title.setFont(ResourceLoader.DEFAULT_FONT);
		JLabel info = new JLabel(r.getIP());
		info.setFont(ResourceLoader.DEFAULT_FONT12);		
		
		add(title);
		add(info);		
		
		addMouseListener(l);
		
		setVisible(true);
		setSize(275, 70);
	}	
	
	public int getIndex() {
		return index;
	}
}
