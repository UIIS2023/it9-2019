package command;

import java.util.ArrayList;
import java.util.HashMap;

import geometry.Shape;
import mvc.DrawingModel;

public class RemoveShapesCmd implements Command {

	private DrawingModel model;
	
	private ArrayList<Shape> selectedShapes= new ArrayList<Shape>();
	
	HashMap<Integer, Shape> positions = new HashMap<Integer, Shape>();
	
	
	public RemoveShapesCmd(DrawingModel model,ArrayList<Shape> selectedShapes){
		this.model=model;
		this.selectedShapes=selectedShapes;
		for(Shape shape : selectedShapes) {
			positions.put(model.getShapes().indexOf(shape), shape);
		}
		
	}
	@Override
	public void execute() {
		for(Shape shape : selectedShapes) {
			model.getShapes().remove(shape);
			
			}

	}

	@Override
	public void unexecute() {
		
		for(Integer i: positions.keySet()){
			model.getShapes().add(i, positions.get(i));
		}
		

	}
	
	@Override
	public String toString() { 
		String text="Removed: ";
		for (Integer i : positions.keySet()){
			Shape shape=positions.get(i);
			text=text+ shape.toString()+", ";
		}
	
		return text;
	}

}
