package me.byungjin.game.gui;

import me.byungjin.game.gui.panel.ControlClientPanel;
import me.byungjin.network.Agent;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class ClientWindow extends JFrame {
	private Agent agentToServer;
	private Agent client;

	public ClientWindow(Agent agent) {
		this.agentToServer = agent;
		setUndecorated(true);
		setTitle("Client");

		Container con = getContentPane();
		con.setLayout(new GridLayout(1,1));

		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setBorder(new LineBorder(Color.GRAY));

		container.add(new ControlClientPanel("Mini game"), BorderLayout.NORTH);

		JPanel content = new JPanel();
		content.setPreferredSize(new Dimension(250, 400));

		container.add(content, BorderLayout.CENTER);
		con.add(container);

		setSize(getPreferredSize());
		setVisible(true);
	}

	public void close(){
		if(agentToServer == null && client == null) return;

		if(agentToServer != null && agentToServer.isRunning())
			agentToServer.close();

		if(client != null && client.isRunning()){
			client.close();
		}
		
		dispose();
	}
}
