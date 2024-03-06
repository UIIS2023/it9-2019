package command;

import adapter.HexagonAdapter;
import mvc.DrawingModel;

public class RemoveHexagonCmd implements Command{

	private DrawingModel model;
	private HexagonAdapter hexagon;
	private int position;
	
	
	public RemoveHexagonCmd(DrawingModel model, HexagonAdapter hexagon) {
		this.model = model;
		this.hexagon = hexagon;
		position=model.getShapes().indexOf(hexagon);
		
	}

	@Override
	public void execute() {
		model.remove(hexagon);
		
	}

	@Override
	public void unexecute() {
		model.getShapes().add(position,hexagon);
		
	}
	@Override
	public String toString() { 
		return "Removed : " + hexagon.toString();
	}

}
