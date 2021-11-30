package me.byungjin.game.gui.panel;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import me.byungjin.game.GameKind;
import me.byungjin.game.Room;
import me.byungjin.game.gui.MiniDialogPanel;
import me.byungjin.game.gui.item.PlaceHolderTextField;
import resource.ResourceLoader;

public class CreateRoomPanel extends MiniDialogPanel {
	private PlaceHolderTextField txt_name;
	private PlaceHolderTextField txt_password;
	private JLabel warningsLabel;
	private GameKind kind = GameKind.OMOK;
	
	
	public CreateRoomPanel() {
		setBorder(BorderFactory.createEmptyBorder(40,45,20,45));
		
		txt_name = new PlaceHolderTextField(15, "Enter Room Name");
		txt_password = new PlaceHolderTextField(15, "Null or Password");
		
		JLabel title = new JLabel("Room Setting");
		title.setForeground(Color.white);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBackground(Color.black);
		title.setOpaque(true);
		title.setFont(ResourceLoader.H_FONT);
		
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
		
		JButton createBtn = new JButton("Create");
		createBtn.setFocusPainted(false);
		createBtn.setFont(ResourceLoader.H_FONT);
		createBtn.setBackground(Color.black);
		createBtn.setForeground(Color.white);
		createBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				create();
			}
		});
		
		warningsLabel = new JLabel("");
		warningsLabel.setFont(ResourceLoader.DEFAULT_FONT12);
		warningsLabel.setForeground(Color.red);
		
		setLayout(new GridLayout(5, 1, 0, 5));
		
		add(title);
		add(txt_name);		
		add(game_kind);
		add(createBtn);
		add(warningsLabel);
		
		setSize(300, 250);
		setBackground(Color.white);
	}
	
	public void create() {
		String name = txt_name.getInnerText().trim();
		
		if(name.length() < 10 || name.length() > 15) {
			message("방 이름이 올바르지 않음(10~15자 이내)");
			return;
		}		
		
		if(kind == GameKind.NOT_YET) {
			message("올바르지 못한 게임 종목");
			return;
		}
		
		String pw = txt_password.getInnerText().trim();
		
		if(name.indexOf(" ") != -1 || (pw.indexOf(" ") != -1 && pw.length() != 0)) {
			message("띄어쓰기는 허용되지 않습니다.");
			return;
		}
		
		
		if(pw.length() == 0)
			dispatchDialogTaskComplete(new Room(null, name, kind));
		else
			dispatchDialogTaskComplete(new Room(null, name, pw, kind));		
		disposeWindow();
	}
	
	public void message(String str) {
		warningsLabel.setText(str);
	}
}
