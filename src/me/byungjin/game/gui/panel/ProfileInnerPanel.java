package me.byungjin.game.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import me.byungjin.db.UserSchema;
import me.byungjin.game.GameKind;
import me.byungjin.network.Agent;
import me.byungjin.network.PROMISE;
import me.byungjin.network.event.DataComeInEvent;
import resource.ResourceLoader;

public class ProfileInnerPanel extends JPanel {
	private Agent toServer;
	private GameKind kind;
	private UserSchema user;	
	
	public ProfileInnerPanel(UserSchema user) {
		this.user = user;						
		
		setBackground(Color.white);
		setLayout(new GridLayout(3, 1));
		setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		
		JLabel label_user = new JLabel("ID - " + user.getId());
		label_user.setFont(ResourceLoader.DEFAULT_FONT);
		JLabel label_password = new JLabel("PW - " + user.getPassword());
		label_password.setFont(ResourceLoader.DEFAULT_FONT);
		
		add(label_user);
		add(label_password);
		
		setSize(300, 150);
		
		JPanel game_kind = new JPanel();
		game_kind.setLayout(new BorderLayout(5, 0));
		game_kind.setBackground(Color.white);
		
		JLabel g_label = new JLabel("¿À¸ñ");
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
	}
	
	public void getRankFromServer() {
		toServer.addOtherComeInEvent(new DataComeInEvent() {			
			@Override
			public void dispatch(Object source, String data) {
				StringTokenizer tokens = new StringTokenizer(data);
				switch(PROMISE.valueOf(tokens.nextToken())) {
				case RANK_RETURN:
					
					break;
				}
			}
		});
		toServer.send(PROMISE.RANK, user.getId() + " " + kind);		
	}

}
