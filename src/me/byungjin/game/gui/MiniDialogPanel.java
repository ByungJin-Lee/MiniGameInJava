package me.byungjin.game.gui;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public abstract class MiniDialogPanel extends JPanel {
	
	public void disposeWindow() {
		((JDialog)SwingUtilities.getAncestorOfClass(MiniDialog.class, this)).dispose();
	}
}
