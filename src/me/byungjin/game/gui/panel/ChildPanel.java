package me.byungjin.game.gui.panel;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import me.byungjin.db.UserSchema;
import me.byungjin.game.gui.ClientWindow;
import me.byungjin.network.Agent;

public abstract class ChildPanel extends JPanel {
	enum CHILDPANEL { LOGIN, LANDING }
	
	public void switchPanel(CHILDPANEL c)
	{
		ClientWindow window = (ClientWindow)SwingUtilities.getWindowAncestor(this);
		
		switch(c) {
		case LOGIN:
			
			break;
		case LANDING:
			window.changeMainPanel(new LandingPanel());
			break;
		}
	}
	public Agent getAgentToHost() {
		ClientWindow window = (ClientWindow)SwingUtilities.getWindowAncestor(this);
		return window.getAgentToHost();
	}
	public void setAgentToServer(Agent agent) {
		ClientWindow window = (ClientWindow)SwingUtilities.getWindowAncestor(this);
		window.setAgentToServer(agent);
	}
	
	public Agent getAgentToServer() {
		ClientWindow window = (ClientWindow)SwingUtilities.getWindowAncestor(this);
		return window.getAgentToServer();
	}
	
	public UserSchema getUser() {
		ClientWindow window = (ClientWindow)SwingUtilities.getWindowAncestor(this);
		return window.getUser();
	}
	
	public void setUser(UserSchema u) {
		ClientWindow window = (ClientWindow)SwingUtilities.getWindowAncestor(this);
		window.setUser(u);
	}
	
	public abstract void init(); 
}

