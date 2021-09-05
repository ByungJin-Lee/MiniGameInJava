package me.byungjin.minigame.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;

import me.byungjin.minigame.actionlisteners.ChatSendActionListener;

public class ReadyPanel extends JPanel {
	
	public ReadyPanel() {
		JTextField field = new JTextField(15);
		
		field.addActionListener(new ChatSendActionListener(field));
		
		add(field);
	}
}
