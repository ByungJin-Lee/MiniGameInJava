package me.byungjin.game.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import me.byungjin.game.gui.listener.MiniDialogWorkListener;
import me.byungjin.game.gui.panel.ControlDialogPanel;

public class MiniDialog extends JDialog {
	private MiniDialogWorkListener dialogWorkComplete;
	
	public MiniDialog(JPanel content, String text, MiniDialogWorkListener l) {
		setModal(true);
		this.dialogWorkComplete = l;
		setUndecorated(true);		
		setLayout(new GridLayout(1,1));		
		setMinimumSize(new Dimension(200,100));
		setLocationRelativeTo(null);
		
		JPanel container = new JPanel();		
		container.setBorder(new LineBorder(Color.LIGHT_GRAY));		
		container.setLayout(new BorderLayout());
		
		container.add(new ControlDialogPanel(text), BorderLayout.NORTH);
		
		
		container.add(content, BorderLayout.CENTER);
		add(container);
		setSize(content.getWidth(), content.getHeight() + 30);
		setVisible(true);
	}	
	
	public void dispatchCompleteEvent(Object obj) {
		if(dialogWorkComplete != null)
			dialogWorkComplete.completeDialogTask(obj);
	}
}
