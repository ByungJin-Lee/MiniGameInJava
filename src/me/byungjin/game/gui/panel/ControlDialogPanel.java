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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import me.byungjin.game.gui.listener.ControlPanelMouseListener;
import me.byungjin.manager.SystemManager;

public class ControlDialogPanel extends JPanel {
	public ControlDialogPanel(String title) {
		Cursor hand = new Cursor(Cursor.HAND_CURSOR);
		setBackground(Color.WHITE);
//		setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		setPreferredSize(new Dimension(10, 25));
		setLayout(new GridLayout(1, 2));
		
		JPanel left = new JPanel();
		left.setBackground(Color.WHITE);
		FlowLayout flowLayout_left = (FlowLayout) left.getLayout();
		flowLayout_left.setAlignment(FlowLayout.LEFT);
		JLabel tit = new JLabel(title);
		tit.setForeground(Color.gray);
		left.add(tit);
		
		add(left);
		
		JPanel right = new JPanel();
		right.setBackground(Color.WHITE);
		FlowLayout flowLayout_right = (FlowLayout) right.getLayout();
		flowLayout_right.setHgap(0);
		flowLayout_right.setVgap(0);
		flowLayout_right.setAlignment(FlowLayout.RIGHT); 
					
		
		JButton btn_exit = new JButton("X");
		btn_exit.setForeground(Color.LIGHT_GRAY);
		btn_exit.setFocusPainted(false);
		btn_exit.setContentAreaFilled(false);
		btn_exit.setBorderPainted(false);		
		btn_exit.setCursor(hand);
		btn_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
			}
		});
		right.add(btn_exit);
		
		add(right);
		
		ControlPanelMouseListener controlAdapter = new ControlPanelMouseListener();
		addMouseListener(controlAdapter);
		addMouseMotionListener(controlAdapter);
		
	}
}

