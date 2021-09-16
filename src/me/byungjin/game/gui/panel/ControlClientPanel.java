package me.byungjin.game.gui.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import me.byungjin.game.gui.listener.ControlPanelMouseListener;
import me.byungjin.manager.SystemManager;

public class ControlClientPanel extends JPanel {
	public ControlClientPanel(String title) {
		Cursor hand = new Cursor(Cursor.HAND_CURSOR);
//		setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		setPreferredSize(new Dimension(10, 25));
		setBackground(Color.WHITE);
		setLayout(new GridLayout(1,2));
		
		JPanel right = new JPanel();
		FlowLayout flowLayout_right = (FlowLayout) right.getLayout();
		flowLayout_right.setHgap(0);
		flowLayout_right.setVgap(0);
		flowLayout_right.setAlignment(FlowLayout.RIGHT);
		right.setBackground(Color.WHITE);	
		
		
		JPanel left = new JPanel();
		FlowLayout flowLayout_left = (FlowLayout) left.getLayout();
		flowLayout_left.setHgap(0);
		flowLayout_left.setVgap(0);
		flowLayout_left.setAlignment(FlowLayout.LEFT);
		left.setBackground(Color.WHITE);
		left.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
		
		JLabel label = new JLabel(title);
		label.setForeground(Color.gray);
		left.add(label);			
		
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
		right.add(btn_minimize);
		
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
		right.add(btn_exit);
		
		add(left);
		add(right);
		
		ControlPanelMouseListener controlAdapter = new ControlPanelMouseListener();
		addMouseListener(controlAdapter);
		addMouseMotionListener(controlAdapter);
		
	}
}

