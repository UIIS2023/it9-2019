package command;

import geometry.Circle;
import mvc.DrawingModel;

public class AddCircleCmd implements Command {

	private DrawingModel model;
	private Circle circle;
	
	public AddCircleCmd(DrawingModel model, Circle circle){
		this.model=model;
		this.circle=circle;
	}
	
	@Override
	public void execute() {
		model.add(circle);

	}

	@Override
	public void unexecute() {
		model.remove(circle);
	}

	@Override
	public String toString() {
		return "Added : "+ circle.toString();
	}
	

}
