package command;

import java.awt.Color;

import geometry.Line;
import geometry.Point;

public class ModifyLineCmd implements Command {
	private Line oldState;
	private Line newState;
	
	private Line original = new Line(new Point(1,1), new Point(2,2),Color.BLACK);
	
	public ModifyLineCmd(Line oldState, Line newState){
		this.oldState=oldState;
		this.newState=newState;
	}
	
	public void execute(){
		original = oldState.clone(original);
		oldState = newState.clone(oldState);
		//oldState.setSelected(false);
	}
	
	public void unexecute() {
		oldState = original.clone(oldState);
		
	}

	@Override
	public String toString() { 
		return "Modified : " + original.toString()+" --> "+ newState.toString();
	}
}
