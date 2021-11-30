package me.byungjin.game.gui;

import me.byungjin.game.gui.listener.MiniDialogWorkListener;
import me.byungjin.game.gui.panel.CreateRoomPanel;

public class CreateRoomDialog extends MiniDialog {
	
	public CreateRoomDialog(MiniDialogWorkListener l){
		super(new CreateRoomPanel(), "Create Room", l);
	}
}
