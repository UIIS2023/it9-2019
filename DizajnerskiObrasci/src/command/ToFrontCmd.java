package command;

import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class ToFrontCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	private int i;
	
	public ToFrontCmd(DrawingModel model, Shape shape){
		this.model=model;
		this.shape=shape;
		this.i=model.getShapes().indexOf(shape);
	}
	
	
	
	@Override
	public void execute() {
		if(i<model.getShapes().size()-1){
			Collections.swap(model.getShapes(), i, i+1);
			i++;
		}
	}

	@Override
	public void unexecute() {
		if (i!=0){
			Collections.swap(model.getShapes(), i, i-1);
			i--;
		}

	}
	@Override
	public String toString() { 
		return "ToFront : " + shape.toString();
	}

}
