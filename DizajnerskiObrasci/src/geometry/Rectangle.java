package geometry;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Rectangle extends Shape {

	/**
	 * 
	 */

	private Point upperLeftPoint;
	private int width;
	private int height;
	
public Rectangle() {
		
	}
	
	public Rectangle(Point upperLeftPoint, int height, int width) {
		this.upperLeftPoint = upperLeftPoint;
		this.width = width;
		this.height = height;
	}
	
	public Rectangle(Point upperLeftPoint,  int height, int width, boolean selected) {
		this(upperLeftPoint, height, width);
		this.setSelected(selected);
	}
	
	public Rectangle(Point upperLeftPoint, int height, int width, Color edgeColor, Color innerColor) {
		this.upperLeftPoint = upperLeftPoint;
		setHeight(height);
		setWidth(width);
		setEdgeColor(edgeColor);
		setInnerColor(innerColor);
	}
	
	public Rectangle(Point upperLeftPoint, int height, int width, boolean selected, Color edgeColor) {
		this(upperLeftPoint, height, width,  selected);
		this.setEdgeColor(edgeColor);
	}
	
	public Rectangle(Point upperLeftPoint, int height, int width, boolean selected, Color edgeColor, Color innerColor) {
		this(upperLeftPoint, height, width, selected, edgeColor);
		this.setInnerColor(innerColor);
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		this.upperLeftPoint.moveBy(byX, byY);	
		
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Rectangle) {
			return this.area() - ((Rectangle) o).area();
		}
		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		if(upperLeftPoint.getX() <= x &&
				upperLeftPoint.getY() <= y &&
				x <= upperLeftPoint.getX() + width &&
				y <= upperLeftPoint.getY() + height) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean contains(Point p) {
		if(upperLeftPoint.getX() <= p.getX() &&
				upperLeftPoint.getY() <= p.getY() &&
				p.getX() <= upperLeftPoint.getX() + width &&
				p.getY() <= upperLeftPoint.getY() + height) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getEdgeColor());
		g.drawRect(this.upperLeftPoint.getX(), this.upperLeftPoint.getY(), this.width, this.height);
		g.setColor(getInnerColor());
		g.fillRect(this.getUpperLeftPoint().getX()+1, this.getUpperLeftPoint().getY()+1, this.width-1, this.height-1);
		
		if(isSelected()){
			g.setColor(Color.BLUE);
			g.drawRect(this.upperLeftPoint.getX()-3,this.upperLeftPoint.getY()-3, 6, 6);
			g.drawRect(this.upperLeftPoint.getX()-3,this.upperLeftPoint.getY()+this.height-3, 6, 6);
			g.drawRect(this.upperLeftPoint.getX()+this.width-3,this.upperLeftPoint.getY()-3, 6, 6);
			g.drawRect(this.upperLeftPoint.getX()+this.width-3,this.upperLeftPoint.getY()+this.height-3, 6, 6);
			//g.drawRect(this.upperLeftPoint.getX()+ this.width/2 -3,this.upperLeftPoint.getY()+this.height/2 - 3 , 6, 6);
		}
	}
	
	/*public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillRect(this.upperLeftPoint.getX()+1, this.upperLeftPoint.getY()+1, this.width-1, this.height-1);
		
	}*/
	public boolean equals(Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle r = (Rectangle) obj;
			if (this.upperLeftPoint.equals(r.getUpperLeftPoint()) && this.height == r.getHeight()
					&& this.width == r.getWidth()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public int area() {
		return width * height;
	}
	
	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}
	public void setUpperLeftPoint(Point upperLeftPoint) {
		this.upperLeftPoint = upperLeftPoint;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public String toString() {
		// Upper Left Point=(x,y), width=width, height=height
		return "Rectangle = (" + upperLeftPoint.getX()+", " +upperLeftPoint.getY()+ ") , height= " + height + " , width= " + width+ " , edgeColor= ("+getEdgeColor().getRGB()+
				") , innerColor= ("+getInnerColor().getRGB()+")";
	}

	public Rectangle clone(Rectangle r) {
		r.getUpperLeftPoint().setX(this.getUpperLeftPoint().getX());
		r.getUpperLeftPoint().setY(this.getUpperLeftPoint().getY());
		r.setHeight(this.getHeight());
		r.setWidth(this.getWidth());
		r.setEdgeColor(this.getEdgeColor());
		r.setInnerColor(this.getInnerColor());
		
		return r;
	}
}
