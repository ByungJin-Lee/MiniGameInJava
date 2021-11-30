package me.byungjin.game.gui.panel;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import me.byungjin.game.gui.MiniDialogPanel;
import resource.ResourceLoader;

public class PopUpPanel extends MiniDialogPanel {
	public PopUpPanel(String txt) {
		setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		setLayout(new GridLayout(1, 1));
		
		setBackground(Color.white);
		
		JLabel content = new JLabel(txt);
		content.setFont(ResourceLoader.DEFAULT_FONT);
		add(content);
	}
}
