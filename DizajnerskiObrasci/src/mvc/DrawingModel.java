package mvc;

import java.util.ArrayList;

import geometry.Point;
import geometry.Shape;

public class DrawingModel {
	
	private ArrayList<Shape> shapes=new ArrayList<Shape>();
	private int i;
	
	
	public ArrayList<Shape> getShapes() {
		return shapes;
	}
	
	public void add(Shape s){
		shapes.add(s);
		
	}
	public void remove(Shape s){
		shapes.remove(s);
		
	}
	public Shape getShape(int index){
		return shapes.get(index);
	}
	
	public void setShape(int index, Shape s){
		shapes.set(index, s);
	}
	
	public void removeSelected(){
		shapes.removeIf(shape->shape.isSelected());
		//repaint();
	}
	
	public void deselect() {
		shapes.forEach(shape -> shape.setSelected(false));
		//repaint();
	}
	public int getSelected() {
		for (i = shapes.size()-1; i >= 0; i--) {
			if (shapes.get(i).isSelected()) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean isEmpty() {
		return shapes.isEmpty();
	}
	
	/*public void select(Point point) {
		for (i = shapes.size()-1; i >= 0; i--) {
			if (shapes.get(i).contains(point.getX(), point.getY())) {
				shapes.get(i).setSelected(true);
				//repaint();
				return;
			}
		}
	}*/
	

}
