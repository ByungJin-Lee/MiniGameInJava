package me.byungjin.game.gui.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import me.byungjin.game.gui.listener.ControlPanelMouseListener;
import me.byungjin.manager.DBManager;
import me.byungjin.manager.SystemManager;
import resource.ResourceLoader;

public class ControlServerPanel extends JPanel {
	private JLabel dbLabel;
	private JLabel serverLabel;
	
	public ControlServerPanel() {
		Cursor hand = new Cursor(Cursor.HAND_CURSOR);
//		setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		setPreferredSize(new Dimension(10, 25));
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setHgap(5);
		flowLayout.setVgap(0);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		setBackground(Color.WHITE);
		
		dbLabel = new JLabel("DB");
		dbLabel.setFont(ResourceLoader.DEFAULT_FONT);
		dbLabel.setForeground(Color.red);
		serverLabel = new JLabel("SERV");
		serverLabel.setFont(ResourceLoader.DEFAULT_FONT);
		serverLabel.setForeground(Color.red);
		
		add(dbLabel);
		add(serverLabel);
		
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
		btn_exit.setOpaque(true);
		btn_exit.setBackground(Color.white);
		btn_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DBManager.disconnectDB();
				((JFrame)SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
			}
		});
		btn_exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_exit.setForeground(Color.white);
				btn_exit.setBackground(Color.pink);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btn_exit.setBackground(Color.white);
				btn_exit.setForeground(Color.LIGHT_GRAY);
			}
		});
		add(btn_exit);
		
		ControlPanelMouseListener controlAdapter = new ControlPanelMouseListener();
		addMouseListener(controlAdapter);
		addMouseMotionListener(controlAdapter);		
	}
	
	public void serverOpen() {
		serverLabel.setForeground(Color.green);
	}
	public void serverClose() {
		serverLabel.setForeground(Color.red);
	}
	public void dbOpen() {
		dbLabel.setForeground(Color.green);
	}
	public void dbClose() {
		dbLabel.setForeground(Color.red);
	}
}
