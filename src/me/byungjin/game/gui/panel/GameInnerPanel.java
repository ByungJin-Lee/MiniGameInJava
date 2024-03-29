package me.byungjin.game.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import me.byungjin.db.Rank;
import me.byungjin.db.UserSchema;
import me.byungjin.game.GameEndEvent;
import me.byungjin.game.GameKind;
import me.byungjin.game.Room;
import me.byungjin.game.gui.ClientWindow;
import me.byungjin.game.gui.ConnectErrorDialog;
import me.byungjin.game.gui.CreateRoomDialog;
import me.byungjin.game.gui.DisplayRoomDialog;
import me.byungjin.game.gui.PopUpDialog;
import me.byungjin.game.gui.item.UserItem;
import me.byungjin.game.gui.listener.MiniDialogWorkListener;
import me.byungjin.game.omock.gui.OmokPanel;
import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.manager.NetworkManager;
import me.byungjin.manager.SystemManager;
import me.byungjin.network.Agent;
import me.byungjin.network.PROMISE;
import me.byungjin.network.event.DataComeInEvent;
import resource.ResourceLoader;

public class GameInnerPanel extends JPanel {
	private LandingPanel landing;
	private Agent agentToHost;
	private Agent agentToServer;
	private UserSchema client;
	private DraggablePanel parent;	
	private Rank rank;
	private boolean isUser = false;
	private JPanel control_panel;
	private JPanel info_panel;	
	private boolean search = true;
	private	GameKind kind = GameKind.OMOK;
	private JLabel lbl_rank;
	private Room curRoom;
	private boolean host = false;
	
	private JPanel clientItem;
	
	
	public GameInnerPanel(UserSchema c, Agent server, DraggablePanel panel, LandingPanel landing) {
		setPreferredSize(new Dimension(300,500));
		this.landing = landing;		
		clientItem = new JPanel();
		clientItem.setBorder(null);
		clientItem.setBackground(Color.white);
		clientItem.setLayout(new GridLayout(1,1));
		
		parent = panel;		
		setLayout(null);
		control_panel = new JPanel();
		control_panel.setLocation(0, 0);
		control_panel.setBackground(Color.white);
		
		info_panel = new JPanel();		
		info_panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		info_panel.setLocation(0, 400);
		info_panel.setSize(300,100);
		info_panel.setBackground(Color.white);
		add(control_panel);
		add(info_panel);
		setSize(300, 500);
		
		ResourceLoader.readDefaultUser();
		this.agentToServer = server;		
		this.client = c;
		setAgentToServerEvent();
		setControlSetting();
		setInfoSetting();		
	}
	
