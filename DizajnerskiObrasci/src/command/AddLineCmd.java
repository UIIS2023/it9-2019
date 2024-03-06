package command;

import mvc.DrawingModel;
import geometry.Line;

public class AddLineCmd implements Command {
	private Line line;
	private DrawingModel model;

	
	public AddLineCmd(Line line, DrawingModel model) {
		this.line = line;
		this.model = model;
	}

	public void execute() {
		model.add(line);
	}

	public void unexecute() {
		model.remove(line);
	}

	@Override
	public String toString() {
		return "Added : "+ line.toString();
	} 
}
