package me.byungjin.game.gui.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import me.byungjin.game.gui.item.Item;
import me.byungjin.game.gui.item.MenuItem;

public class ItemMouseListener extends MouseAdapter {
	private Item selectedItem;
	
	@Override
	public void mouseEntered(MouseEvent e) {		
		((Item)e.getSource()).hover();		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		Item item = (Item)e.getSource(); 
		if(selectedItem == null || item != selectedItem)
			item.unhover();		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		Item curSelected = (Item)e.getSource();
		curSelected.select();		
		if(selectedItem != null && selectedItem != curSelected) 			
			selectedItem.unhover();								
		selectedItem = curSelected;
	}
}
