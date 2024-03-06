package command;

import geometry.Donut;
import mvc.DrawingModel;

public class AddDonutCmd implements Command {

	private DrawingModel model;
	private Donut donut;
	
	
	
	public AddDonutCmd(DrawingModel model, Donut donut) {
		this.model = model;
		this.donut = donut;
	}

	@Override
	public void execute() {
		model.add(donut);

	}

	@Override
	public void unexecute() {
		model.remove(donut);

	}
	
	@Override
	public String toString() {
		return "Added : "+ donut.toString();
	}

}
