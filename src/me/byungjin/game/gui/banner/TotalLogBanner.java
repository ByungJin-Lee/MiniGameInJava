package me.byungjin.game.gui.banner;

import me.byungjin.game.gui.item.BannerItem;
import me.byungjin.game.gui.page.TotalLogPage;

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
