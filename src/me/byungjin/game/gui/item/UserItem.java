package me.byungjin.game.gui.item;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import me.byungjin.db.Rank;
import resource.ResourceLoader;

public class UserItem extends JPanel {
	public UserItem(Rank rank) {
		setBackground(Color.white);
		setLayout(new BorderLayout());
		JLabel imgLabel = new JLabel(ResourceLoader.DEFAULT_USER);
		JLabel id_label = new JLabel(rank.getId());
		id_label.setFont(ResourceLoader.DEFAULT_FONT);
		id_label.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel info = new JPanel();
		info.setLayout(new GridLayout(2,1));
		info.add(id_label);
		JLabel r_label = new JLabel("V : " +rank.getVictory() + " - L : " + rank.getLose());
		r_label.setFont(ResourceLoader.DEFAULT_FONT12);
		r_label.setHorizontalAlignment(SwingConstants.CENTER);
		info.add(r_label);
		add(imgLabel, BorderLayout.CENTER);
		add(info, BorderLayout.SOUTH);		
	}
}
