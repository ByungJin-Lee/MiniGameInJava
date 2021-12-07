package me.byungjin.game.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import me.byungjin.game.gui.item.ChatItem;
import me.byungjin.network.Agent;
import me.byungjin.network.event.DataComeInEvent;
import resource.ResourceLoader;

public class ChatInnerPanel extends JPanel {
	private Agent agentToHost;
	private JPanel input;
	private JTextArea input_area;
	private Vector<ChatItem> chats = new Vector<ChatItem>();
	private Color bkColor = new Color(240,240,240);
	private int chatY = 10;
	private boolean mute = false;
	private CustomScrollPane c_scroll;
	final short LIMITE = 20;
	
	public ChatInnerPanel(Agent agentToHost) {
		this.agentToHost = agentToHost;		
		
		setSize(350, 600);
		setBackground(Color.white);
		setLayout(new BorderLayout());		
		JPanel inner = new JPanel();
		inner.setLayout(null);
		inner.setBackground(bkColor);
		
		
		input = new JPanel();
		input.setLayout(null);
		input.setBackground(Color.white);
		input.setPreferredSize(new Dimension(getWidth(), 76));		
		
		JPanel input_right = new JPanel();		
		input_right.setBounds(286, 8, 56, 60);
		input_right.setBackground(Color.white);
		input_right.setLayout(new BorderLayout());
		
		JPanel input_inner_right = new JPanel();		
		input_inner_right.setBackground(Color.WHITE);
		input_inner_right.setLayout(new GridLayout(2,1));
		
		JLabel label_len = new JLabel("0/"+LIMITE, SwingConstants.CENTER);		
		label_len.setFont(ResourceLoader.DEFAULT_FONT);
		JButton btn_send = new JButton("Àü¼Û");	
		btn_send.setFont(ResourceLoader.DEFAULT_FONT12);
		btn_send.setBorderPainted(false);
		btn_send.setFocusPainted(false);
		btn_send.setContentAreaFilled(false);
		btn_send.setOpaque(true);
		btn_send.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				sendChat();
			}
		});
		input_inner_right.add(btn_send);
		input_inner_right.add(label_len);
		input_right.add(input_inner_right, BorderLayout.NORTH);
		
		
		input_area = new JTextArea(4, 50);
		input_area.setBounds(8, 8, 270, 60);		
		input_area.setFont(ResourceLoader.DEFAULT_FONT14);
		
		input_area.addKeyListener(new KeyListener() {			
			@Override
			public void keyTyped(KeyEvent e) {				
			}			
			@Override
			public void keyReleased(KeyEvent e) {		
				int len = input_area.getText().length();							
				
				if(len > LIMITE)
					label_len.setText("Over");
				else
					label_len.setText(len + "/" + LIMITE);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendChat();			
					e.consume();
				}				
			}
		});		
		
		input.add(input_area);
				
		input.add(input_right);
		
		c_scroll = new CustomScrollPane(inner,new Color(240,240,240));
		
		add(c_scroll, BorderLayout.CENTER);
		add(input, BorderLayout.SOUTH);			
		agentChatSetting();
	}
	
	public void agentChatSetting() {
		if(this.agentToHost == null) return;
		
		if(this.agentToHost != null && !this.agentToHost.isRunning()) return;
		
		this.agentToHost.addChatComeInEvent(new DataComeInEvent() {
			@Override
			public void dispatch(Object source, String data) {				
				assignChats(new ChatItem(data.substring(5), false, bkColor));
			}
		});
	}
	
	public void sendChat() {
		String text = input_area.getText().trim();		
		
		if(text.length() == 0) return;	
		
		if(text.length() > LIMITE) return;		
		
		if(text.equals("mute")) {
			mute = true;
		}else if(text.equals("unmute")) {
			mute = false;
			return;
		}				
		
		if(mute)
			return;
		
		assignChats(new ChatItem(text, true, bkColor));
		
		input_area.setText("");
		
		this.agentToHost.chat(text);
	}
	
	public void assignChats(ChatItem item) {	
		if(mute) {			
			return;
		}
		
		chats.add(item);		
		
		if(item.isMine()) {
			item.setLocation(335 - item.getWidth(), chatY);
		}else {
			item.setLocation(10, chatY);						
		}
		
		chatY = chatY + item.getHeight() + 5;
		c_scroll.addInnerItem(item);		
		c_scroll.setInnerSize(new Dimension(getWidth(), chatY));		
	}
	
	
}
