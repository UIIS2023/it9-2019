package command;

import mvc.DrawingModel;
import geometry.Line;

public class RemoveLineCmd implements Command {
	private Line line;
	private DrawingModel model;
	private int position;

	public RemoveLineCmd(Line line, DrawingModel model) {
		this.line = line;
		this.model = model;
		position=model.getShapes().indexOf(line);
	}
	
	public void execute() {
		model.remove(line);
	}

	public void unexecute() {
		model.getShapes().add(position,line);
		//model.add(line);
	}
	@Override
	public String toString() { 
		return "Removed : " + line.toString();
	}

}
