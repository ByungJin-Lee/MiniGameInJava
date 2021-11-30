package me.byungjin.game.gui.panel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import me.byungjin.game.gui.MiniDialogPanel;
import resource.ResourceLoader;

public class ConnectErrorPanel extends MiniDialogPanel {
	public ConnectErrorPanel() {
		setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		setLayout(new GridLayout(2, 1));
		
		setBackground(Color.white);
		
		JLabel content = new JLabel("���� ����! Sock�� �ùٸ��� �ʽ��ϴ�.");
		content.setFont(ResourceLoader.DEFAULT_FONT);
		add(content);
		
		JButton btn = new JButton("�ݱ�");
		btn.setBackground(Color.white);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				disposeWindow();
			}
		});
		add(btn);
		
	}
}
