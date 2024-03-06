package command;

import java.awt.Color;

import geometry.Circle;
import geometry.Point;

public class ModifyCircleCmd implements Command{

	private Circle oldState;
	private Circle newState;
	
	private Circle original=new Circle(new Point(1,1), 2, Color.BLACK, Color.WHITE);
	
	
	public ModifyCircleCmd(Circle oldState, Circle newState){
		this.oldState=oldState;
		this.newState=newState;
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
