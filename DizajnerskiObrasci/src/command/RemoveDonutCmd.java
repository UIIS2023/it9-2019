package command;

import geometry.Donut;
import mvc.DrawingModel;

public class RemoveDonutCmd implements Command {
	private DrawingModel model;
	private Donut donut;
	private int position;
	
	public RemoveDonutCmd(DrawingModel model, Donut donut) {
		this.model = model;
		this.donut = donut;
		position=model.getShapes().indexOf(donut);
	}
	@Override
	public void execute() {
		model.remove(donut);

	}

	@Override
	public void unexecute() {
		model.getShapes().add(position, donut);
	}

	@Override
	public String toString() { 
		return "Removed : " + donut.toString();
	}
}
