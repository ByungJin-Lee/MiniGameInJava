package me.byungjin.game.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import me.byungjin.game.gui.panel.ControlClientPanel;
import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.manager.NetworkManager;
import me.byungjin.manager.SystemManager;
import me.byungjin.network.Agent;

public class ConnectWindow extends JFrame {
	private Agent agent;
	public ConnectWindow(Agent age, boolean running) {		
		agent = age;
		setUndecorated(true);
		
		Container con = getContentPane();
		con.setLayout(new GridLayout(1,1));
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setBorder(new LineBorder(Color.GRAY));
		
		container.add(new ControlClientPanel("Connect"), BorderLayout.NORTH);
		
		JPanel content = new JPanel();		
		
		content.setLayout(new BorderLayout(5,8));
		content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		content.add(new JLabel(NetworkManager.getNetworkIp()), BorderLayout.NORTH);
		
		JPanel inner = new JPanel();
		inner.setLayout(new GridLayout(3,2));				
		
		JLabel label_ip = new JLabel("IP");
		
		inner.add(label_ip);
		
		JTextField text_ipField = new JTextField(16);
		
		inner.add(text_ipField);
		
		JButton connect_open = new JButton("connect");
		connect_open.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					agent = NetworkManager.getClientToHost(null, text_ipField.getText());
					agent.open();
				} catch (Exception e1) { 
					SystemManager.catchException(ENVIRONMENT.GUI, e1);
				}
			}
		});
		inner.add(connect_open);
		
		JButton connect_close = new JButton("disconnect");
		connect_close.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				agent.close();				
			}
		});
		inner.add(connect_close);	
		
		JButton host_open = new JButton("host open");
		host_open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					agent = NetworkManager.getHost(null, "Host");
					agent.open();
				} catch (Exception e1) {
					SystemManager.catchException(ENVIRONMENT.GUI, e1);
				}				
			}
		});
		inner.add(host_open);
		
		JButton host_close = new JButton("host close");
		host_close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				agent.close();
			}
		});
		inner.add(host_close);
		
		
		content.add(inner);			
		
		container.add(content, BorderLayout.CENTER);
		
		con.add(container);
		
		setSize(getPreferredSize());
		setVisible(true);
	}
}
