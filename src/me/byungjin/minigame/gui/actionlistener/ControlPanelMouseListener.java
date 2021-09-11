package me.byungjin.minigame.gui.actionlistener;

import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

public class ControlPanelMouseListener extends MouseAdapter {
	private Point clickedPoint;	
	private Window master;
	
	@Override
	public void mousePressed(MouseEvent e) {
		clickedPoint= e.getPoint();		
		master = SwingUtilities.getWindowAncestor(e.getComponent());
	}
	@Override
	public void mouseDragged(MouseEvent e) {		
		master.setLocation(master.getX() + e.getX() - clickedPoint.x, master.getY() + e.getY() - clickedPoint.y);		
	}
}
