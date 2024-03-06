package command;

import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class ToBackCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	private int i;
	
	public ToBackCmd(DrawingModel model, Shape shape){
		this.model=model;
		this.shape=shape;
		this.i=model.getShapes().indexOf(shape);
	}
	@Override
	public void execute() {
		if(i>0){
			Collections.swap(model.getShapes(), i, i-1);
			i--;
		}else{
			return;
		}

	}

	@Override
	public void unexecute() {
		int listSize=model.getShapes().size()-1;
		if(i<listSize){
			Collections.swap(model.getShapes(), i, i+1);
			i++;
		}

	}
	@Override
	public String toString() { 
		return "ToBack : " + shape.toString();
	}

}
