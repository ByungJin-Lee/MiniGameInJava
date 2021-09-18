package me.byungjin.game;

public class Point {
	private int x, y;
	
	public Point() {
		this(0,0);
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public boolean isSame(int x, int y) {
		if(this.x == x && this.y == y)
			return true;
		return false;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	@Override
	public String toString() {
		return "Point " + x + " " + y;
	
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Point) {
			Point p = (Point)obj;
			if(p.x == x && p.y == y)
				return true;
		}
		return false;
	}
}
