package me.byungjin.game.gui;

import me.byungjin.game.gui.panel.ConnectErrorPanel;

public class ConnectErrorDialog extends MiniDialog {
	public ConnectErrorDialog() {
		super(new ConnectErrorPanel(), "Error", null);
	}
}
