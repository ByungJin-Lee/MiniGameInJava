package me.byungjin.game.gui.page;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import me.byungjin.game.gui.ServerWindow;

public class Page extends JPanel {	
	public Page(){
		setBackground(ServerWindow.INNER_BGCOLOR);
		setLayout(new FlowLayout(FlowLayout.LEFT));
	}
}
