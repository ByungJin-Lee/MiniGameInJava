package me.byungjin.game.gui.page;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import me.byungjin.db.LogSchema;
import me.byungjin.manager.DBManager;
import resource.ResourceLoader;

public class TotalLogPage extends JTable {
	public TotalLogPage() {		
		DefaultTableModel model = new DefaultTableModel(LogSchema.getHeader(), 0);
		setModel(model);
		setFont(ResourceLoader.DEFAULT_FONT);
		Vector<String> row;
		ArrayList<LogSchema> logs = DBManager.getLog(); 
		
		if(logs == null) return;
		
		for(LogSchema l : logs) {
			row = new Vector<>();
			row.add(l.getSource());
			row.add(Boolean.toString(l.isWarning()));
			row.add(l.getContent());
			row.add(l.getTime().toLocalTime().toString());
			model.addRow(row);
		}		
		resizeColumnWidth();
	}
	public void resizeColumnWidth(){ 
		final TableColumnModel columnModel = getColumnModel(); 
		for (int column = 0, columCount = getColumnCount(); column < columCount; column++) { 
			int width = 50; // Min width 
			for (int row = 0, rowCount = getRowCount(); row < rowCount; row++) { 
				TableCellRenderer renderer = getCellRenderer(row, column); 
				Component comp = prepareRenderer(renderer, row, column); 
				width = Math.max(comp.getPreferredSize().width +1 , width); 
			} 
			columnModel.getColumn(column).setPreferredWidth(width); 			
		}		
	}
	
}
