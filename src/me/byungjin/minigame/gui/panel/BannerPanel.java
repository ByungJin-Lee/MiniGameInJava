package me.byungjin.minigame.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class BannerPanel extends JPanel {
	public BannerPanel() {
		setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		setPreferredSize(new Dimension(200, 10));		
		setLayout(new GridLayout(13, 1, 0, 0));
		setBackground(new Color(0xf1f8e9));
	}

}
