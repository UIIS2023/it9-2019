package command;

import geometry.Shape;
import mvc.DrawingController;

public class SelectShapeCmd implements Command {

	private Shape shape;
	private DrawingController controller;
	
	public SelectShapeCmd(DrawingController controller, Shape shape){
		this.controller=controller;
		this.shape=shape;
	}
	
	@Override
	public void execute() {
		shape.setSelected(true);
		controller.getSelectedShapes().add(shape);
	}

	@Override
	public void unexecute() {
		shape.setSelected(false);
		controller.getSelectedShapes().remove(shape);

	}
	@Override
	public String toString() { 
		return "Selected : " + shape.toString();
	}

}
