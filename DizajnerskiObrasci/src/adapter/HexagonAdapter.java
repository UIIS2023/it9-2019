package adapter;

import java.awt.Color;
import java.awt.Graphics;

import geometry.Point;
import geometry.Shape;
import hexagon.Hexagon;

@SuppressWarnings("serial")
public class HexagonAdapter extends Shape{
	
	/**
	 * 
	 */
	private Hexagon hexagon;
	

	public HexagonAdapter() {
		
	}
	
	public HexagonAdapter(Point center, int radius) {
		this.hexagon = new Hexagon(center.getX(), center.getY(), radius);
	}

	public HexagonAdapter(Point center, int radius, boolean selected) {
		this(center, radius);
		this.hexagon.setSelected(selected);
	}

	public HexagonAdapter(Point center, int radius, boolean selected, Color edgeColor) {
		this(center, radius, selected);
		this.hexagon.setBorderColor(edgeColor);
	}

	public HexagonAdapter(Point center, int radius, boolean selected, Color edgeColor, Color innerColor) {
		this(center, radius, selected, edgeColor);
		this.hexagon.setAreaColor(innerColor);
	}
	

	public HexagonAdapter(Point center, int radius, Color edgeColor, Color innerColor) {
		this.hexagon = new Hexagon(center.getX(), center.getY(), radius);
		this.hexagon.setBorderColor(edgeColor);
		this.hexagon.setAreaColor(innerColor);
	}

	
	

	public Hexagon getHexagon() {
		return hexagon;
	}

	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}

	@Override
	public void moveBy(int byX, int byY) {
		hexagon.setX(hexagon.getX() + byX);
		hexagon.setY(hexagon.getY() + byY);
		
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Hexagon) {
			return (this.hexagon.getR() - ((Hexagon) o).getR());
		}
		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
	}

	@Override
	public void draw(Graphics g) {
		this.hexagon.paint(g);
		
	}
	
	public boolean equals(Object o){
		
		if(o instanceof HexagonAdapter){
			HexagonAdapter hexAdapter = (HexagonAdapter) o;
			Point p1 = new Point(hexagon.getX(),hexagon.getY());
			Point p2 = new Point(hexAdapter.hexagon.getX(),hexAdapter.hexagon.getY());
			if(p1.equals(p2) && hexagon.getR() == hexAdapter.getHexagon().getR())
				return true;
			else
				return false;

		}
		else
			return false;
	}
	public int getX(){
		return hexagon.getX();
	}
	public int getY(){
		return hexagon.getY();
	}
	public void setX(int x){
		 hexagon.setX(x);
	}
	public void setY(int y){
		 hexagon.setY(y);
	}
	
	
	public boolean isSelected() {
		return hexagon.isSelected();
	}
	
	public void setSelected(boolean selected) {
		this.hexagon.setSelected(selected);
	}

	public Point getCenter() {
		return new Point(hexagon.getX(), hexagon.getY());
	}
	
	public void setCenter(Point center) {
		hexagon.setX(center.getX());
		hexagon.setY(center.getY());
	}

	public int getRadius() {
		return this.hexagon.getR();
	}

	public void setRadius(int radius) {
		this.hexagon.setR(radius);
	}

	public Color getHexagonEdgeColor() {
		return this.hexagon.getBorderColor();
	}

	public void setHexagonEdgeColor(Color edgeColor) {
		this.hexagon.setBorderColor(edgeColor);
	}
	public Color getHexagonInnerColor() {
		return this.hexagon.getAreaColor();
	}

	public void setHexagonInnerColor(Color innerColor) {
		this.hexagon.setAreaColor(innerColor);
	}

	public String toString() {
		return "Hexagon = (" + getCenter().getX() + ", " + getCenter().getY() + ") , radius= "
				+ getRadius() + " , edgeColor= (" + getHexagonEdgeColor().getRGB()+ ") , innerColor= (" + getHexagonInnerColor().getRGB() + ")";
	}
	

	public HexagonAdapter clone() {
		HexagonAdapter hexagon=new HexagonAdapter(new Point(this.getX(), this.getY()), this.getRadius()	, this.getHexagonEdgeColor(), this.getHexagonInnerColor());
		/*h.getCenter().setX(this.getCenter().getX());
		h.getCenter().setY(this.getCenter().getY());
		h.setHexagonEdgeColor(this.getHexagonEdgeColor());
		h.setHexagonInnerColor(this.getHexagonInnerColor());
		h.setRadius(this.getRadius());
		
		return h;*/
		return hexagon;
	}

}
