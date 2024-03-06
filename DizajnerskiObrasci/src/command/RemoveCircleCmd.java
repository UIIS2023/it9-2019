package command;

import geometry.Circle;
import mvc.DrawingModel;

public class RemoveCircleCmd implements Command {

	private DrawingModel model;
	private Circle circle;
	private int position;
	
	public RemoveCircleCmd(DrawingModel model, Circle circle){
		this.model=model;
		this.circle=circle;
		position=model.getShapes().indexOf(circle);
	}
	
	@Override
	public void execute() {
		model.remove(circle);

	}

	@Override
	public void unexecute() {
		model.getShapes().add(position, circle);
	}
	
	@Override
	public String toString() { 
		return "Removed : " + circle.toString();
	}

}