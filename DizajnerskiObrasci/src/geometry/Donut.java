package geometry;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;


@SuppressWarnings("serial")
public class Donut extends Circle{

	
	private int innerRadius;
	
	public Donut() {

	}

	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius); 
		this.innerRadius = innerRadius;
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		this.setSelected(selected);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color edgeColor) {
		this(center, radius, innerRadius, selected);
		this.setEdgeColor(edgeColor);
	}
	
	public Donut(Point center, int radius, int innerRadius, Color edgeColor, Color innerColor) {
		super(center, radius, edgeColor, innerColor);
		this.innerRadius = innerRadius;
		setEdgeColor(edgeColor);
		setInnerColor(innerColor);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color edgeColor, Color innerColor) {
		this(center, radius, innerRadius, selected, edgeColor);
		this.setInnerColor(innerColor);
	}
	
	
	public int compareTo(Object o) {
		if (o instanceof Donut) {
			return (int) (this.area() - ((Donut) o).area());
		}
		return 0;
	}
	
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		super.fill(g);
		g.setColor(Color.white); //ovde stavljamo boju unutrasnjeg kruga
		g.fillOval(this.getCenter().getX()-this.innerRadius, this.getCenter().getY()-this.innerRadius,
				this.innerRadius*2, this.innerRadius*2);
	}
	
	public void draw(Graphics g) {
		/*super.draw(g);
		g.setColor(getEdgeColor());
		g.drawOval(this.getCenter().getX() - this.innerRadius, this.getCenter().getY() - this.innerRadius,
				this.innerRadius * 2, this.innerRadius * 2);*/
		
		Ellipse2D bigCircle = new Ellipse2D.Double(this.getCenter().getX() - this.getRadius(), this.getCenter().getY() - this.getRadius(), this.getRadius()*2, this.getRadius()*2);
		Ellipse2D smallCircle = new Ellipse2D.Double(this.getCenter().getX() - this.getInnerRadius(), this.getCenter().getY() - this.getInnerRadius(), this.getInnerRadius()*2, this.getInnerRadius()*2);
		Area bigArea = new Area(bigCircle);
		bigArea.subtract(new Area(smallCircle));
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(getInnerColor());
		g2d.fill(bigArea);
		g2d.setColor(getEdgeColor());
		g2d.draw(bigArea);
		
		if (isSelected()) 
			super.selectCircle(g);
	}
	
	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}

	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return super.contains(x, y) && dFromCenter > innerRadius;
	}

	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().distance(p.getX(), p.getY());
		return super.contains(p.getX(), p.getY()) && dFromCenter > innerRadius;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut pomocni = (Donut) obj;
			if (this.getCenter().equals(pomocni.getCenter()) && this.getRadius() == pomocni.getRadius()
					&& this.innerRadius == pomocni.innerRadius) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public int getInnerRadius() {
		return innerRadius;
	}

	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}

	public String toString() {
		return "Donut = (" + this.getCenter().getX()+", "+this.getCenter().getY()+") , radius= " + this.getRadius() + " , innerRadius= " + innerRadius+ " , edgeColor= ("+ getEdgeColor().getRGB()+ ") , innerColor= ("+getInnerColor().getRGB()+")";
	}
	
	public Donut clone(Donut d){
		d.getCenter().setX(this.getCenter().getX());
		d.getCenter().setY(this.getCenter().getY());
		d.setEdgeColor(this.getEdgeColor());
		d.setInnerColor(this.getInnerColor());
		try {
			d.setInnerRadius(this.getInnerRadius());
			
		} catch (Exception e) {
			throw new NumberFormatException("Inner radius has to be greater then 0!");
		}
		try {
			d.setRadius(this.getRadius());
			
		} catch (Exception e) {
			throw new NumberFormatException("Radius has to be greater then 0!");
		}
		
		return d;
	}
	
}
