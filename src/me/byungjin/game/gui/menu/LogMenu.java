package me.byungjin.game.gui.menu;

import javax.swing.JPanel;

import me.byungjin.game.gui.banner.ConnectionLogBanner;
import me.byungjin.game.gui.banner.DBLogBanner;
import me.byungjin.game.gui.banner.ServerLogBanner;
import me.byungjin.game.gui.banner.TotalLogBanner;
import me.byungjin.game.gui.item.Item;
import me.byungjin.game.gui.item.MenuItem;
import me.byungjin.game.gui.listener.ItemMouseListener;
import me.byungjin.manager.AssetManager;

public class LogMenu extends MenuItem{
	public LogMenu(JPanel banner, JPanel inner) {
		super(banner, 1);		
		setIcons(AssetManager.ICON_LOG, AssetManager.ICON_LOG_SELECTED);				
		registerChildrenWithParam(new Item[] {new TotalLogBanner(), new ServerLogBanner(),
				new DBLogBanner(), new ConnectionLogBanner()}, inner, new ItemMouseListener());
	}
	
	
}
