package command;

import geometry.Rectangle;
import mvc.DrawingModel;

public class RemoveRectangleCmd implements Command {

	private Rectangle rectangle;
	private DrawingModel model;
	private int position;
	
	public RemoveRectangleCmd(Rectangle rectangle, DrawingModel model) {
		this.rectangle = rectangle;
		this.model = model;
		position=model.getShapes().indexOf(rectangle);
	}

	@Override
	public void execute() {
		model.remove(rectangle);
	}

	@Override
	public void unexecute() {
		model.getShapes().add(position,rectangle);
	}
	@Override
	public String toString() { 
		return "Removed : " + rectangle.toString();
	}

}