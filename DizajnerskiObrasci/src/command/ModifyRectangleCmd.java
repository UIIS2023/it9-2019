package command;

import java.awt.Color;

import geometry.Point;
import geometry.Rectangle;


public class ModifyRectangleCmd implements Command {

	private Rectangle oldState;
	private Rectangle newState;
	
	private Rectangle original = new Rectangle(new Point(1,1), 11, 1, Color.BLACK, Color.WHITE);
	
	public ModifyRectangleCmd(Rectangle oldState,Rectangle newState) {
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
