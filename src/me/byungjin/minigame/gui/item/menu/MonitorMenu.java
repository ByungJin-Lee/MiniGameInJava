package me.byungjin.minigame.gui.item.menu;

import javax.swing.JPanel;

import me.byungjin.manager.AssetManager;
import me.byungjin.minigame.gui.actionlistener.ItemMouseListener;
import me.byungjin.minigame.gui.item.Item;
import me.byungjin.minigame.gui.item.MenuItem;
import me.byungjin.minigame.gui.item.banner.ClientBanner;
import me.byungjin.minigame.gui.item.banner.ServerStatusBanner;

public class MonitorMenu extends MenuItem {
	public MonitorMenu(JPanel banner, JPanel inner) {
		super(banner, 0);
		setIcons(AssetManager.ICON_MONITER, AssetManager.ICON_MONITER_SELECTED);		
		registerChildrenWithParam(new Item[] {new ServerStatusBanner(), new ClientBanner()}, inner, new ItemMouseListener());
	}	
}
