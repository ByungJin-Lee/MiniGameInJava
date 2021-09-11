package me.byungjin.minigame.gui.item.menu;

import javax.swing.JPanel;

import me.byungjin.manager.AssetManager;
import me.byungjin.minigame.gui.item.MenuItem;

public class SettingMenu extends MenuItem{
	public SettingMenu(JPanel banner, JPanel inner) {
		super(banner, 2);		
		setIcons(AssetManager.ICON_SETTING, AssetManager.ICON_SETTING_SELECTED);
	}
}
