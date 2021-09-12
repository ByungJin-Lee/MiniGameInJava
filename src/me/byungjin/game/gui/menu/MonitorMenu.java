package me.byungjin.game.gui.menu;

import javax.swing.JPanel;

import me.byungjin.game.gui.banner.ClientBanner;
import me.byungjin.game.gui.banner.ServerStatusBanner;
import me.byungjin.game.gui.item.Item;
import me.byungjin.game.gui.item.MenuItem;
import me.byungjin.game.gui.listener.ItemMouseListener;
import me.byungjin.manager.AssetManager;

public class MonitorMenu extends MenuItem {
	public MonitorMenu(JPanel banner, JPanel inner) {
		super(banner, 0);
		setIcons(AssetManager.ICON_MONITER, AssetManager.ICON_MONITER_SELECTED);		
		registerChildrenWithParam(new Item[] {new ServerStatusBanner(), new ClientBanner()}, inner, new ItemMouseListener());
	}	
}
