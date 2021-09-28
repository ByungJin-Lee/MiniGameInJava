package me.byungjin.game.gui.page;

import me.byungjin.game.gui.ServerWindow;
import me.byungjin.manager.DBManager;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

public class ServerStatusPage extends Page {
	
    public ServerStatusPage(ServerWindow window){
    	super();    	  
    	setLayout(new GridLayout(8,1));
    	
    	JPanel topControlPanel = new JPanel();
    	topControlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    	Border controlBorder = BorderFactory.createLineBorder(Color.lightGray);    	
    	
    	Border topControlBorder = BorderFactory.createTitledBorder(controlBorder ,"Connect Status");
    	
    	topControlPanel.setBackground(ServerWindow.INNER_BGCOLOR);
    	
    	JLabel dbLabel = new JLabel("DB Connect : ");
    	JLabel servLabel = new JLabel("Server Connect : ");
    	
    	JButton dbBtn = new JButton("Try Connection");
    	dbBtn.setBorderPainted(false);
    	dbBtn.setContentAreaFilled(false);
    	dbBtn.setFocusPainted(false);
    	dbBtn.setOpaque(true);
    	dbBtn.setBackground(Color.red);
    	dbBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	dbBtn.addActionListener(new ActionAdapter() {    	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isRunning) {
					isRunning = window.openDB();
				}else {
					window.stopDB();
					isRunning = false;
				}
				display();
			}
			@Override
			public void init() {
				isRunning = DBManager.isConnection();
				display();
			}			
			public void display() {
				if(!isRunning) {
					dbBtn.setText("Try Connection");
					dbBtn.setBackground(Color.red);
				}else {
					dbBtn.setText("Try Disconnection");
					dbBtn.setBackground(Color.green);
				}
			}	
		});
    	JButton serverBtn = new JButton("Bulid Server");
    	serverBtn.setBorderPainted(false);
    	serverBtn.setContentAreaFilled(false);
    	serverBtn.setFocusPainted(false);
    	serverBtn.setOpaque(true);
    	serverBtn.setBackground(Color.red);
    	serverBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	serverBtn.addActionListener(new ActionAdapter() {		    		
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isRunning) {
					isRunning = window.openServer();
				}else {
					window.stopServer();
					isRunning = false;
				}
				display();
			}
			@Override
			public void init() {
				isRunning = window.isServerRunning();
				display();
			}
			public void display() {
				if(!isRunning) {
					serverBtn.setText("Bulid Server");
					serverBtn.setBackground(Color.red);
					
				}else {
					serverBtn.setText("Shutdown Server");
					serverBtn.setBackground(Color.green);
				}
			}			
		});
    	topControlPanel.add(dbLabel);
    	topControlPanel.add(dbBtn);
    	topControlPanel.add(servLabel);
    	topControlPanel.add(serverBtn);
    	
    	topControlPanel.setBorder(topControlBorder);    	
    	add(topControlPanel);
    }
    
    public abstract class ActionAdapter implements ActionListener{
    	protected boolean isRunning;
    	public ActionAdapter() {
    		init();
    	}
    	public abstract void init();
    }
}
