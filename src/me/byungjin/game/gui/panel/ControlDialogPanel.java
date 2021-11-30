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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import me.byungjin.game.gui.listener.ControlPanelMouseListener;
import me.byungjin.manager.SystemManager;
import resource.ResourceLoader;

public class ControlDialogPanel extends JPanel {
	public ControlDialogPanel(String title) {
		Cursor hand = new Cursor(Cursor.HAND_CURSOR);
		setBackground(Color.WHITE);
//		setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		setPreferredSize(new Dimension(10, 30));
		setLayout(new GridLayout(1, 2));
		
		JPanel left = new JPanel();
		left.setBackground(Color.WHITE);
		FlowLayout flowLayout_left = (FlowLayout) left.getLayout();
		flowLayout_left.setAlignment(FlowLayout.LEFT);
		JLabel tit = new JLabel(title);
		tit.setFont(ResourceLoader.H_FONT);
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
		btn_exit.setOpaque(true);
		btn_exit.setBackground(Color.white);
		btn_exit.setCursor(hand);
		btn_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
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
		
		add(right);
		
		ControlPanelMouseListener controlAdapter = new ControlPanelMouseListener();
		addMouseListener(controlAdapter);
		addMouseMotionListener(controlAdapter);
		
	}
}

