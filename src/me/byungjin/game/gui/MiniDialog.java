package me.byungjin.game.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import me.byungjin.game.gui.panel.ControlDialogPanel;

public class MiniDialog extends JDialog {
	public MiniDialog(JPanel content, String text) {		
		setUndecorated(true);		
		setLayout(new GridLayout(1,1));
		setMinimumSize(new Dimension(200, 150));
		setLocationRelativeTo(null);
		
		JPanel container = new JPanel();		
		container.setBorder(new LineBorder(Color.LIGHT_GRAY));		
		container.setLayout(new BorderLayout());
		
		container.add(new ControlDialogPanel(text), BorderLayout.NORTH);
		
		JPanel padding = new JPanel();
		padding.setLayout(new GridLayout(1,1));
		padding.setBorder(new EmptyBorder(5, 5, 5, 5));
		padding.add(content);			
		
		container.add(padding, BorderLayout.CENTER);
		add(container);
		setVisible(true);
	}
}
