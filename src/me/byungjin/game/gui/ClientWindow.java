package me.byungjin.game.gui;

import me.byungjin.db.UserSchema;
import me.byungjin.game.gui.panel.ChildPanel;
import me.byungjin.game.gui.panel.ControlClientPanel;
import me.byungjin.game.gui.panel.LoginPanel;
import me.byungjin.network.Agent;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class ClientWindow extends JFrame{
	private Agent agentToServer, agentToHost;
	private UserSchema user;
	
	private JPanel container;
	private ChildPanel mPanel;
	
	public ClientWindow() {	
		setLocationRelativeTo(null);
		setUndecorated(true);		

		Container con = getContentPane();
		con.setLayout(new GridLayout(1,1));
		
		container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setBorder(new LineBorder(Color.GRAY));

		container.add(new ControlClientPanel("Online Mini Game"), BorderLayout.NORTH);		
		
		mPanel = new LoginPanel();		
		container.add(mPanel, BorderLayout.CENTER);
		
		con.add(container);
				
		mPanel.init();
		setSize(getPreferredSize());
		setResizable(true);
		setVisible(true);
	}
	
	public void changeMainPanel(ChildPanel panel) {
		container.remove(mPanel);
		mPanel = panel;
		container.add(panel, BorderLayout.CENTER);
		mPanel.init();
		setSize(getPreferredSize());		
	}
	
	public void setAgentToServer(Agent agent) {
		if(agent == null) return;
		
		this.agentToServer = agent;
	}
	
	public void setAgentToHost(Agent agent) {
		if(agent == null) return;
		
		this.agentToHost = agent;
	}
	
	public Agent getAgentToServer() {		
		synchronized(this) {
			return this.agentToServer;
		}		
	}
	public Agent getAgentToHost() {		
		synchronized(this) {
			return this.agentToHost;
		}		
	}
	
	public UserSchema getUser() {
		return this.user;
	}
	
	public void setUser(UserSchema user) {
		this.user = user;
	}

	@Override
	public void dispose(){		
		if(agentToServer != null && agentToServer.isRunning())
			agentToServer.close();

		if(agentToHost != null && agentToHost.isRunning())
			agentToHost.close();		
		
		super.dispose();
	}
		
}
