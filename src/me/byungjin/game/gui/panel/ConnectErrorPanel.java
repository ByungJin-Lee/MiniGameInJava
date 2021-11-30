package me.byungjin.game.gui.panel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import me.byungjin.game.gui.MiniDialogPanel;
import resource.ResourceLoader;

public class ConnectErrorPanel extends MiniDialogPanel {
	public ConnectErrorPanel() {
		setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		setLayout(new GridLayout(2, 1));
		setSize(320,120);
		
		setBackground(Color.white);
		
		JLabel content = new JLabel("연결 오류! Sock이 올바르지 않습니다.");
		content.setHorizontalAlignment(SwingConstants.CENTER);
		content.setFont(ResourceLoader.DEFAULT_FONT);
		add(content);
		
		JButton btn = new JButton("닫기");
		btn.setFont(ResourceLoader.DEFAULT_FONT);
		btn.setContentAreaFilled(false);
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
