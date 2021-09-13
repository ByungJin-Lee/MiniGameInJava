package me.byungjin.game.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import me.byungjin.game.gui.panel.ControlClientPanel;

public class ChatWindow extends JFrame {

	public ChatWindow() {
		setUndecorated(true);
		
		Container con = getContentPane();
		con.setLayout(new GridLayout(1,1));
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setBorder(new LineBorder(Color.GRAY));
		
		container.add(new ControlClientPanel("Chat"), BorderLayout.NORTH);
		
		JPanel content = new JPanel();
		content.setPreferredSize(new Dimension(250, 400));
		
		container.add(content, BorderLayout.CENTER);
		
		
		con.add(container);
		
		setSize(getPreferredSize());
		setVisible(true);
	}

}
