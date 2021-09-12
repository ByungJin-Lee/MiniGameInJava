package me.byungjin.game.gui;

import javax.swing.JFrame;

import me.byungjin.game.omock.gui.OmokPanel;

public class OmokWindow extends JFrame {	
	public OmokWindow() {		
		setLayout(null);
		setSize(734,755);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						
		getContentPane().setLayout(null);
		getContentPane().add(new OmokPanel());		
		
		setVisible(true);		
	}	
}
