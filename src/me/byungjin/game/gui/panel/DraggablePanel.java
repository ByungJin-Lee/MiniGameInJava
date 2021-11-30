package me.byungjin.game.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import resource.ResourceLoader;

public class DraggablePanel extends JPanel implements MouseMotionListener, MouseListener {
	private Point pressedPoint;	
	private JPanel panel;
	private JPanel drag_control;
	final int CONTROL_HEIGHT = 35;
	
	public DraggablePanel(String name, JPanel inner) {
		super();
		
		setLayout(new BorderLayout());	
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		drag_control = new JPanel();
		drag_control.setBackground(Color.white);
		drag_control.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel title = new JLabel(name);
		title.setForeground(Color.DARK_GRAY);	
		title.setFont(ResourceLoader.H_FONT);
		drag_control.add(title);		
		drag_control.setCursor(new Cursor(Cursor.MOVE_CURSOR));
		drag_control.addMouseListener(this);
		drag_control.addMouseMotionListener(this);
		
		add(drag_control, BorderLayout.NORTH);
		
		if(inner != null) {
			drag_control.setSize(inner.getWidth(), CONTROL_HEIGHT);
			
			setInner(inner);
			
			setSize(inner.getWidth(), inner.getHeight() + CONTROL_HEIGHT);
		}
	}
	
	public void setInner(JPanel inner) {
		panel = inner;
		add(panel, BorderLayout.CENTER);	
	}
	
	public void resize() {
		drag_control.setSize(panel.getWidth(), CONTROL_HEIGHT);
		setSize(panel.getWidth() + 2, panel.getHeight() + CONTROL_HEIGHT);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {	
		int x = getX() + e.getX() - pressedPoint.x;
		int y = getY() + e.getY() - pressedPoint.y;
		
		if(x < 0 || x > 1200 || y < 0 || y >  750)
			return;
		setLocation(x, y);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		pressedPoint = e.getPoint();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}	
}
