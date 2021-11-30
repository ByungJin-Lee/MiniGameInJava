package me.byungjin.game.gui;

import me.byungjin.game.gui.panel.ChatInnerPanel;
import me.byungjin.network.Agent;

public class ChatDialog extends MiniDialog {
	public ChatDialog(Agent agentToHost) {
		super(new ChatInnerPanel(agentToHost), "Chat", null); 
	}
}
