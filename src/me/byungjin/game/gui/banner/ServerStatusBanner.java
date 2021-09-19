package me.byungjin.game.gui.banner;

import me.byungjin.game.gui.ServerWindow;
import me.byungjin.game.gui.item.BannerItem;
import me.byungjin.game.gui.page.ServerStatusPage;
import me.byungjin.network.Agent;

import javax.swing.*;

public class ServerStatusBanner extends BannerItem {

	public ServerStatusBanner() {
		super("서버 상태");		
	}
		
	@Override
	public void unselect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterSelected() {
		// TODO Auto-generated method stub
		super.afterSelected();

		dest.add(new ServerStatusPage(((ServerWindow)SwingUtilities.getWindowAncestor(this)).getAgent()));
	}
}
