package me.byungjin.minigame.gui.item;

import java.awt.Color;

import me.byungjin.manager.Environment;
import me.byungjin.manager.SystemManager;

public abstract class BannerItem extends Item {
	static private Color defaultColor = new Color(0xf1f8e9);
	static private Color selectedColor = new Color(0xffffff);

	public BannerItem(String text) {
		super(text);
		setOpaque(true);				
		setBackground(defaultColor);		
	}
	@Override
	public void hover() {
		setBackground(selectedColor);
		
	}
	@Override
	public void unhover() {
		setBackground(defaultColor);
	}
	@Override
	public void afterSelected() {		
		SystemManager.message(Environment.M_SERVER, getText() + " was Clicked.");
	}
}
