package observer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Observable {
	
	//private boolean btnSelectEnabled;
	private boolean btnDeleteEnabled;
	private boolean btnEditEnabled;
	//private boolean btnDrawEnabled;
	private boolean btnToFrontEnabled;
	private boolean btnBringToFrontEnabled;
	private boolean btnToBackEnabled;
	private boolean btnBringToBackEnabled;
	
	private PropertyChangeSupport propertyChangeSupport;

	public Observable(){
		propertyChangeSupport=new PropertyChangeSupport(this);
	}

	public void setBtnDeleteEnabled(boolean btnDeleteEnabled) {
		propertyChangeSupport.firePropertyChange("btnDelete", this.btnDeleteEnabled, btnDeleteEnabled);
		this.btnDeleteEnabled = btnDeleteEnabled;
	}


	public void setBtnEditEnabled(boolean btnEditEnabled) {
		propertyChangeSupport.firePropertyChange("btnEdit", this.btnEditEnabled, btnEditEnabled);
		this.btnEditEnabled = btnEditEnabled;
	}

	public void setBtnToFrontEnabled(boolean btnToFrontEnabled) {
		propertyChangeSupport.firePropertyChange("btnToFront", this.btnToFrontEnabled, btnToFrontEnabled);
		this.btnToFrontEnabled = btnToFrontEnabled;
	}


	public void setBtnBringToFrontEnabled(boolean btnBringToFrontEnabled) {
		propertyChangeSupport.firePropertyChange("btnBringToFront", this.btnBringToFrontEnabled, btnBringToFrontEnabled);
		this.btnBringToFrontEnabled = btnBringToFrontEnabled;
	}


	public void setBtnToBackEnabled(boolean btnToBackEnabled) {
		propertyChangeSupport.firePropertyChange("btnToBack", this.btnToBackEnabled, btnToBackEnabled);
		this.btnToBackEnabled = btnToBackEnabled;
	}


	public void setBtnBringToBackEnabled(boolean btnBringToBackEnabled) {
		propertyChangeSupport.firePropertyChange("btnBringToBack", this.btnBringToBackEnabled, btnBringToBackEnabled);
		this.btnBringToBackEnabled = btnBringToBackEnabled;
	}

	
	

	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener){
		propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener ){
		propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
	}
	
}
