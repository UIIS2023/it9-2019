package command;

import java.awt.Color;

import geometry.Donut;
import geometry.Point;

public class ModifyDonutCmd implements Command {

	private Donut oldState;
	private Donut newState;
	private Donut original= new Donut(new Point(1,1), 10, 5, Color.BLACK, Color.WHITE);
	
	public ModifyDonutCmd(Donut oldState, Donut newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	@Override
	public void execute() {
		original=oldState.clone(original);
		oldState=newState.clone(oldState);
		//oldState.setSelected(false);
	}

	@Override
	public void unexecute() {
		
		oldState=original.clone(oldState);	
	}
	@Override
	public String toString() { 
		return "Modified : " + original.toString()+" --> "+ newState.toString();
	}

}
