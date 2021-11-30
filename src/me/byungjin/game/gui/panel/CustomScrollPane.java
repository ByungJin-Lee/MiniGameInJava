package me.byungjin.game.gui.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CustomScrollPane extends JScrollPane{
	private JPanel inner;
	private boolean isAdd;
	
	public CustomScrollPane(JPanel inner, Color bk) {
		super(inner);
		setBackground(bk);
		this.inner = inner;		
		JScrollBar v_scroll = new JScrollBar(JScrollBar.VERTICAL);		
		v_scroll.setBackground(bk);
		v_scroll.setPreferredSize(new Dimension(9,0));;
		v_scroll.setUI(new CustomScrollUI());
		v_scroll.addAdjustmentListener(new AdjustmentListener() {
		
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				if(isAdd) {
					e.getAdjustable().setValue(e.getAdjustable().getMaximum());
					isAdd = false;
				}
			}
		});
		setVerticalScrollBar(v_scroll);
		setHorizontalScrollBar(null);
		setBorder(null);
	}

	
	public void addInnerItem(Component c) {
		isAdd = true;
		inner.add(c);		
	}
	
	public void clearInnerItem() {
		inner.removeAll();
	}
	
	public void setInnerSize(Dimension d) {
		inner.setPreferredSize(d);
		inner.revalidate();
		inner.repaint();
	}
	
	class CustomScrollUI extends BasicScrollBarUI{
		public CustomScrollUI() {
			super();			
		}
		
		private final Dimension d = new Dimension(0,0);

		  @Override
		  protected JButton createDecreaseButton(int orientation) {
		    return new JButton() {
		      @Override
		      public Dimension getPreferredSize() {
		        return d;
		      }
		    };
		  }

		  @Override
		  protected JButton createIncreaseButton(int orientation) {
		    return new JButton() {
		      @Override
		      public Dimension getPreferredSize() {
		        return d;
		      }
		    };
		  }
		  
		 @Override
		 protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
			  
		 }
		  
		  @Override
		  protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
		    Graphics2D g2 = (Graphics2D) g.create();
		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		    Color color = null;
		    JScrollBar sb = (JScrollBar) c;		   
		    if (!sb.isEnabled() || r.width > r.height) {
		      return;
		    }else {
	//	    } else if (isDragging) {
	//	      color = Color.DARK_GRAY;
	//	    } else if (isThumbRollover()) {
	//	      color = Color.LIGHT_GRAY;
	//	    } else {
		      color = Color.LIGHT_GRAY;
		    }
		    g2.setPaint(color);
		    g2.fillRoundRect(r.x, r.y, r.width - 2, r.height, 10, 10);
//		    g2.setPaint(Color.WHITE);
//		    g2.drawRoundRect(r.x + 6, r.y, r.width - 8, r.height, 10, 10);
		    g2.dispose();
		  }
		}
}
