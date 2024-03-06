package command;

import java.awt.Color;

import adapter.HexagonAdapter;
import geometry.Point;

public class ModifyHexagonCmd implements Command {

	private HexagonAdapter oldState;
	private HexagonAdapter newState;
	private HexagonAdapter original;
	
	public ModifyHexagonCmd(HexagonAdapter oldState, HexagonAdapter newState){
		this.oldState = oldState;
		this.newState = newState;
	}
	@Override
	public void execute() {
		original=oldState.clone();
		
		oldState.setX(newState.getX());
		oldState.setY(newState.getY());
		oldState.setRadius(newState.getRadius());
		oldState.setHexagonEdgeColor(newState.getHexagonEdgeColor());
		oldState.setHexagonInnerColor(newState.getHexagonInnerColor());
		//oldState.setSelected(false);
	}

	@Override
	public void unexecute() {
		//oldState=original.clone();
		oldState.setX(original.getX());
		oldState.setY(original.getY());
		oldState.setRadius(original.getRadius());
		oldState.setHexagonEdgeColor(original.getHexagonEdgeColor());
		oldState.setHexagonInnerColor(original.getHexagonInnerColor());
	}

	@Override
	public String toString() { 
		return "Modified :" + original.toString()+" --> "+ newState.toString();
	}
}
