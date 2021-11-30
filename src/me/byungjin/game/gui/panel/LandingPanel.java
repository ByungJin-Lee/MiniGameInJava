package me.byungjin.game.gui.panel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import me.byungjin.network.Agent;

public class LandingPanel extends ChildPanel {
	private DraggablePanel chat;
	
	public LandingPanel() {
		setBackground(new Color(225,239,250));
		setLayout(null);
		
		setPreferredSize(new Dimension(1400, 900));
		setVisible(true);
	}

	@Override
	public void init() {
		DraggablePanel game = new DraggablePanel("Game", null);
				
		JPanel rank_inner = new JPanel();		
		
		GameInnerPanel game_inner = new GameInnerPanel(getUser(), getAgentToServer(), game, this);
		game_inner.setBackground(Color.white);
		game.setInner(game_inner);
		game.resize();		
			
		game.setLocation(10, 210);		
				
		add(game);
	}
	
	public void openChat() {
		Agent toHost = getAgentToHost(); 
		if(toHost != null) {
			if(chat != null)
				closeChat();
			chat = new DraggablePanel("Chat", new ChatInnerPanel(toHost));
			add(chat);
			revalidate();
			repaint();
		}
	}
	
	public void closeChat() {
		if(chat != null) {
			remove(chat);
			revalidate();
			repaint();
		}
	}
}
