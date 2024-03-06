package geometry;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Point extends Shape{

	/**
	 * 
	 */
	private int x;
	private int y;
	
	public Point(){
		
	}
	
	public Point(int x, int y){
		this.x=x;
		this.y=y;
		
	}
	public Point(int x, int y, boolean selected){
		this(x,y);
		setSelected(selected);
		
	}
	public Point(int x, int y, Color edgeColor) {
		this.x = x;
		setY(y);
		this.setEdgeColor(edgeColor);
	}
	@Override
	public void moveBy(int byX, int byY) {
		this.x = this.x + byX;
		this.y += byY;
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof Point) {
			Point pocetak = new Point(0, 0);
			return (int) (this.distance(pocetak.getX(), pocetak.getY()) - ((Point) o).distance(pocetak.getX(), pocetak.getY()));
		}
		return 0;
	}
	

	@Override
	public boolean contains(int x, int y) {
		return this.distance(x, y) <= 3;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getEdgeColor());
		g.drawLine(this.x-2, this.y, this.x+2, this.y);
		g.drawLine(this.x, this.y-2, this.x, this.y+2);
		
		if(isSelected()){
			g.setColor(Color.BLUE);
			g.drawRect(this.x-3, this.y-3,6, 6);
		}
	}
	
	public double distance(int x2, int y2) {
		double dx = this.x-x2;
		double dy = this.y-y2;
		double d = Math.sqrt(dx*dx + dy*dy);
		return d;
	}
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point pomocna = (Point) obj;
			if (this.x == pomocna.x && this.y == pomocna.y) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	//geteri i seteri 
	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	
	public String toString() {
		// (x,y)
		return "Point = (" + x + ", " + y + ") , edgeColor= ("+ getEdgeColor().getRGB()+")";
	}

	public Point clone(Point p) {
		p.setX(this.getX());
		p.setY(this.getY());
		p.setEdgeColor(this.getEdgeColor());
		return p;
	}
}
