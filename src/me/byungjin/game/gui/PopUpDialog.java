package me.byungjin.game.gui;

import me.byungjin.game.gui.panel.PopUpPanel;

public class PopUpDialog extends MiniDialog {
	public PopUpDialog(String txt) {
		super(new PopUpPanel(txt), "¾Ë¸²", null);
	}

}
