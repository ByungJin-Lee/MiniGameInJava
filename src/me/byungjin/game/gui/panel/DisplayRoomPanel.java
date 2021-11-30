package me.byungjin.game.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import me.byungjin.game.GameKind;
import me.byungjin.game.Room;
import me.byungjin.game.gui.MiniDialogPanel;
import me.byungjin.game.gui.item.RoomItem;
import me.byungjin.network.Agent;
import me.byungjin.network.PROMISE;
import me.byungjin.network.event.DataComeInEvent;
import resource.ResourceLoader;

public class DisplayRoomPanel extends MiniDialogPanel {
	Agent toServer;
	private JLabel title;
	private CustomScrollPane scroll_panel;
	ArrayList<Room> rooms;
	private MouseListener btn_l;
	
	public DisplayRoomPanel(Agent toServer) {
		this.toServer = toServer;		
		
		setLayout(new BorderLayout());
		
		JPanel header = new JPanel();
		JPanel inner = new JPanel();
		header.setLayout(new BorderLayout());
		
		JButton reloadBtn = new JButton("R");		
		reloadBtn.setFocusPainted(false);
		reloadBtn.setFont(ResourceLoader.H_FONT);
		reloadBtn.setBackground(Color.black);
		reloadBtn.setForeground(Color.white);
		reloadBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				inner.removeAll();
				reloadList();
			}
		});
		
		
		title = new JLabel("Rooms");
		title.setForeground(Color.white);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBackground(Color.black);
		title.setOpaque(true);
		title.setFont(ResourceLoader.H_FONT);
						
		header.add(title, BorderLayout.CENTER);
		header.add(reloadBtn, BorderLayout.EAST);		
		
		add(header, BorderLayout.NORTH);
		
		
		inner.setLayout(null);
		inner.setBackground(Color.white);
		inner.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		scroll_panel = new CustomScrollPane(inner, Color.white);
		add(scroll_panel, BorderLayout.CENTER);
		
		setSize(300, 500);
		setBackground(Color.white);
		
		btn_l = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {					
					dispatchDialogTaskComplete(rooms.get(((RoomItem)e.getSource()).getIndex()));
					disposeWindow();
				}			
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				((RoomItem)e.getSource()).setBackground(new Color(0xF5F5F5));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				((RoomItem)e.getSource()).setBackground(Color.white);
			}
		};				
		
		setOtherEvent();
		reloadList();
	}
	
	public void reloadList() {
		if(toServer == null || !toServer.isRunning()) return;
		
		toServer.send(PROMISE.ROOM_LIST, "");
	}
	
	public void setOtherEvent() {
		if(toServer == null || !toServer.isRunning()) return;
		
		toServer.addOtherComeInEvent(new DataComeInEvent() {
			
			@Override
			public void dispatch(Object source, String data) {
				StringTokenizer tokens = new StringTokenizer(data);
				switch(PROMISE.valueOf(tokens.nextToken())) {
				case ROOM_LIST:
					rooms = new ArrayList<>();
					break;
				case ROOM_ITEM:
					rooms.add(new Room(tokens.nextToken(), tokens.nextToken(), GameKind.valueOf(tokens.nextToken())));
					break;					
				case ROOM_LIST_END:
					reload();
					break;
				}
			}
		});
	}
	public void reload() {
		title.setText("Rooms (" + rooms.size() + ")");
		int posY = 10;		
		int i = 0;
		for(Room r : rooms) {
			RoomItem item = new RoomItem(r, i++, btn_l);
			item.setLocation(10, posY);
			scroll_panel.addInnerItem(item);
			posY = posY + 10 + item.getHeight();
		}
		scroll_panel.setInnerSize(new Dimension(300, posY));
	}
}
