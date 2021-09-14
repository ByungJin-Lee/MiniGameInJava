package me.byungjin.game.gui.menu;

import javax.swing.JPanel;

import me.byungjin.game.gui.item.MenuItem;
import resource.ResourceLoader;

public class SettingMenu extends MenuItem{
	public SettingMenu(JPanel banner, JPanel inner) {
		super(banner, 2);		
		setIcons(ResourceLoader.ICON_SETTING, ResourceLoader.ICON_SETTING_SELECTED);
	}
}
