package me.byungjin.game.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import me.byungjin.game.gui.panel.ControlClientPanel;
import me.byungjin.game.omock.gui.OmokPanel;
import me.byungjin.network.Agent;

public class OmokWindow extends JFrame {	
	public OmokWindow(Agent agent) {	
		setUndecorated(true);
		setLocationRelativeTo(null);
		
		Container con = getContentPane();
		con.setLayout(new GridLayout(1,1));
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setBorder(new LineBorder(Color.LIGHT_GRAY));
		container.add(new ControlClientPanel("Omok"), BorderLayout.NORTH);
		container.add(new OmokPanel(agent), BorderLayout.CENTER);
		
		con.add(container);
		
		setSize(getPreferredSize());
		setVisible(true);			
	}
}
