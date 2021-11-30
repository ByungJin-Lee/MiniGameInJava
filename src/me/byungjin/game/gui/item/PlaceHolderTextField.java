package me.byungjin.game.gui.item;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import resource.ResourceLoader;

public class PlaceHolderTextField extends JTextField implements FocusListener {
	private String placedText;
	
	public PlaceHolderTextField(int col, String placedText) {
		super(col);
		this.placedText = placedText;
		setBorder(BorderFactory.createCompoundBorder(getBorder(), BorderFactory.createEmptyBorder(5, 7, 5, 7)));
		setFont(ResourceLoader.DEFAULT_FONT);
		setForeground(Color.LIGHT_GRAY);
		setText(placedText);
		addFocusListener(this);
	}
	
	public String getInnerText() {
		if(getText().equals(placedText))
			return "";
		else
			return getText();
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		if(getText().equals(placedText))
			setText("");
		setForeground(Color.DARK_GRAY);
	}
	
	@Override
	public void focusLost(FocusEvent e) {
		if(getText().isEmpty())
			setText(placedText);
		setForeground(Color.LIGHT_GRAY);
	}
}
