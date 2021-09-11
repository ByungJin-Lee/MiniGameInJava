package me.byungjin.minigame.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import me.byungjin.minigame.gui.panel.BannerPanel;
import me.byungjin.minigame.gui.panel.ControlPanel;
import me.byungjin.minigame.gui.panel.InnerPanel;
import me.byungjin.minigame.gui.panel.MenuPanel;

public class MasterWindow extends JFrame {
	private BannerPanel panel_banner;
	private InnerPanel panel_inner;	
	
	public MasterWindow() {		
		setSize(800, 600);
		setUndecorated(true);		
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));		
		
		JPanel panel_border = new JPanel();
		getContentPane().add(panel_border);		
		panel_border.setLayout(new BorderLayout(0, 0));				
		panel_border.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		JPanel panel_right = new JPanel();
		panel_right.setBorder(new EmptyBorder(0, 0, 5, 5));
		panel_right.setBackground(Color.WHITE);
		panel_border.add(panel_right, BorderLayout.CENTER);
		panel_right.setLayout(new BorderLayout(5, 0));
		
		//Control
					
		panel_right.add(new ControlPanel(), BorderLayout.NORTH);			
		
		//Banner
		panel_banner = new BannerPanel();
		panel_right.add(panel_banner, BorderLayout.WEST);
		
		//Container
		Color color_container = new Color(244,247,252);
		
		JPanel panel_container = new JPanel();
		panel_container.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		panel_container.setBackground(color_container);
		panel_container.setLayout(new GridLayout(0, 1, 0, 0));
		panel_right.add(panel_container, BorderLayout.CENTER);				
		
		JPanel panel_padding = new JPanel();		
		panel_padding.setBackground(color_container);
		panel_padding.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		JScrollPane scroll = new JScrollPane(panel_padding); 
		scroll.setBorder(null);
		
		panel_container.add(scroll);
		panel_padding.setLayout(new GridLayout(0, 1, 0, 0));
		
		//Inner		
		panel_inner = new InnerPanel();		
		panel_inner.setBackground(color_container);		
		panel_padding.add(panel_inner);
		
		//Menu
		panel_border.add(new MenuPanel(panel_banner, panel_inner), BorderLayout.WEST);
		
		setVisible(true);

	}
}
