package me.byungjin.game.gui.panel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import me.byungjin.game.gui.MiniDialogPanel;
import resource.ResourceLoader;

public class ConnectWarningPanel extends MiniDialogPanel {
	
	public ConnectWarningPanel() {				
		setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		setLayout(null);
		JLabel label = new JLabel("연결 상태가 좋지 않아 프로그램이 종료됩니다.");
		label.setFont(ResourceLoader.DEFAULT_FONT);
		label.setBounds(10, 10, 300, 20);
		add(label);
		JButton button = new JButton("확인");
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setOpaque(true);
		button.setBackground(Color.pink);
		button.setForeground(Color.white);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.addMouseListener(new MouseAdapter() {			
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(Color.white);
				button.setForeground(Color.pink);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				button.setBackground(Color.pink);
				button.setForeground(Color.white);
			}
		});
		
		button.setFont(ResourceLoader.DEFAULT_FONT);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				disposeWindow();
			}
		});				
		button.setBounds(123, 40, 65,30);
		add(button);	
		setSize(310, 75);
	}
}
