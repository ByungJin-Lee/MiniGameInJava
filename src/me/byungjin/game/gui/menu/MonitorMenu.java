package me.byungjin.game.gui.menu;

import javax.swing.JPanel;

import me.byungjin.game.gui.banner.ServerStatusBanner;
import me.byungjin.game.gui.banner.TotalLogBanner;
import me.byungjin.game.gui.item.Item;
import me.byungjin.game.gui.item.MenuItem;
import me.byungjin.game.gui.listener.ItemMouseListener;
import resource.ResourceLoader;

public class MonitorMenu extends MenuItem {
	public MonitorMenu(JPanel banner, JPanel inner) {
		super(banner, 0);
		setIcons(ResourceLoader.ICON_MONITER, ResourceLoader.ICON_MONITER_SELECTED);		
		registerChildrenWithParam(new Item[] {new ServerStatusBanner(), new TotalLogBanner()}, inner, new ItemMouseListener());
	}	
}
