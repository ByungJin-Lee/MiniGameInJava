package me.byungjin.game.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import me.byungjin.db.DBConnection;
import me.byungjin.db.Rank;
import me.byungjin.game.GameKind;
import me.byungjin.game.Room;
import me.byungjin.game.gui.panel.BannerPanel;
import me.byungjin.game.gui.panel.ControlServerPanel;
import me.byungjin.game.gui.panel.InnerPanel;
import me.byungjin.game.gui.panel.MenuPanel;
import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.manager.NetworkManager;
import me.byungjin.manager.RoomManager;
import me.byungjin.manager.DBManager;
import me.byungjin.manager.SystemManager;
import me.byungjin.network.Agent;
import me.byungjin.network.Client;
import me.byungjin.network.PROMISE;
import me.byungjin.network.event.DataComeInEvent;

public class ServerWindow extends JFrame {
	public static Color INNER_BGCOLOR = new Color(244,247,252);	
	
	private Agent agent;
	private BannerPanel panel_banner;
	private InnerPanel panel_inner;
	private ControlServerPanel controlServerPanel;
	private DBConnection conn;
	private String cmdDetail;
	
	public ServerWindow() {		
		setSize(800, 600);
		setUndecorated(true);		
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));		
		
		JPanel panel_border = new JPanel();
		getContentPane().add(panel_border);		
		panel_border.setLayout(new BorderLayout(0, 0));				
		panel_border.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		JPanel panel_right = new JPanel();
		panel_right.setBorder(new EmptyBorder(0, 0, 5, 5));
		panel_right.setBackground(Color.WHITE);
		panel_border.add(panel_right, BorderLayout.CENTER);
		panel_right.setLayout(new BorderLayout(5, 0));
		
		//Control
		controlServerPanel = new ControlServerPanel();	
		panel_right.add(controlServerPanel, BorderLayout.NORTH);			
		
		//Banner
		panel_banner = new BannerPanel();
		panel_right.add(panel_banner, BorderLayout.WEST);
		
		//Container				
		JPanel panel_container = new JPanel();
		panel_container.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		panel_container.setBackground(INNER_BGCOLOR);
		panel_container.setLayout(new GridLayout(0, 1, 0, 0));
		panel_right.add(panel_container, BorderLayout.CENTER);				
		
		JPanel panel_padding = new JPanel();		
		panel_padding.setBackground(INNER_BGCOLOR);
		panel_padding.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		JScrollPane scroll = new JScrollPane(panel_padding); 
		scroll.setBorder(null);
		
		panel_container.add(scroll);
		panel_padding.setLayout(new GridLayout(0, 1, 0, 0));
		
		//Inner		
		panel_inner = new InnerPanel();		
		panel_inner.setBackground(INNER_BGCOLOR);		
		panel_padding.add(panel_inner);
		
		//Menu
		panel_border.add(new MenuPanel(panel_banner, panel_inner), BorderLayout.WEST);
		
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stopDB();
				stopServer();
			}
		});

	}
	
	public Agent getAgent(){
		return agent;
	}
	/**
	 * DB 연결 생성
	 * @return
	 */
	public boolean openDB() {
		DBManager.connectDB();
		conn = DBManager.getDBConn();
		if(conn!=null && conn.isConnect) {
			controlServerPanel.dbOpen();
			return true;
		}			
		return false;
	}
	/**
	 * 서버 생성
	 * @return
	 */
	public boolean openServer() {
		RoomManager.init();
		
		if(agent != null){
			agent.close();			
			if(makeServer()) {
				controlServerPanel.serverOpen();
				return true;
			}										
		}else{
			if(makeServer()) {
				controlServerPanel.serverOpen();
				return true;
			}
		}
		return false;
	}

	public boolean makeServer() {
		try{
			agent = NetworkManager.getServer();
			agent.addOtherComeInEvent(new DataComeInEvent() {
				@Override
				public void dispatch(Object source, String data) {
					command((Client)source, data);
				}
			});
			agent.open();
			controlServerPanel.serverOpen();
			return true;
		}catch(Exception e){
			SystemManager.catchException(ENVIRONMENT.GUI, e);
			controlServerPanel.serverClose();
			return false;
		}
	}
	/**
	 * 서버 중지
	 */
		
	public void stopServer(){		
		if(agent == null) return;

		if(agent.isRunning()){
			agent.close();
		}
		controlServerPanel.serverClose();
	}
	public void stopDB() {
		if(conn != null && conn.isConnect)
			conn.close();
		controlServerPanel.dbClose();
	}

	/**
	 * 서버 명령어
	 * @param source
	 * @param data
	 */
	public void command(Client source, String data){
		StringTokenizer tokens = new StringTokenizer(data);
		switch(PROMISE.valueOf(tokens.nextToken())){
			case LOGIN:
				send(source, login(tokens.nextToken(), tokens.nextToken()), cmdDetail);
				break;
			case REGISTER:
				send(source, register(tokens.nextToken(), tokens.nextToken()), cmdDetail);
				break;
			case ROOM:
				send(source, room(source.getSocketIP(), tokens.nextToken(), tokens.nextToken(), tokens.nextToken(), tokens.nextToken()), cmdDetail);
				break;
			case ROOM_LIST:
				sendRoomList(source);
				break;
			case ROOM_END:
				roomEnd(tokens.nextToken());
				break;
			case RANK:
				send(source, rank(tokens.nextToken(), GameKind.valueOf(tokens.nextToken())), cmdDetail);
				break;
			case RANK_UPDATE:
				rank_update(new Rank(tokens.nextToken(), GameKind.valueOf(tokens.nextToken()), Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken())));
				break;
			default:
				break;
		}
	}

	private PROMISE login(String id, String pw){
		cmdDetail = "";
		if(conn == null || !conn.isConnect) return PROMISE.LOGIN_FAIL;

		if(conn.confirmUser(id, pw))
			return PROMISE.LOGIN_SUC;
		return PROMISE.LOGIN_FAIL;
	}
	
	private PROMISE rank(String id, GameKind k) {
		cmdDetail = "";
		if(conn == null || !conn.isConnect) return PROMISE.RANK_FAIL;
		
		Rank rank;
		if((rank = conn.getRank(id, k)) != null) {
			cmdDetail = rank.toString();
			return PROMISE.RANK_RETURN;
		}
		return PROMISE.RANK_FAIL;
	}
	
	private void rank_update(Rank r) {
		cmdDetail = "";
		if(conn == null || !conn.isConnect) return;
						
		conn.updateRank(r);
	}
	
	private void sendRoomList(Client source) {		
		try {
			Thread room_send_thread = new Thread(new Runnable() {
				@Override
				public void run() {				
					String hostIp = source.getSocketIP();					
					
					send(source, PROMISE.ROOM_LIST, "");
					for(Room r : RoomManager.getRoomVector()) {
						if(r.isLan(hostIp)) {
							System.out.println("LAN");
							send(source, PROMISE.ROOM_ITEM, r.toItemStrLan());
						}else {							
							send(source, PROMISE.ROOM_ITEM, r.toItemStr());
						}
					}
					send(source, PROMISE.ROOM_LIST_END, "");
				}
			});
			room_send_thread.start();
		}catch(Exception e) {
			SystemManager.catchException(ENVIRONMENT.SERVER, e);
		}	
	}
	
	private PROMISE room(String ip, String innerIp, String name, String kind, String pw) {
		if(ip.equals("172.30.1.254")) //Server GateWay IP
			ip = "121.146.245.59";		
		Room newRoom;
		if(pw.equals("!!!!!"))
			newRoom = new Room(ip, name, GameKind.valueOf(kind));
		else
			newRoom = new Room(ip, name, pw, GameKind.valueOf(kind));
		newRoom.setInnerIp(innerIp);
		if(RoomManager.add(newRoom)) {
			cmdDetail = "방 생성 완료";
			return PROMISE.ROOM_SUC;
		}else {
			cmdDetail = "같은 이름의 방이 존재함";
			return PROMISE.ROOM_FAIL;
		}		
	}
	
	private void roomEnd(String name) {
		RoomManager.remove(new Room(null, name, null));
	}
	
	private PROMISE register(String id, String pw){
		cmdDetail = "서버 오류!";
		if(conn == null || !conn.isConnect) return PROMISE.REGISTER_FAIL;

		if(conn.isUser(id)){
			cmdDetail = "같은 ID가 존재함.";
			return PROMISE.REGISTER_FAIL;
		}

		if(conn.registerUser(id, pw)){
			cmdDetail = "";
			return PROMISE.REGISTER_SUC;
		}
		cmdDetail = "서버 오류!";
		return PROMISE.REGISTER_FAIL;
	}

	private void send(Client target, PROMISE tag, String data){
		if(target.isRunning())
			target.send(tag, data);
	}	
	
	public boolean isServerRunning() {
		if(agent != null && agent.isRunning())
			return true;
		return false;
	}
}
