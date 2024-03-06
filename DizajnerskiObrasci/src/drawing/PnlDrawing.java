package drawing;

import javax.swing.JPanel;

import geometry.Point;
import geometry.Shape;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class PnlDrawing extends JPanel {

	private ArrayList<Shape> listOfShapes = new ArrayList<Shape>();
	private int i;
	
	public PnlDrawing() {
		setBackground(Color.WHITE);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		listOfShapes.forEach(shape -> shape.draw(g));
	}
	
	public void addShape(Shape shape) {
		listOfShapes.add(shape);
		repaint();
	}
	
	public Shape getShape(int index) {
		return listOfShapes.get(index);
	}
	
	public void setShape(int index, Shape shape) {
		listOfShapes.set(index, shape);
	}
	
	public void removeSelected() {
		listOfShapes.removeIf(shape -> shape.isSelected());
		repaint();
	}
	
	public void deselect() {
		listOfShapes.forEach(shape -> shape.setSelected(false));
		repaint();
	}
	
	public void select(Point point) {
		for (i = listOfShapes.size()-1; i >= 0; i--) {
			if (listOfShapes.get(i).contains(point.getX(), point.getY())) {
				listOfShapes.get(i).setSelected(true);
				repaint();
				return;
			}
		}
	}
	
	public int getSelected() {
		for (i = listOfShapes.size()-1; i >= 0; i--) {
			if (listOfShapes.get(i).isSelected()) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean isEmpty() {
		return listOfShapes.isEmpty();
	}
	
	public ArrayList<Shape> getShapes() {
		return listOfShapes;
	}
}
