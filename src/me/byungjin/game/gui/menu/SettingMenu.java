package me.byungjin.game.gui.menu;

import javax.swing.JPanel;

import me.byungjin.game.gui.item.MenuItem;
import me.byungjin.manager.AssetManager;

public class SettingMenu extends MenuItem{
	public SettingMenu(JPanel banner, JPanel inner) {
		super(banner, 2);		
		setIcons(AssetManager.ICON_SETTING, AssetManager.ICON_SETTING_SELECTED);
	}
}
