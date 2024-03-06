package command;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToBackCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	private int i;
	
	public  BringToBackCmd( DrawingModel model, Shape shape){
		this.model=model;
		this.shape=shape;
		this.i=model.getShapes().indexOf(shape);
	}
	@Override
	public void execute() {
		
		model.remove(shape);
		model.getShapes().add(0, shape);

	}

	@Override
	public void unexecute() {
		model.remove(shape);
		model.getShapes().add(i, shape);

	}
	
	@Override
	public String toString() { 
		return "BringToBack : " + shape.toString();
	}

}
