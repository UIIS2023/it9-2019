package command;


import geometry.Shape;
import mvc.DrawingModel;

public class BringToFrontCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	private int i;
	
	public BringToFrontCmd(DrawingModel model, Shape shape){
		this.model=model;
		this.shape=shape;
		this.i= model.getShapes().indexOf(shape);
	}
	
	@Override
	public void execute() {
		model.remove(shape);
		model.getShapes().add(shape);
	}

	@Override
	public void unexecute() {
		model.remove(shape);
		model.getShapes().add(i, shape);
		
	}

	@Override
	public String toString() { 
		return "BringToFront : " + shape.toString();
	}
}
