package me.byungjin.minigame.gui.item;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public abstract class MenuItem extends Item {
	protected ImageIcon defaultIcon, selectedIcon;	

	public MenuItem(JPanel panel, int i) {		
		super(i);		
		dest = panel;
	}	
	public void setIcons(ImageIcon de, ImageIcon sel) {
		defaultIcon = de;
		selectedIcon = sel;
		setIcon(defaultIcon);
	}	
					
	
	@Override
	public void hover() {
		setIcon(selectedIcon);
	}
	@Override
	public void unhover() {		
		setIcon(defaultIcon);		
	}		
	@Override
	public void afterSelected() {
		
	}
	@Override
	public void unselect() {
		unhover();				
	}
}
