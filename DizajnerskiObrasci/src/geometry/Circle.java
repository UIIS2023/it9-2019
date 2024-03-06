package geometry;

import java.awt.Graphics;
import java.awt.Color;

@SuppressWarnings("serial")
public class Circle extends Shape{

	/**
	 * 
	 */
	private Point center;
	private int radius;
	
	//konstruktori
	public Circle() {
		
	}
	
	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}
	
	public Circle(Point center, int radius, boolean selected) {
		this(center, radius);
		this.setSelected(selected);
	}
	public Circle(Point center, int radius, Color edgeColor, Color innerColor) {
		this.center = center;
		this.radius = radius;
		setEdgeColor(edgeColor);
		setInnerColor(innerColor);
	}
	
	public Circle(Point center, int radius, boolean selected, Color edgeColor) {
		this(center, radius, selected);
		this.setEdgeColor(edgeColor);
	}
	
	public Circle(Point center, int radius, boolean selected, Color edgeColor, Color innerColor) {
		this(center, radius, selected, edgeColor);
		this.setInnerColor(innerColor);
	}
	
	
	@Override
	public void moveBy(int byX, int byY) {
		this.center.moveBy(byX, byY);
		
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof Circle) {
			return this.radius - ((Circle) o).radius;
		}
		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		return center.distance(x, y) <= radius;
	}
	
	public boolean contains(Point p) {
		return center.distance(p.getX(), p.getY()) <= radius;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getEdgeColor());
		g.drawOval(this.center.getX() - this.radius, this.center.getY() - this.radius, this.radius*2, this.radius*2);
		this.fill(g);
		
		if(isSelected()){
			selectCircle(g);
			/*g.setColor(Color.BLUE);
			g.drawRect(this.center.getX()-3, this.center.getY()-3, 6, 6);
			g.drawRect(this.center.getX()-radius-3, this.center.getY()-3, 6, 6);
			g.drawRect(this.center.getX()+radius-3, this.center.getY()-3, 6, 6);
			g.drawRect(this.center.getX()-3, this.center.getY()-radius-3, 6, 6);
			g.drawRect(this.center.getX()-3, this.center.getY()+radius-3, 6, 6);*/
		}
		
	}
	
	public void selectCircle(Graphics g){
		g.setColor(Color.BLUE);
		g.drawRect(this.center.getX()-3, this.center.getY()-3, 6, 6);
		g.drawRect(this.center.getX()-radius-3, this.center.getY()-3, 6, 6);
		g.drawRect(this.center.getX()+radius-3, this.center.getY()-3, 6, 6);
		g.drawRect(this.center.getX()-3, this.center.getY()-radius-3, 6, 6);
		g.drawRect(this.center.getX()-3, this.center.getY()+radius-3, 6, 6);
		
	}
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillOval(this.center.getX() - this.radius + 1, this.center.getY() - this.radius + 1,
				this.radius*2 - 2, this.radius*2 - 2);
		
	}
	public boolean equals(Object obj) {
		if (obj instanceof Circle) {
			Circle pomocni = (Circle) obj;
			if (this.center.equals(pomocni.center) &&
					this.radius == pomocni.radius) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	public double area() {
		return radius * radius * Math.PI;
	}
	
	public Point getCenter() {
		return center;
	}
	public void setCenter(Point center) {
		this.center = center;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) throws Exception {
		if (radius >= 0) {
			this.radius = radius;
		} else {
			throw new NumberFormatException("Radius has to be a value greater then 0!");
		}
	}
	
	public String toString() {
		// Center=(x,y), radius= radius
		return "Circle = (" + center.getX()+", "+center.getY()+") , radius= " + radius+ " , edgeColor= ("+ getEdgeColor().getRGB()+") , innerColor= ("+getInnerColor().getRGB()+")"; 
	}
	public Circle clone(Circle c){
		c.getCenter().setX(this.getCenter().getX());
		c.getCenter().setY(this.getCenter().getY());
		try {
			c.setRadius(this.getRadius());
		} catch (Exception e) {
			
			throw new NumberFormatException("Raius has to be greater than 0");
		}
		c.setEdgeColor(this.getEdgeColor());
		c.setInnerColor(this.getInnerColor());
		
		
		return c;
	}

}
