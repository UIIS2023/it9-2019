package strategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import mvc.DrawingFrame;

public class SaveLog implements Save {

	@Override
	public void save(Object obj, File f) {
		 DrawingFrame frame=(DrawingFrame) obj;
		 try {
		     
		      FileWriter file = new FileWriter(f.getAbsolutePath());
		      BufferedWriter output = new BufferedWriter(file);
		      output.write(frame.getLogArea().getText());
		      output.flush();
		      //System.out.println("Data is flushed to the file.");
		      output.close();
		    }

		    catch(Exception e) {
		      e.getStackTrace();
		    }

	}

}
