package me.byungjin.game.gui.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JToggleButton;

import me.byungjin.manager.SystemManager;
import resource.ResourceLoader;

public class ControlRunningMouseListener extends MouseAdapter{
	private boolean isRunning = false;
	@Override
	public void mouseClicked(MouseEvent e) {
		JToggleButton btn = (JToggleButton)e.getComponent();		
		if(!isRunning) {
			SystemManager.connectDB();
			btn.setIcon(ResourceLoader.ICON_SWITCH_ON);
		}else {
			SystemManager.disconnectDB();
			btn.setIcon(ResourceLoader.ICON_SWITCH_OFF);
		}
		isRunning = !isRunning;
	}
}
