package me.byungjin.minigame.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.minigame.MiniGame;

public class ChatSendActionListener implements ActionListener{
	private JTextField field;	
	/**
	 * ä�� �ʵ� ���
	 * @param field
	 */
	public ChatSendActionListener(JTextField field) {
		this.field = field;
	}
	@Override
	public void actionPerformed(ActionEvent e) {		
		if(field != null && MiniGame.man != null) {			
			MiniGame.man.send(ENVIRONMENT.CHAT, field.getText());
		}
					
	}	
}
