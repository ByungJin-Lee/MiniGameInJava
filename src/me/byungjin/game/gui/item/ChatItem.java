package me.byungjin.game.gui.item;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import me.byungjin.manager.SystemManager;
import resource.ResourceLoader;

public class ChatItem extends JPanel {	
	private boolean isMy;	
	static private FontRenderContext r_content = new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT, RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
	
	public ChatItem(String text, boolean isMine, Color bk) {		
		this.isMy = isMine;		
		setBackground(bk);
		setLayout(new BorderLayout());
		JLabel jTime = new JLabel(SystemManager.getTime());
		jTime.setFont(ResourceLoader.DEFAULT_FONT12);
		
		JTextArea innerLabel = new JTextArea(text);		
		innerLabel.setFont(ResourceLoader.DEFAULT_FONT14);				
		innerLabel.setEditable(false);				
		innerLabel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.LIGHT_GRAY), BorderFactory.createEmptyBorder(8, 8, 8, 8)));
		if(isMy) {
			jTime.setHorizontalAlignment(SwingConstants.RIGHT);			
			innerLabel.setBackground(Color.GREEN);
			innerLabel.setForeground(Color.black);
		}else {
			innerLabel.setBackground(Color.WHITE);
		}	
						
		add(jTime, BorderLayout.NORTH);
		add(innerLabel, BorderLayout.CENTER);					
		setSize(Math.max((int)ResourceLoader.DEFAULT_FONT14.getStringBounds(text, r_content).getWidth() + 17, 77), 50);		
	}
	
	public boolean isMine() {
		return this.isMy;
	}
}
