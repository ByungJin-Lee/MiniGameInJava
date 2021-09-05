package me.byungjin.minigame.gui;

import java.awt.Container;

import javax.swing.JFrame;

public class ReadyFrame extends JFrame {
	
	public ReadyFrame() {		
		setTitle("NULL");		
		setResizable(false);
		setLocationRelativeTo(null);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);								
//		setLayout(new GridBagLayout());					
		
		Container contentPane = getContentPane();
		
		contentPane.add(new ReadyPanel());

		setSize(200,80);
		setVisible(true);
	}	
}
