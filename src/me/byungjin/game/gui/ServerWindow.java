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
import me.byungjin.game.gui.panel.BannerPanel;
import me.byungjin.game.gui.panel.ControlServerPanel;
import me.byungjin.game.gui.panel.InnerPanel;
import me.byungjin.game.gui.panel.MenuPanel;
import me.byungjin.manager.ENVIRONMENT;
import me.byungjin.manager.NetworkManager;
import me.byungjin.manager.SystemManager;
import me.byungjin.network.Agent;
import me.byungjin.network.Client;
import me.byungjin.network.PROMISE;
import me.byungjin.network.event.DataComeInEvent;

public class ServerWindow extends JFrame {
	private Agent agent;
	private BannerPanel panel_banner;
	private InnerPanel panel_inner;
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
					
		panel_right.add(new ControlServerPanel(), BorderLayout.NORTH);			
		
		//Banner
		panel_banner = new BannerPanel();
		panel_right.add(panel_banner, BorderLayout.WEST);
		
		//Container
		Color color_container = new Color(244,247,252);
		
		JPanel panel_container = new JPanel();
		panel_container.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		panel_container.setBackground(color_container);
		panel_container.setLayout(new GridLayout(0, 1, 0, 0));
		panel_right.add(panel_container, BorderLayout.CENTER);				
		
		JPanel panel_padding = new JPanel();		
		panel_padding.setBackground(color_container);
		panel_padding.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		JScrollPane scroll = new JScrollPane(panel_padding); 
		scroll.setBorder(null);
		
		panel_container.add(scroll);
		panel_padding.setLayout(new GridLayout(0, 1, 0, 0));
		
		//Inner		
		panel_inner = new InnerPanel();		
		panel_inner.setBackground(color_container);		
		panel_padding.add(panel_inner);
		
		//Menu
		panel_border.add(new MenuPanel(panel_banner, panel_inner), BorderLayout.WEST);
		
		setVisible(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stopServer();
			}
		});

	}
	
	public Agent getAgent(){
		return agent;
	}

	/**
	 * 서버 생성
	 * @return
	 */
	public boolean openServer(){
		SystemManager.connectDB();
		conn = SystemManager.getDBConn();
		if(agent != null){
			if(!agent.isRunning())
				agent.open();
			return true;
		}else{
			try{
				agent = NetworkManager.getServer();
				agent.addOtherComeInEvent(new DataComeInEvent() {
					@Override
					public void dispatch(Object source, String data) {
						command((Client)source, data);
					}
				});
				agent.open();
				return true;
			}catch(Exception e){
				SystemManager.catchException(ENVIRONMENT.GUI, e);
				return false;
			}
		}
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

	/**
	 * 서버 중지
	 */
	public void stopServer(){
		if(agent == null) return;

		if(agent.isRunning()){
			agent.block();
		}
	}
}
