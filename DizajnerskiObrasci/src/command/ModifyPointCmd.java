package command;

import geometry.Point;

public class ModifyPointCmd implements Command{
	
	private Point oldState;
	private Point newState;
	private Point original=new Point();
	
	public ModifyPointCmd(Point oldState, Point newState){
		this.oldState=oldState;
		this.newState=newState;
	}
	
	public void execute() {
		original=oldState.clone(original);
		
		oldState=newState.clone(oldState);
		//oldState.setSelected(false);
	}

	public void unexecute(){
		oldState=original.clone(oldState);
		
	}
	@Override
	public String toString() { 
		return "Modified : " + original.toString()+" --> "+ newState.toString();
	}


}



