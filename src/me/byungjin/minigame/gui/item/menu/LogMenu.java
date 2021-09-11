package me.byungjin.minigame.gui.item.menu;

import javax.swing.JPanel;

import me.byungjin.manager.AssetManager;
import me.byungjin.minigame.gui.actionlistener.ItemMouseListener;
import me.byungjin.minigame.gui.item.Item;
import me.byungjin.minigame.gui.item.MenuItem;
import me.byungjin.minigame.gui.item.banner.ConnectionLogBanner;
import me.byungjin.minigame.gui.item.banner.DBLogBanner;
import me.byungjin.minigame.gui.item.banner.ServerLogBanner;
import me.byungjin.minigame.gui.item.banner.TotalLogBanner;

public class LogMenu extends MenuItem{
	public LogMenu(JPanel banner, JPanel inner) {
		super(banner, 1);		
		setIcons(AssetManager.ICON_LOG, AssetManager.ICON_LOG_SELECTED);				
		registerChildrenWithParam(new Item[] {new TotalLogBanner(), new ServerLogBanner(),
				new DBLogBanner(), new ConnectionLogBanner()}, inner, new ItemMouseListener());
	}
	
	
}
