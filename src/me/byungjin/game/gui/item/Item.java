package me.byungjin.game.gui.item;

import java.awt.Cursor;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import me.byungjin.manager.AssetManager;

public abstract class Item extends JButton {
	/**
	 * Item 식별자
	 */
	private int identify = 0;
	/**
	 * 하위 Item 목록
	 */	
	private Item[] children = null;
	/**
	 * Item이 선택되었을때 작용할 Panel
	 */
	protected JPanel dest = null;
	
	/**
	 * Item 초기화
	 * @param i
	 */
	public Item() {
		setFont(AssetManager.DEFAULT_FONT);
		setFocusPainted(false);		
		setContentAreaFilled(false);	
		setBorderPainted(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));	
	}
	public Item(int i) {		
		this();
		identify = i;
	}	
	
	public Item(Item[] children) {
		this();		
		registerChildren(children);
	}	
		
	public Item(String str) {
		this();
		setText(str);		
	}
	public void registerChildren(Item[] items) {
		if(items != null)
			children = items;
	}
	public void registerChildrenWithParam(Item[] items, JPanel childrenDest, MouseListener listener) {
		children = items;
		int i = 0;
		for(Item c : items) {
			c.registerDest(childrenDest);
			c.addMouseListener(listener);
			c.setIdentify(i++);
		}
	}
	public void setIdentify(int identify) {
		this.identify = identify;
	}
	public void registerDest(JPanel destination) {
		if(destination != null)
			dest = destination;
	}		
	/**
	 * Item 선택
	 */
	public void select() {
		if(dest == null) return;	
		dest.removeAll();
		
		if(children != null) {
			for(Item i : children) {
				dest.add(i);
			}				
		}					
		afterSelected();		
		dest.revalidate();
		dest.repaint();		
	}
	/**
	 * Item 비교
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Item) {
			if(((Item)obj).identify == identify)
				return true;
		}
		return false;
	}	
	/**
	 * Hover Effect
	 */
	public abstract void hover();
	/**
	 * unHover Effect
	 */
	public abstract void unhover();
	/**
	 * Item이 선택된 후 동작
	 */
	public abstract void afterSelected();
	public abstract void unselect();
}