	public void setInfoSetting() {	
		info_panel.removeAll();		
		info_panel.setLayout(new GridLayout(2,1));		
		JPanel c_panel = new JPanel();
		c_panel.setLayout(new BorderLayout());
		
		JPanel game_kind = new JPanel();		
		game_kind.setLayout(new BorderLayout(5, 0));
		game_kind.setBackground(Color.white);
		
		JLabel g_label = new JLabel("오목");
		g_label.setFont(ResourceLoader.DEFAULT_FONT);
		g_label.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton left = new JButton("<");
		JButton right = new JButton(">");
		left.setContentAreaFilled(false);
		left.setFocusPainted(false);
		left.setBorderPainted(false);
		left.setFont(ResourceLoader.DEFAULT_FONT);
		left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				kind = kind.next(false);
				g_label.setText(kind.switchString());
			}
		});
		right.setContentAreaFilled(false);
		right.setFocusPainted(false);
		right.setBorderPainted(false);
		right.setFont(ResourceLoader.DEFAULT_FONT);
		right.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				kind = kind.next(true);
				g_label.setText(kind.switchString());
			}
		});
		
		
		game_kind.add(left, BorderLayout.WEST);
		game_kind.add(right, BorderLayout.EAST);
		game_kind.add(g_label, BorderLayout.CENTER);		
		game_kind.setBackground(Color.white);		

		c_panel.add(game_kind, BorderLayout.CENTER);							
		
		JButton btn_search = new JButton("R");		
		btn_search.setBackground(Color.white);
		btn_search.setContentAreaFilled(true);
		btn_search.setBorderPainted(false);	
		btn_search.setFocusPainted(false);
		btn_search.setFont(ResourceLoader.H_FONT);
		btn_search.setPreferredSize(new Dimension(45,10));
		lbl_rank = new JLabel("");
		lbl_rank.setFont(ResourceLoader.DEFAULT_FONT);
		lbl_rank.setHorizontalAlignment(SwingConstants.CENTER);
		btn_search.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				search = true;
				getRank(client.getId(), kind);
			}
		});
		c_panel.add(btn_search, BorderLayout.EAST);
		
		JLabel lbl_title = new JLabel("Rank");
		lbl_title.setOpaque(true);
		lbl_title.setFont(ResourceLoader.H_FONT);
		lbl_title.setBackground(Color.white);
		lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		c_panel.add(lbl_title, BorderLayout.WEST);		
		info_panel.add(c_panel);
		
		
		info_panel.add(lbl_rank);
		getRank(client.getId(), kind);
	}
	
	public void getRank(String id, GameKind kind) {
		this.agentToServer.send(PROMISE.RANK, id + " " + kind);
	}
		
	
	public void setAgentToServerEvent() {
		if(this.agentToServer == null) return;
		
		this.agentToServer.addOtherComeInEvent(new DataComeInEvent() {
			@Override
			public void dispatch(Object source, String data) {
				onServerOtherEvent(data);
			}
		});
	}
	
	public void onServerOtherEvent(String data) {
		StringTokenizer tokens = new StringTokenizer(data);
		switch(PROMISE.valueOf(tokens.nextToken())) {
		case ROOM_SUC:
			setRoom();
			break;
		case ROOM_FAIL:			
			setControlSetting();
			break;
		case RANK_RETURN:			
			Rank retRank = new Rank(tokens.nextToken(), GameKind.valueOf(tokens.nextToken()), Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()));			
			if(search) {
				lbl_rank.setText("전적 - 승리 : " + retRank.getVictory() + " 패배 : " + retRank.getLose());
				search = false;
				break;
			}			
						
			if(retRank.getId().equals(client.getId())) {
				rank = retRank;
			}else {
				setProfile(retRank);
			}			
						
			break;
		}
	}
	
	synchronized public void setControlSetting() {			
		control_panel.removeAll();		
		
		JButton createRoom = new JButton("Create Room");
		createRoom.setBackground(Color.black);
		createRoom.setFocusPainted(false);
		createRoom.setForeground(Color.white);
		createRoom.setFont(ResourceLoader.H_FONT);
		JButton displayRoom = new JButton("Search Room");
		displayRoom.setBackground(Color.black);
		displayRoom.setFocusPainted(false);
		displayRoom.setForeground(Color.white);
		displayRoom.setFont(ResourceLoader.H_FONT);
		
		createRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateRoomDialog d = new CreateRoomDialog(new MiniDialogWorkListener() {				
					@Override
					public void completeDialogTask(Object result) {
						curRoom = (Room)result;						
						sendRoomData(curRoom);
					}
				});
				
			}
		});
		displayRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("serial")
				DisplayRoomDialog d = new DisplayRoomDialog(agentToServer, new MiniDialogWorkListener() {				
					@Override
					public void completeDialogTask(Object result) {
						setAgentToServerEvent();
						setRoomClient((Room)result);						
					}
				}) {
					@Override
					public void dispose() {
						setAgentToServerEvent();
						super.dispose();
					}
				};
			}
		});		
		
		control_panel.setBorder(BorderFactory.createEmptyBorder(150, 50, 150, 50));		
		control_panel.setLayout(new GridLayout(2, 1, 0, 10));		
		control_panel.add(createRoom);
		control_panel.add(displayRoom);		
		
		control_panel.setSize(300, 400);
		
		revalidate();
		repaint();
	}
	
	public void setRoom() {
		control_panel.removeAll();
		host = true;
		if(agentToHost != null && agentToHost.isRunning())
			agentToHost.close();
		
		try {
			agentToHost = NetworkManager.getHost("");
			((ClientWindow)SwingUtilities.getWindowAncestor(this)).setAgentToHost(agentToHost);
			agentToHost.addOtherComeInEvent(new DataComeInEvent() {				
				@Override
				public void dispatch(Object source, String data) {
					onOtherHostEvent(source, data);
				}	
			});			
			agentToHost.start();			
			displayRoomGUI(curRoom, true);
			this.landing.openChat();
		}catch(Exception e) {
			SystemManager.catchException(ENVIRONMENT.CLIENT, e);
			new ConnectErrorDialog();
			setControlSetting();
		}		
	}	
	
	public void onOtherHostEvent(Object source, String data) {
		StringTokenizer tokens = new StringTokenizer(data);
		switch(PROMISE.valueOf(tokens.nextToken())) {
		case ROOM_ENTER:
			getRank(tokens.nextToken(), kind);
			isUser = true;
			agentToHost.send(PROMISE.ROOM_ENTER_RETURN, client.getId());
			break;		
		case ROOM_ENTER_RETURN:
			getRank(tokens.nextToken(), kind);
			break;		
		case ROOM_EXIT:			
			exit();			
			break;
		case ROOM_START:			
			switch(curRoom.getGameKind()) {
			case OMOK:
				setOmok();
				break;
			default:
				break;
			}
			break;
		default:
				break;
		}
	}	
	
	public void displayReady() {
		
	}
	
	public void setProfile(Rank r) {				
		clientItem.removeAll();
		clientItem.add(new UserItem(r));
		clientItem.repaint();
		revalidate();
		repaint();
	}
	
	
	public void setRoomClient(Room info) {
		host = false;
		curRoom = info;
		control_panel.removeAll();
		if(agentToHost != null && agentToHost.isRunning())
			agentToHost.close();
		try {
			agentToHost = NetworkManager.getClientToHost(info.getIP());
			((ClientWindow)SwingUtilities.getWindowAncestor(this)).setAgentToHost(agentToHost);
			agentToHost.addOtherComeInEvent(new DataComeInEvent() {				
				@Override
				public void dispatch(Object source, String data) {
					onOtherHostEvent(source, data);
				}	
			});
			agentToHost.start();			
			displayRoomGUI(info, false);
			agentToHost.send(PROMISE.ROOM_ENTER, client.getId());
			this.landing.openChat();
		}catch(Exception e) {
			SystemManager.catchException(ENVIRONMENT.CLIENT, e);
			new ConnectErrorDialog();
			setControlSetting();
		}
	}
	
	public void sendRoomData(Room room) {
		agentToServer.send(PROMISE.ROOM, room.toString());
	}
	
	public void displayRoomGUI(Room room, boolean isHost) {
		clientItem.removeAll();
		
		JLabel room_title = new JLabel("Room - " + room.getRoomName());
		room_title.setFont(ResourceLoader.DEFAULT_FONT);
		room_title.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel room_kind = new JLabel(room.getGameKind().switchString());
		room_kind.setFont(ResourceLoader.DEFAULT_FONT14);
		room_kind.setForeground(Color.LIGHT_GRAY);
		room_kind.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel header_panel = new JPanel();
		header_panel.setBackground(Color.white);
		header_panel.setLayout(new GridLayout(2, 1, 0, 1));
		header_panel.add(room_title);
		header_panel.add(room_kind);
		
		JPanel center = new JPanel();
		center.setBackground(Color.white);
		center.setLayout(new GridLayout(1,1));
		center.setBorder(BorderFactory.createEmptyBorder(80, 0, 80, 0));
		//Player
		JPanel player_list = new JPanel();		
		player_list.setBackground(Color.white);
		player_list.setLayout(new GridLayout(1, 1, 10, 0));
		player_list.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));		
		player_list.add(clientItem);
		
		center.add(player_list);
		//Control
		JPanel control = new JPanel();
		control.setLayout(new GridLayout(1, 3));		
		
		JButton exit = new JButton("Exit");
		exit.setBorderPainted(false);
		exit.setFont(ResourceLoader.H_FONT);
		exit.setBackground(Color.pink);
		exit.setForeground(Color.white);
		exit.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		
		JButton btn_start = new JButton("Start");
		btn_start.setFont(ResourceLoader.H_FONT);
		btn_start.setBorderPainted(false);
		btn_start.setBackground(Color.black);
		btn_start.setForeground(Color.white);
		if(!host)
			btn_start.setText("Wait Host");
		btn_start.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				
				if(host && isUser) {
					agentToHost.send(PROMISE.ROOM_START, "");					
					switch(room.getGameKind()) {
					case OMOK:
						setOmok();
						break;
					default:
						break;
					}
				}
			}
		});
		
		JButton chat = new JButton("Chat");
		chat.setBorderPainted(false);
		chat.setFont(ResourceLoader.H_FONT);
		chat.setBackground(new Color(0xACD49F));
		chat.setForeground(Color.white);
		chat.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				landing.openChat();				
			}
		});
		
		control.add(btn_start);
		control.add(chat);
		control.add(exit);
		
		control_panel.setBorder(null);
		control_panel.setLayout(new BorderLayout());		
		
		control_panel.add(header_panel, BorderLayout.NORTH);
		control_panel.add(center, BorderLayout.CENTER);
		control_panel.add(control, BorderLayout.SOUTH);
		control_panel.revalidate();
		control_panel.repaint();
		
		getRank(client.getId(), room.getGameKind());
	}
	
	public void exit() {
		this.landing.closeChat();
		isUser = false;
		
		agentToHost.send(PROMISE.ROOM_EXIT, "");
		
		if(agentToHost != null)
			agentToHost.close();
				
		if(host)
			this.agentToServer.send(PROMISE.ROOM_END, curRoom.getRoomName());
		
		setControlSetting();		
	}
	
	public void setOmok() {
		removeAll();
				
		JPanel panel = new OmokPanel(this.agentToHost, this.agentToServer, new GameEndEvent() {		
			@Override
			public void dispatch(GameKind kind, boolean win) {				
				reset();
				if(win) {
					new PopUpDialog("You Win!");
					rank.win();
				}else {
					new PopUpDialog("You Lose...");
					rank.lose();
				}
				agentToServer.send(PROMISE.RANK_UPDATE, rank.toString());				
			}
		}); 
		add(panel);
		setPreferredSize(panel.getSize());
		setSize(panel.getSize());
		parent.resize();
		repaint();
	}
	
	public void reset() {	
		removeAll();
		add(control_panel);
		add(info_panel);
		setSize(300,500);
		setPreferredSize(new Dimension(300,500));
								
		setInfoSetting();
		setControlSetting();
		
		this.landing.closeChat();
				
		parent.resize();
				
		if(host)
			this.agentToServer.send(PROMISE.ROOM_END, curRoom.getRoomName());				
	}
}
