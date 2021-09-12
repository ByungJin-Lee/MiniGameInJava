package me.byungjin.game.gui.banner;

import javax.swing.JButton;

import me.byungjin.game.gui.item.BannerItem;

public class ClientBanner extends BannerItem {

	public ClientBanner() {
		super("Client Info");				
	}

	@Override
	public void unselect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterSelected() {
		super.afterSelected();
		// TODO Auto-generated method stub
		dest.add(new JButton("Text"));
		
	}

}
