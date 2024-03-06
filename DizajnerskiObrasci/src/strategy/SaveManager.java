package strategy;

import java.io.File;

public class SaveManager  implements Save{

	private Save saving;
	
	public SaveManager(Save saving){
		this.saving=saving;
	}
	
	@Override
	public void save(Object obj, File f) {
		saving.save(obj, f);
	}

}
