package strategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import mvc.DrawingModel;

public class SaveDrawing implements Save {

	@Override
	public void save(Object obj, File f) {
		DrawingModel model= (DrawingModel) obj;
		ObjectOutputStream objectOutputStream = null;
		try{
			objectOutputStream = new ObjectOutputStream(new FileOutputStream(f));
			objectOutputStream.writeObject(model.getShapes());
			objectOutputStream.close();
			
		} catch(Exception e) {
		      e.getStackTrace();
		}
	}
	
	

}
