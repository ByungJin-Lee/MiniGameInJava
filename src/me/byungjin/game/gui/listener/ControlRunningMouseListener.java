package me.byungjin.game.gui.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JToggleButton;

import me.byungjin.db.DBConnection;
import me.byungjin.manager.AssetManager;
import me.byungjin.manager.SystemManager;

public class ControlRunningMouseListener extends MouseAdapter{
	private boolean isRunning = false;
	@Override
	public void mouseClicked(MouseEvent e) {
		JToggleButton btn = (JToggleButton)e.getComponent();		
		if(!isRunning) {
			SystemManager.connectDB();
			btn.setIcon(AssetManager.ICON_SWITCH_ON);
		}else {
			SystemManager.disconnectDB();
			btn.setIcon(AssetManager.ICON_SWITCH_OFF);
		}
		isRunning = !isRunning;
	}
}
