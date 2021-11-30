package me.byungjin.game.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import me.byungjin.game.gui.item.MenuItem;
import me.byungjin.game.gui.listener.ItemMouseListener;
import me.byungjin.game.gui.menu.MonitorMenu;

public class MenuPanel extends JPanel {
	public MenuPanel(JPanel banner, JPanel inner) {
		setBorder(new EmptyBorder(0, 5, 0, 5));
		setAlignmentY(0.0f);
		setAlignmentX(0.0f);		
		setPreferredSize(new Dimension(40, 10));
		setBackground(Color.WHITE);	
		
		ItemMouseListener itemMouseLinstener = new ItemMouseListener();
		
		GridLayout layout = new GridLayout(0, 1, 0, 0);
		layout.setVgap(5);
		setLayout(layout);		
		
		JLabel menu_blank = new JLabel("");
		add(menu_blank);
		
		MenuItem btn_monitor = new MonitorMenu(banner, inner);
		btn_monitor.addMouseListener(itemMouseLinstener);
		add(btn_monitor);		
		
		JLabel menu_blank_0 = new JLabel("");
		add(menu_blank_0);
		
		JLabel menu_blank_1 = new JLabel("");
		add(menu_blank_1);
		
		JLabel menu_blank_2 = new JLabel("");
		add(menu_blank_2);
		
		JLabel menu_blank_3 = new JLabel("");
		add(menu_blank_3);
		
		JLabel menu_blank_4 = new JLabel("");
		add(menu_blank_4);
		
		JLabel menu_blank_5 = new JLabel("");
		add(menu_blank_5);
		
		JLabel menu_blank_6 = new JLabel("");
		add(menu_blank_6);
		
		JLabel menu_blank_7 = new JLabel("");
		add(menu_blank_7);
		
		JLabel menu_blank_8 = new JLabel("");
		add(menu_blank_8);		
	}
}
