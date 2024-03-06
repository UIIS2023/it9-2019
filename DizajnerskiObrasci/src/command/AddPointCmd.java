package command;

import geometry.Point;
import mvc.DrawingModel;

public class AddPointCmd implements Command {

	private DrawingModel model;
	private Point point;
	
	
	public AddPointCmd(Point point, DrawingModel model){
		this.point=point;
		this.model=model;
	}
	public void execute() {
		model.add(point);
		
	}

	public void unexecute() {
		model.remove(point);
		
	}
	
	@Override
	public String toString() {
		return "Added : "+ point.toString();
	}

}
