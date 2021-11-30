package me.byungjin.game.gui;

import me.byungjin.game.gui.listener.MiniDialogWorkListener;
import me.byungjin.game.gui.panel.DisplayRoomPanel;
import me.byungjin.network.Agent;

public class DisplayRoomDialog extends MiniDialog {

	public DisplayRoomDialog(Agent toServer, MiniDialogWorkListener l) {
		super(new DisplayRoomPanel(toServer), "Display Room", l); 		
	}

}
