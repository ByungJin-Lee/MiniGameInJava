package me.byungjin.game.gui.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import me.byungjin.game.gui.listener.ControlPanelMouseListener;
import me.byungjin.game.gui.listener.ControlRunningMouseListener;
import me.byungjin.manager.SystemManager;
import resource.ResourceLoader;

public class ControlServerPanel extends JPanel {
	public ControlServerPanel() {
		Cursor hand = new Cursor(Cursor.HAND_CURSOR);
//		setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		setPreferredSize(new Dimension(10, 25));
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setHgap(0);
		flowLayout.setVgap(0);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		setBackground(Color.WHITE);
		
		JToggleButton btn_running = new JToggleButton();
		btn_running.setContentAreaFilled(false);
		btn_running.setBorderPainted(false);
		btn_running.setFocusPainted(false);
		btn_running.setCursor(hand);
		btn_running.setIcon(ResourceLoader.ICON_SWITCH_OFF);
		btn_running.addMouseListener(new ControlRunningMouseListener());
		add(btn_running);
		
		JButton btn_minimize = new JButton("_");
		btn_minimize.setForeground(Color.LIGHT_GRAY);
		btn_minimize.setFocusPainted(false);
		btn_minimize.setContentAreaFilled(false);
		btn_minimize.setBorderPainted(false);
		btn_minimize.setCursor(hand);
		btn_minimize.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				((JFrame)SwingUtilities.getWindowAncestor((Component) e.getSource())).setState(Frame.ICONIFIED);
			}
		});
		add(btn_minimize);
		
		JButton btn_exit = new JButton("X");
		btn_exit.setForeground(Color.LIGHT_GRAY);
		btn_exit.setFocusPainted(false);
		btn_exit.setContentAreaFilled(false);
		btn_exit.setBorderPainted(false);
		btn_exit.setBackground(Color.PINK);
		btn_exit.setCursor(hand);
		btn_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SystemManager.disconnectDB();
				((JFrame)SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
			}
		});
		add(btn_exit);
		
		ControlPanelMouseListener controlAdapter = new ControlPanelMouseListener();
		addMouseListener(controlAdapter);
		addMouseMotionListener(controlAdapter);
		
	}
}
