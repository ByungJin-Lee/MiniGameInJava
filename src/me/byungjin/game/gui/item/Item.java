package me.byungjin.game.gui.item;

import java.awt.Cursor;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import resource.ResourceLoader;

public abstract class Item extends JButton {
	/**
	 * Item �ĺ���
	 */
	private int identify = 0;
	/**
	 * ���� Item ���
	 */	
	private Item[] children = null;
	/**
	 * Item�� ���õǾ����� �ۿ��� Panel
	 */
	protected JPanel dest = null;
	
	/**
	 * Item �ʱ�ȭ
	 * @param i
	 */
	public Item() {
		setFont(ResourceLoader.DEFAULT_FONT);
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
	 * Item ����
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
	 * Item ��
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
	 * Item�� ���õ� �� ����
	 */
	public abstract void afterSelected();
	public abstract void unselect();
}
