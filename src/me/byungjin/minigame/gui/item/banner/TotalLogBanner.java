package me.byungjin.minigame.gui.item.banner;

import me.byungjin.minigame.gui.item.BannerItem;
import me.byungjin.minigame.gui.page.TotalLogPage;

public class TotalLogBanner extends BannerItem {
	public TotalLogBanner() {
		super("Total Log");				
	}

	@Override
	public void unselect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterSelected() {
		super.afterSelected();
		dest.add(new TotalLogPage());
	}
}
