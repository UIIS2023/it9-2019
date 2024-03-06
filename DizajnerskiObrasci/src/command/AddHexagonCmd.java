package command;

import mvc.DrawingModel;
import adapter.HexagonAdapter;

public class AddHexagonCmd implements Command{

	private DrawingModel model;
	private HexagonAdapter hexagon;
	
	
	public AddHexagonCmd(DrawingModel model, HexagonAdapter hexagon) {
		this.model = model;
		this.hexagon = hexagon;
	}

	@Override
	public void execute() {
		model.add(hexagon);
		
	}

	@Override
	public void unexecute() {
		model.remove(hexagon);
		
	}

	@Override
	public String toString() {
		return "Added : "+ hexagon.toString();
	}
}
