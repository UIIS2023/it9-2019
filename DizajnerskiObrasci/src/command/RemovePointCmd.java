package command;

import mvc.DrawingModel;
import geometry.Point;

public class RemovePointCmd implements Command {
	private Point point;
	private DrawingModel model;
	private int position;
	
	public RemovePointCmd(Point point, DrawingModel model){
		this.point=point;
		this.model=model;
		position=model.getShapes().indexOf(point);
	}
	@Override
	public void execute() {
		model.remove(point);
		
	}

	@Override
	public void unexecute() {
		model.getShapes().add(position,point);
		
	}

	@Override
	public String toString() { 
		return "Removed : "   + point.toString();
	}
	
}
