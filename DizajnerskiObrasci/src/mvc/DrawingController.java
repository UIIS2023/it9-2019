package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

import javax.security.auth.login.LoginException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import adapter.HexagonAdapter;
import command.AddCircleCmd;
import command.AddDonutCmd;
import command.AddHexagonCmd;
import command.AddLineCmd;
import command.AddPointCmd;
import command.AddRectangleCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.Command;
import command.DeselectShapeCmd;
import command.ModifyCircleCmd;
import command.ModifyDonutCmd;
import command.ModifyHexagonCmd;
import command.ModifyLineCmd;
import command.ModifyPointCmd;
import command.ModifyRectangleCmd;
import command.RemoveCircleCmd;
import command.RemoveDonutCmd;
import command.RemoveHexagonCmd;
import command.RemoveLineCmd;
import command.RemovePointCmd;
import command.RemoveRectangleCmd;
import command.RemoveShapesCmd;
import command.SelectShapeCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import drawing.DlgCircle;
import drawing.DlgDonut;
import drawing.DlgHexagon;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import observer.Observable;
import observer.Observer;
import strategy.SaveDrawing;
import strategy.SaveLog;
import strategy.SaveManager;


public class DrawingController {
	
	private Stack<Command> commandStack= new Stack<>();
	private int undoRedoPointer = -1;
	private ArrayList<Shape> selectedShapes= new ArrayList<>();
	private Shape selected;
	
	private DrawingModel model;
	private DrawingFrame frame;
	private Point pointHelper;
	
	
	private Observable observable= new Observable();
	private Observer observer;
	
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	private ArrayList<String> logCommandsUndo = new ArrayList<String>();
	private ArrayList<String> logCommandsRedo = new ArrayList<String>();
	
	private ArrayList<String> log= new ArrayList<>();
	private int currentLine=0;
	
	private boolean endLog=false;
	
	public ArrayList<Shape> getSelectedShapes() {
		return selectedShapes;
	}
	
	
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model=model;
		this.frame=frame;
		observer= new Observer(frame);	
		observable.addPropertyChangeListener(observer);
		
	}

	/*****************************************ADD SHAPES******************************************************/
	public void addShape(MouseEvent e, Color edgeColor, Color innerColor){
		setOperationDrawing();
		Point mouseClick = new Point(e.getX(),e.getY());
		//model.deselect();
		if(frame.getBtnPoint().isSelected()){
			Point point= new Point(e.getX(), e.getY(), edgeColor);
			
			//model.add(point);
			insertCommand(new AddPointCmd(point,model), point, "Removed", "Added");
			frame.getView().repaint();
		}
		else if(frame.getBtnLine().isSelected()){
			 if(pointHelper == null) {
				pointHelper = new Point(e.getX(),e.getY());
			 }else {
				Point pointTwo = new Point(e.getX(),e.getY());
				Line line = new Line(pointHelper,pointTwo,edgeColor);
				pointHelper=null;
				insertCommand(new AddLineCmd(line, model), line, "Removed", "Added");
				frame.getView().repaint();
			 }
		}
		else if(frame.getBtnRectangle().isSelected()){
			DlgRectangle dlgRectangle = new DlgRectangle();
			dlgRectangle.setPoint(mouseClick);
			dlgRectangle.getBtnEdgeColor().setBackground(edgeColor);
			dlgRectangle.getBtnInnerColor().setBackground(innerColor);
			dlgRectangle.setColors(edgeColor, innerColor);
			dlgRectangle.setVisible(true);
			dlgRectangle.setModal(true);
			
			if(dlgRectangle.getRectangle() != null) {
				insertCommand(new AddRectangleCmd(dlgRectangle.getRectangle(), model), dlgRectangle.getRectangle(), "Removed", "Added");
				//model.add(dlgRectangle.getRectangle());
			}
			frame.getView().repaint();
			return;
		}
		else if(frame.getBtnCircle().isSelected()){
			DlgCircle dlgCircle = new DlgCircle();
			dlgCircle.getBtnEdgeColor().setBackground(edgeColor);
			dlgCircle.getBtnInnerColor().setBackground(innerColor);
			dlgCircle.setPoint(mouseClick);
			dlgCircle.setColors(edgeColor, innerColor);
			dlgCircle.setVisible(true);
			dlgCircle.setModal(true);
			
			if(dlgCircle.getCircle() != null){
				//model.add(dlgCircle.getCircle());
				insertCommand(new AddCircleCmd(model, dlgCircle.getCircle()), dlgCircle.getCircle(), "Removed", "Added");
			}
			frame.getView().repaint();
			return;
		}
		else if(frame.getBtnDonut().isSelected()){
			DlgDonut dlgDonut = new DlgDonut();
			dlgDonut.getBtnEdgeColor().setBackground(edgeColor);
			dlgDonut.getBtnInnerColor().setBackground(innerColor);
			dlgDonut.setPoint(mouseClick);
			dlgDonut.setColors(edgeColor, innerColor);
			dlgDonut.setVisible(true);
			dlgDonut.setModal(true);
			
			if(dlgDonut.getDonut() != null){
				//model.add(dlgDonut.getDonut());
				insertCommand(new AddDonutCmd(model, dlgDonut.getDonut()), dlgDonut.getDonut(), "Removed", "Added");
			}
			frame.getView().repaint();
			return;
			
		}
		else if(frame.getBtnHexagon().isSelected()){
			DlgHexagon dlgHexagon= new DlgHexagon();
			dlgHexagon.getBtnEdgeColor().setBackground(edgeColor);
			dlgHexagon.getBtnInnerColor().setBackground(innerColor);
			dlgHexagon.setPoint(mouseClick);
			dlgHexagon.setColors(edgeColor, innerColor);
			dlgHexagon.setVisible(true);
			dlgHexagon.setModal(true);
			
			if(dlgHexagon.getHexagonAdapter() != null) {
				//model.add(dlgHexagon.getHexagonAdapter());
				insertCommand(new AddHexagonCmd(model, dlgHexagon.getHexagonAdapter()), dlgHexagon.getHexagonAdapter(), "Removed", "Added");
			}
			frame.getView().repaint();
			
			return;
		}
		if(model.getShapes().size()>0){

			frame.getBtnRedo().setEnabled(false);
			frame.getBtnUndo().setEnabled(true);
		}
		endLog=true;
		
	}
	/******************************************DELETE SHAPES********************************************/
	
	public void delete() {
		if(model.isEmpty())return;
		int i=selectedShapes.size()-1;
		
		if (JOptionPane.showConfirmDialog(null, "Do you really want to delete the selected shapes?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0){
			while(i>=0){
				Shape help=selectedShapes.get(i);
				if(help instanceof Point){
					insertCommand(new RemovePointCmd((Point) help, model), help, "Added", "Removed");
				}else if(help instanceof Line){
					insertCommand(new RemoveLineCmd((Line) help, model), help, "Added", "Removed");
				}else if(help instanceof Rectangle){
					insertCommand(new RemoveRectangleCmd((Rectangle) help, model),help, "Added", "Removed");
				}else if(help instanceof Circle){
					insertCommand(new RemoveCircleCmd(model, (Circle) help), help, "Added", "Removed");
				}else if(help instanceof Donut){
					insertCommand(new RemoveDonutCmd(model, (Donut) help), help, "Added", "Removed");
				}else if(help instanceof HexagonAdapter){
					insertCommand(new RemoveHexagonCmd(model, (HexagonAdapter) help), help, "Added", "Removed");
				}
				frame.getView().repaint();
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				selectedShapes.remove(i);
				i--;
				endLog=true;
				setOperationEditDelete();
				
			}
			/*else{
				insertCommand(new RemoveShapesCmd(model,selectedShapes), );
				frame.getView().repaint();
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
			}*/
		}
		
		
	}

	/**************************************EDIT SHAPES**********************************************/
	public void edit() {
		//int index = model.getSelected();
		//if (index == -1) return;
		//ovde moze ako je indeks veci od 1, onemoguciti da se brise 
		//Shape shape = model.getShape(index);
		
		
		Shape shape= selectedShapes.get(0);

		if (shape instanceof Point) {
			//Point oldState=(Point) shape;
			DlgPoint dlgPoint = new DlgPoint();
			dlgPoint.getBtnEdgeColor().setBackground(shape.getEdgeColor());
			dlgPoint.setPoint((Point)shape);
			dlgPoint.setVisible(true);
			dlgPoint.setModal(true);
			
			if(dlgPoint.getPoint() != null) {
				Point newState=dlgPoint.getPoint();
				insertCommand(new ModifyPointCmd((Point)shape, newState), newState, "Modified", "Modified");
				frame.repaint();
			}
			
		} else if (shape instanceof Line) {
			DlgLine dlgLine = new DlgLine();
			dlgLine.getBtnEdgeColor().setBackground(shape.getEdgeColor());
			dlgLine.setLine((Line)shape);
			dlgLine.setVisible(true);
			dlgLine.setModal(true);
			
			if(dlgLine.getLine() != null) {
				
				insertCommand(new ModifyLineCmd((Line) shape, dlgLine.getLine()), dlgLine.getLine(), "Modified", "Modified");
				frame.repaint();
			}
			
		} else if (shape instanceof Rectangle) {
			DlgRectangle dlgRectangle = new DlgRectangle();
			dlgRectangle.setRectangle((Rectangle)shape);
			dlgRectangle.getBtnEdgeColor().setBackground(shape.getEdgeColor());
			dlgRectangle.getBtnInnerColor().setBackground(shape.getInnerColor());
			dlgRectangle.setVisible(true);
			dlgRectangle.setModal(true);
			
			if(dlgRectangle.getRectangle() != null) {
				
				insertCommand(new ModifyRectangleCmd((Rectangle) shape, dlgRectangle.getRectangle()), dlgRectangle.getRectangle(), "Modified", "Modified");
				frame.getView().repaint();
			}
			
		
		}else if (shape instanceof Donut) {
				DlgDonut dlgDonut = new DlgDonut();
				dlgDonut.setDonut((Donut)shape);
				dlgDonut.getBtnEdgeColor().setBackground(shape.getEdgeColor());
				dlgDonut.getBtnInnerColor().setBackground(shape.getInnerColor());
				dlgDonut.setVisible(true);
				dlgDonut.setModal(true);
				
				if(dlgDonut.getDonut() != null) {
					
					insertCommand(new ModifyDonutCmd((Donut) shape, dlgDonut.getDonut()), dlgDonut.getDonut(), "Modified", "Modified");
					frame.getView().repaint();
				}
				
		} else if (shape instanceof Circle) {
			DlgCircle dlgCircle = new DlgCircle();
			dlgCircle.setCircle((Circle)shape);
			dlgCircle.getBtnEdgeColor().setBackground(shape.getEdgeColor());
			dlgCircle.getBtnInnerColor().setBackground(shape.getInnerColor());
			dlgCircle.setVisible(true);
			dlgCircle.setModal(true);
			
			if(dlgCircle.getCircle() != null) {
				
				insertCommand(new ModifyCircleCmd((Circle) shape, dlgCircle.getCircle()), dlgCircle.getCircle(), "Modified","Modified");
				frame.repaint();
			}
			
		} else if(shape instanceof HexagonAdapter){
			
			DlgHexagon dlgHexagon = new DlgHexagon();
			dlgHexagon.setHexagonAdapter((HexagonAdapter)shape);
			dlgHexagon.getBtnEdgeColor().setBackground(shape.getEdgeColor());
			dlgHexagon.getBtnInnerColor().setBackground(shape.getInnerColor());
			dlgHexagon.setVisible(true);
			dlgHexagon.setModal(true);
			
			if(dlgHexagon.getHexagonAdapter() != null) {
				
				insertCommand(new ModifyHexagonCmd((HexagonAdapter) shape, dlgHexagon.getHexagonAdapter()), dlgHexagon.getHexagonAdapter(), "Modified", "Modified");
				
				frame.repaint();
			}
		}
		frame.getBtnUndo().setEnabled(true);
		frame.getBtnRedo().setEnabled(false);		
		endLog=true;
	}
	
	/*********************************************SELECT SHAPES****************************************************/
	public void selectShape(MouseEvent e) {
		Iterator<Shape> it= model.getShapes().iterator();
		while(it.hasNext()){
			selected=it.next();
			if(selected.contains(e.getX(), e.getY())){
				if(selected.isSelected()){
					insertCommand(new DeselectShapeCmd(this, selected), selected, "Selected", "Deselected");
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
					frame.repaint();
					setOperationEditDelete();
					endLog=true;
					break;
				}else{
					insertCommand(new SelectShapeCmd(this, selected), selected, "Deselected", "Selected");
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
					frame.repaint();
					setOperationEditDelete();
					endLog=true;
					break;
				}
			}
		}
	}
	
	public void toFront(){
		frame.getBtnRedo().setEnabled(false);
		if (selectedShapes.size()!=1)
			return;
		insertCommand(new ToFrontCmd(model, selectedShapes.get(0)),selectedShapes.get(0), "ToBack", "ToFront" );
		frame.getView().repaint();
		setOperationEditDelete();
	}
	
	public void toBack(){
		frame.getBtnRedo().setEnabled(false);
		if (selectedShapes.size()!=1)
			return;
		insertCommand(new ToBackCmd(model, selectedShapes.get(0)),selectedShapes.get(0), "ToFront", "ToBack");
		frame.getView().repaint();
		setOperationEditDelete();
	}
	
	public void bringToFront(){
		frame.getBtnRedo().setEnabled(false);
		if (selectedShapes.size()!=1)
			return;
		insertCommand(new BringToFrontCmd(model, selectedShapes.get(0)), selectedShapes.get(0), "BringToBack", "BringToFront");
		frame.getView().repaint();
		setOperationEditDelete();
	}
	
	public void bringToBack(){
		frame.getBtnRedo().setEnabled(false);
		if (selectedShapes.size()!=1)
			return;
		insertCommand(new BringToBackCmd(model, selectedShapes.get(0)), selectedShapes.get(0), "BringToFront", "BringToBack");
		frame.getView().repaint();
		setOperationEditDelete();
	}
	
	 public void deleteAll() {
		 model.getShapes().clear();
		 frame.repaint();
	 }

	//UNDO REDO FUNCIONALITIES
	private void deleteElementsAfterPointer(int undoRedoPointer)
	{
	    if(commandStack.size()<1)return;
	    for(int i = commandStack.size()-1; i > undoRedoPointer; i--)
	    {
	        commandStack.remove(i);
	        shapes.remove(i);
	        logCommandsRedo.remove(i);
	        logCommandsUndo.remove(i);
	    }
	}
	private void insertCommand(Command c, Shape s, String undo, String redo)
	{
	    deleteElementsAfterPointer(undoRedoPointer);
	    commandStack.push(c);
	    c.execute();
	    shapes.add(s);
	    logCommandsUndo.add(undo);
	    logCommandsRedo.add(redo);
	    //System.out.println(shapes.size() + " "+ selectedShapes.size());
	    frame.getLogArea().append(c.toString()+"\n");
	    undoRedoPointer++;
	    
	}
	public void undo(){
		frame.getBtnRedo().setEnabled(true);
		/*if(frame.getBtnSelect().isSelected()){
			setOperationEditDelete();
		}*/
		if(logCommandsUndo.get(undoRedoPointer)=="Modified"){
			frame.getLogArea().append("Undo : "+ commandStack.get(undoRedoPointer).toString()+"\n");
		}else{
			frame.getLogArea().append("Undo "+ logCommandsUndo.get(undoRedoPointer)+ " : "+ shapes.get(undoRedoPointer)+ "\n");
		}
		if(logCommandsUndo.get(undoRedoPointer).equals("Added")){
			SelectShapeCmd cmd=new SelectShapeCmd(this, shapes.get(undoRedoPointer));
			cmd.execute();
		}
	    Command command = commandStack.get(undoRedoPointer);//undo method
	    command.unexecute();
	    
	    undoRedoPointer--;
	    frame.repaint();
	    if (undoRedoPointer==-1) frame.getBtnUndo().setEnabled(false);
	    //setOperationEditDelete();
	    
	    
	}

	public void redo(){
		
	    if(undoRedoPointer == commandStack.size() - 1){
	    	 return;
	    }
	    undoRedoPointer++;
	    Command command = commandStack.get(undoRedoPointer);
	    command.execute();
	    if(logCommandsRedo.get(undoRedoPointer)=="Modified"){
	    	frame.getLogArea().append("Redo : "+ commandStack.get(undoRedoPointer).toString()+"\n");
	    }else{
	    	frame.getLogArea().append("Redo "+logCommandsRedo.get(undoRedoPointer)+" : " + shapes.get(undoRedoPointer)+"\n");
	    }
	    if(logCommandsRedo.get(undoRedoPointer).equals("Removed")){
	    	DeselectShapeCmd cmd= new DeselectShapeCmd(this, shapes.get(undoRedoPointer));
	    	cmd.execute();
	    }
	    frame.getBtnUndo().setEnabled(true);
	    frame.repaint();
	    
	    if (undoRedoPointer+1>=commandStack.size()){
	    	frame.getBtnRedo().setEnabled(false);
	    }
	  // setOperationEditDelete();
	}
	
	//OBSERVE BUTTONS

	public void setOperationDrawing() {
		/*if (!model.getShapes().isEmpty()){
			for(Shape shape: model.getShapes()){
				shape.setSelected(false);
				selectedShapes.remove(shape);
				frame.repaint();
			}
		}*///ovaj deo prazni selektovane oblike kada se prebacimo na draw deo
		//frame.getLogArea().append("Draw button selected \n");
		drawSetButtons();
		frame.getBtnGroupEditDelete().clearSelection();
	}
	
	public void drawSetButtons(){
		
		observable.setBtnDeleteEnabled(false);
		observable.setBtnEditEnabled(false);
		
		observable.setBtnBringToBackEnabled(false);
		observable.setBtnBringToFrontEnabled(false);
		observable.setBtnToBackEnabled(false);
		observable.setBtnToFrontEnabled(false);
	}
	
	public void setOperationEditDelete() {
		
		if(selectedShapes.size()!=0){
			if(selectedShapes.size()==1){
				observable.setBtnEditEnabled(true);
				observable.setBtnDeleteEnabled(true);
				checkPositions();
			}else{
			observable.setBtnDeleteEnabled(true);
			observable.setBtnEditEnabled(false);
			observable.setBtnBringToBackEnabled(false);
			observable.setBtnBringToFrontEnabled(false);
			observable.setBtnToBackEnabled(false);
			observable.setBtnToFrontEnabled(false);
			}
		}else {
			observable.setBtnDeleteEnabled(false);
			observable.setBtnEditEnabled(false);
			observable.setBtnBringToBackEnabled(false);
			observable.setBtnBringToFrontEnabled(false);
			observable.setBtnToBackEnabled(false);
			observable.setBtnToFrontEnabled(false);
		}
		
		frame.getBtnGroupShapes().clearSelection();
	}
	
	public void btnSelection(boolean selected){
		frame.getBtnHexagon().setEnabled(selected);
		frame.getBtnPoint().setEnabled(selected);
		frame.getBtnLine().setEnabled(selected);
		frame.getBtnRectangle().setEnabled(selected);
		frame.getBtnDonut().setEnabled(selected);
		frame.getBtnCircle().setEnabled(selected);
		frame.getBtnEdgeColor().setEnabled(selected);
		frame.getBtnInnerColor().setEnabled(selected);
	}
	
	public void checkPositions(){

		for(Shape shape: model.getShapes()){
			if(shape.isSelected()){
				if(model.getShapes().size()==1){
					observable.setBtnBringToBackEnabled(false);
					observable.setBtnBringToFrontEnabled(false);
					observable.setBtnToBackEnabled(false);
					observable.setBtnToFrontEnabled(false);
				}
				else if(model.getShapes().size()!=1){
					if(shape.equals(model.getShape((model.getShapes().size()-1)))){
						observable.setBtnBringToBackEnabled(true);
						observable.setBtnToBackEnabled(true);
						observable.setBtnBringToFrontEnabled(false);
						observable.setBtnToFrontEnabled(false);
					}else if(shape.equals(model.getShape(0))){
						observable.setBtnBringToBackEnabled(false);
						observable.setBtnToBackEnabled(false);
						observable.setBtnBringToFrontEnabled(true);
						observable.setBtnToFrontEnabled(true);
					}
					else {
						observable.setBtnBringToBackEnabled(true);
						observable.setBtnToBackEnabled(true);
						observable.setBtnBringToFrontEnabled(true);
						observable.setBtnToFrontEnabled(true);
					}
				}
			}
		}
	}
		////SAVE FUNCIONALITIES
	public void saveLog() {
		JFileChooser fileChooser= new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setDialogTitle("Choose prefered file location");
		
		if (fileChooser.showSaveDialog(frame.getParent()) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			String filePath = file.getAbsolutePath();
			File logToSave = new File(filePath + ".txt");
			if(logToSave.exists()){
				JOptionPane.showMessageDialog(null, "File was already created!", "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				SaveManager manager = new SaveManager(new SaveLog());
				manager.save(frame, logToSave);
				System.out.println("Successfully saved " + fileChooser.getSelectedFile().getName() + " file!");
			}
		}
		frame.getView().repaint();
		
	}
	
	public void saveDrawing()  throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save drawing");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("ser file (.ser)", "ser");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(filter);
		
		if (fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
			File drawing = new File(fileChooser.getSelectedFile().getAbsolutePath()+".ser");
			if(drawing.exists()){
				JOptionPane.showMessageDialog(null, "File was already created!", "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				SaveManager drawingMng= new SaveManager(new SaveDrawing());
				drawingMng.save(model, drawing);
				System.out.println("Drawing saved");
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void openDrawing() throws IOException{
		
		JFileChooser fileChooser= new JFileChooser();
		fileChooser.setDialogTitle("Open drawing");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("ser file (.ser)", "ser");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(filter);
		
		int option = fileChooser.showOpenDialog(null);
		if(option==JFileChooser.APPROVE_OPTION){
			
			ObjectInputStream objectinputstream = null;
			try {
			    FileInputStream streamIn = new FileInputStream(fileChooser.getSelectedFile());
			    objectinputstream = new ObjectInputStream(streamIn);
			    ArrayList<Shape> readCase = (ArrayList<Shape>) objectinputstream.readObject();
			    setOperationDrawing();
			    for (Shape s:readCase){
			    	if(s instanceof Point){
			    		insertCommand(new AddPointCmd((Point) s, model), s, "Removed", "Added");
			    	}else if (s instanceof Line){
			    		insertCommand(new AddLineCmd((Line) s, model), s, "Removed"	, "Added");
			    	}else if (s instanceof Circle){
			    		insertCommand(new AddCircleCmd(model, (Circle) s), s, "Removed"	, "Added");
			    	}else if (s instanceof Donut){
			    		insertCommand(new AddDonutCmd(model, (Donut) s), s, "Removed"	, "Added");
			    	}else if (s instanceof Rectangle){
			    		insertCommand(new AddRectangleCmd((Rectangle) s, model), s, "Removed"	, "Added");
			    	}else if (s instanceof HexagonAdapter){
			    		insertCommand(new AddHexagonCmd(model, (HexagonAdapter) s), s, "Removed"	, "Added");
			    	}
			    	
			    	if(s.isSelected()) selectedShapes.add(s);
			    	frame.repaint();
			    	frame.getBtnUndo().setEnabled(true);
			    	frame.getBtnRedo().setEnabled(false);
			    }
			    frame.getLogArea().append("Drawing has been loaded\n");
			    
			} catch (Exception e) {
			    e.printStackTrace();
			} finally {
			    if(objectinputstream != null){
			        objectinputstream.close();
			    } 
			}
		}
	}
	
	
	public void openLog(){
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt","txt");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(filter);
		fileChooser.setDialogTitle("Open log");
		
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File textToLoad = fileChooser.getSelectedFile();
			loadLog(textToLoad);
		}
	}
	
	@SuppressWarnings("resource")
	public void loadLog(File text){
		
		
		try {
			endLog=false;
			undoRedoPointer=-1;
			//model.getShapes().clear();
			commandStack.clear();
			shapes.clear();
			logCommandsRedo.clear();
			logCommandsUndo.clear();
			selectedShapes.clear();
			frame.getLogArea().setText("");
			log.clear();
			frame.getView().removeAll();
			setOperationEditDelete();
			Scanner sc= new Scanner(text);
			while(sc.hasNextLine()){
				log.add(sc.nextLine());
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		frame.getBtnLoadNext().setEnabled(true);
		frame.getBtnUndo().setEnabled(false);
		frame.getBtnRedo().setEnabled(false);
		loadNext();
	}

	public void loadNext(){
		Shape s=null;
		
		if(currentLine<log.size()){
			String line=log.get(currentLine);
			//String[]arr=line.split(" ");
			if(line.contains("Point")){
				int x=Integer.parseInt((line.substring(line.indexOf("(")+1, line.indexOf(","))));
				int y=Integer.parseInt((line.substring(line.indexOf(",")+2, line.indexOf(")"))));
				Color c= new Color(Integer.parseInt(line.substring(line.lastIndexOf("(")+1, line.lastIndexOf(")"))));
				s= new Point(x, y, c);
				
				if (currentLine==log.size()-1){
					frame.getBtnLoadNext().setEnabled(false);
				}
			}else if(line.contains("Line")){
				int x=Integer.parseInt((line.substring(line.indexOf("(")+1, line.indexOf(","))));
				int y=Integer.parseInt((line.substring(line.indexOf(",")+2, line.indexOf(")"))));
				int x1=Integer.parseInt(line.substring(find(2, line, '(')+1, find(3, line,',')));
				int y1=Integer.parseInt(line.substring(find(3, line, ',')+2, find(2, line,')')));
				Color c=new Color(Integer.parseInt(line.substring(find(3, line, '(')+1, find(3, line, ')'))));
				s= new Line(new Point(x,y), new Point(x1,y1), c);
				
				if (currentLine==log.size()-1){
					frame.getBtnLoadNext().setEnabled(false);
				}
			}else if(line.contains("Rectangle")){
				//Rectangle = (199, 120) , height= 50 , width= 70 , edgeColor= (-16777216) , innerColor= (-1)
				int x=Integer.parseInt((line.substring(line.indexOf("(")+1, line.indexOf(","))));
				int y=Integer.parseInt((line.substring(line.indexOf(",")+2, line.indexOf(")"))));
				int height=Integer.parseInt(line.substring(find(2,line,'=')+2, find(3,line,',')-1));
				int width=Integer.parseInt(line.substring(find(3,line,'=')+2, find(4,line,',')-1));
				Color edgeC=new Color(Integer.parseInt(line.substring(find(2, line, '(')+1, find(2, line, ')'))));
				Color innerC=new Color(Integer.parseInt(line.substring(find(3, line, '(')+1, find(3, line, ')'))));
				s= new Rectangle(new Point(x, y), height, width, edgeC, innerC);
				if (currentLine==log.size()-1){
					frame.getBtnLoadNext().setEnabled(false);
				}
			}
			else if (line.contains("Circle")){
				//Circle = (417, 170) , radius= 50 , edgeColor= (-16777216) , innerColor= (-1)
				int x=Integer.parseInt((line.substring(line.indexOf("(")+1, line.indexOf(","))));
				int y=Integer.parseInt((line.substring(line.indexOf(",")+2, line.indexOf(")"))));
				int radius=Integer.parseInt(line.substring(find(2, line, '=')+2, find(3, line, ',')-1));
				Color edgeC=new Color(Integer.parseInt(line.substring(find(2, line, '(')+1, find(2, line, ')'))));
				Color innerC=new Color(Integer.parseInt(line.substring(find(3, line, '(')+1, find(3, line, ')'))));
				s= new Circle(new Point(x,y), radius, edgeC, innerC);
				if (currentLine==log.size()-1){
					frame.getBtnLoadNext().setEnabled(false);
				}
			}else if(line.contains("Donut")){
				//Donut = (336, 187) , radius= 50 , innerRadius= 20 , edgeColor= (-16777216) , innerColor= (-1)
				int x=Integer.parseInt((line.substring(line.indexOf("(")+1, line.indexOf(","))));
				int y=Integer.parseInt((line.substring(line.indexOf(",")+2, line.indexOf(")"))));
				int radius=Integer.parseInt(line.substring(find(2, line, '=')+2, find(3, line, ',')-1));
				int innerRadius=Integer.parseInt(line.substring(find(3, line, '=')+2, find(4, line, ',')-1));
				Color edgeC=new Color(Integer.parseInt(line.substring(find(2, line, '(')+1, find(2, line, ')'))));
				Color innerC=new Color(Integer.parseInt(line.substring(find(3, line, '(')+1, find(3, line, ')'))));
				s= new Donut(new Point(x,y), radius, innerRadius, edgeC, innerC);
				if (currentLine==log.size()-1){
					frame.getBtnLoadNext().setEnabled(false);
				}
				
			}else if(line.contains("Hexagon")){
				//Hexagon = (521, 260) , radius= 50 , edgeColor= (-16777216) , innerColor= (-1)
				int x=Integer.parseInt((line.substring(line.indexOf("(")+1, line.indexOf(","))));
				int y=Integer.parseInt((line.substring(line.indexOf(",")+2, line.indexOf(")"))));
				int radius=Integer.parseInt(line.substring(find(2, line, '=')+2, find(3, line, ',')-1));
				Color edgeC=new Color(Integer.parseInt(line.substring(find(2, line, '(')+1, find(2, line, ')'))));
				Color innerC=new Color(Integer.parseInt(line.substring(find(3, line, '(')+1, find(3, line, ')'))));
				s= new HexagonAdapter(new Point(x, y), radius, edgeC, innerC);
				if (currentLine==log.size()-1){
					frame.getBtnLoadNext().setEnabled(false);
				}
				
			}
			/******************************************************CHECK ACTIONS*****************************************************************/
			 if(line.contains("Select button enabled")){
				
				frame.getLogArea().append(line+"\n");
				//setOperationEditDelete();
				frame.getBtnSelect().setSelected(true);
				frame.getBtnDraw().setSelected(false);
				//btnSelection(false);
			}else if (line.contains("Draw button enabled")){
				frame.getBtnSelect().setSelected(false);
				frame.getBtnDraw().setSelected(true);
				//btnSelection(true);
				//setOperationDrawing();
				frame.getLogArea().append(line+"\n");
				
				
			}else if (line.contains("Added")){
				if(line.contains("Undo")){
					undo();
				}else if(line.contains("Redo")){
					redo();
				}else{
					if(s instanceof Point){
						insertCommand(new AddPointCmd((Point) s, model), s, "Removed", "Added");
					}else if (s instanceof Line){
						insertCommand(new AddLineCmd((Line) s, model), s, "Removed", "Added");
					}else if(s instanceof Rectangle){
						insertCommand(new AddRectangleCmd((Rectangle) s, model), s, "Removed", "Added");
					}else if (s instanceof Donut){
						insertCommand(new AddDonutCmd(model, (Donut) s), s, "Removed", "Added");
					}else if(s instanceof Circle){
						insertCommand(new AddCircleCmd(model, (Circle) s), s, "Removed", "Added");
					}else if(s instanceof HexagonAdapter){
						insertCommand(new AddHexagonCmd(model, (HexagonAdapter) s), s, "Removed", "Added");
					}					
				}
				frame.repaint();
			}else if(line.contains("Removed")){
				if(line.contains("Undo")){
					undo();
				}else if(line.contains("Redo")){
					redo();
				}else{
					if(s instanceof Point){
						insertCommand(new RemovePointCmd((Point) s, model), s, "Added", "Removed");
					}else if(s instanceof Line){
						insertCommand(new RemoveLineCmd((Line) s, model), s, "Added", "Removed");
					}else if(s instanceof Rectangle){
						insertCommand(new RemoveRectangleCmd((Rectangle) s, model),s, "Added", "Removed");
					}else if(s instanceof Donut){
						insertCommand(new RemoveDonutCmd(model, (Donut) s), s, "Added", "Removed");
					}else if(s instanceof Circle){
						insertCommand(new RemoveCircleCmd(model, (Circle) s), s, "Added", "Removed");
					}else if(s instanceof HexagonAdapter){
						insertCommand(new RemoveHexagonCmd(model, (HexagonAdapter) s), s, "Added", "Removed");
					}		
				}
				frame.repaint();
				
			}else if(line.contains("Modified")){
				if(line.contains("Undo")){
					undo();
				}else if(line.contains("Redo")){
					redo();
				}else{
					if(s instanceof Point){
						
						Point p = (Point) s;
						Point helpP = (Point) selectedShapes.get(0);
						
						insertCommand(new ModifyPointCmd(helpP, p), p, "Modified","Modified");
						//frame.repaint();
					}else if(s instanceof Line){
						//Modified : Line = (340, 105) , (514, 416) , edgeColor= (-16777216) --> Line = (340, 105) , (514, 416) , edgeColor= (-16751002)
						
						Line helpL = (Line) selectedShapes.get(0);
						int x=Integer.parseInt(line.substring(find(4, line, '(')+1, find(5, line, ',')));
						int y=Integer.parseInt(line.substring(find(5, line, ',')+2, find(4, line, ')')));
						int x1=Integer.parseInt(line.substring(find(5, line, '(')+1, find(7, line, ',')));
						int y1=Integer.parseInt(line.substring(find(7, line, ',')+2, find(5, line, ')')));
						Color c= new Color(Integer.parseInt(line.substring(find(6, line, '(')+1, find(6, line, ')'))));
						Line l = new Line(new Point(x, y), new Point(x1, y1), c);
						
						insertCommand(new ModifyLineCmd(helpL, l), l,"Modified","Modified");
						
					}else if(s instanceof Rectangle){
						//Rectangle = (352, 148) , height= 80 , width= 90 , edgeColor= (-16777216) , innerColor= (-1) --> Rectangle = (352, 148) , height= 80 , width= 90 , edgeColor= (-16777216) , innerColor= (-13159)

						
						Rectangle rec= (Rectangle) selectedShapes.get(0);
						
						int x=Integer.parseInt(line.substring(find(4, line, '(')+1, find(6, line, ',')));
						int y=Integer.parseInt(line.substring(find(6, line, ',')+2, find(4, line, ')')));
						int height=Integer.parseInt(line.substring(find(7, line, '=')+2, find(8, line, ',')-1));
						int width=Integer.parseInt(line.substring(find(8, line, '=')+2, find(9, line, ',')-1));
						Color edgeColor= new Color(Integer.parseInt(line.substring(find(5, line, '(')+1, find(5, line, ')'))));
						Color innerColor= new Color(Integer.parseInt(line.substring(find(6, line, '(')+1, find(6, line, ')'))));
						
						Rectangle r=new Rectangle(new Point(x, y), height, width, edgeColor, innerColor);
						insertCommand(new ModifyRectangleCmd(rec,r),r, "Modified","Modified");
						
					}else if(s instanceof Donut){
						//Donut = (580, 292) , radius= 50 , innerRadius= 20 , edgeColor= (-16777216) , innerColor= (-26266) --> 
						//Donut = (580, 292) , radius= 50 , innerRadius= 20 , edgeColor= (-16777216) , innerColor= (-10027162)
						
						Donut helpD=(Donut) selectedShapes.get(0);
						
						int x=Integer.parseInt(line.substring(find(4, line, '(')+1, find(6, line, ',')));
						int y=Integer.parseInt(line.substring(find(6, line, ',')+2, find(4, line, ')')));
						int radius=Integer.parseInt(line.substring(find(7, line, '=')+2, find(8, line, ',')-1));
						int innerRadius=Integer.parseInt(line.substring(find(8, line, '=')+2, find(9, line, ',')-1));
						Color edgeC=new Color(Integer.parseInt(line.substring(find(5, line, '(')+1, find(5,line,')'))));
						Color innerC=new Color(Integer.parseInt(line.substring(find(6, line, '(')+1, find(6,line,')'))));
						
						Donut d = new Donut(new Point(x, y), radius, innerRadius, edgeC, innerC);
						
						insertCommand(new ModifyDonutCmd(helpD, d), d, "Modified","Modified");
						
					}else if(s instanceof Circle){
						//Modified : Circle = (204, 375) , radius= 80 , edgeColor= (-16777216) , innerColor= (-26266) --> Circle = (204, 375) , radius= 80 , edgeColor= (-16777216) , innerColor= (-6684775)
						
						Circle help= (Circle) selectedShapes.get(0);
						
						int x=Integer.parseInt(line.substring(find(4, line, '(')+1, find(5, line, ',')));
						int y=Integer.parseInt(line.substring(find(5, line, ',')+2, find(4, line, ')')));
						int radius=Integer.parseInt(line.substring(find(6, line, '=')+2, find(7, line, ',')-1));
						Color edgeC=new Color(Integer.parseInt(line.substring(find(5, line, '(')+1, find(5,line,')'))));
						Color innerC=new Color(Integer.parseInt(line.substring(find(6, line, '(')+1, find(6,line,')'))));
						
						Circle c = new Circle(new Point(x, y), radius, edgeC, innerC);
						//isto ovo uraditi za sve oblike 
						insertCommand(new ModifyCircleCmd(help, c), c, "Modified","Modified");
						
					}else if(s instanceof HexagonAdapter){

						HexagonAdapter help=(HexagonAdapter) selectedShapes.get(0);
						//Modified :Hexagon = (1, 1) , radius= 80 , edgeColor= (-16777216) , innerColor= (-13108) --> Hexagon = (98, 112) , radius= 80 , edgeColor= (-16777216) , innerColor= (-6684775)
						int x=Integer.parseInt(line.substring(find(4, line, '(')+1, find(5, line, ',')));
						int y=Integer.parseInt(line.substring(find(5, line, ',')+2, find(4, line, ')')));
						int radius=Integer.parseInt(line.substring(find(6, line, '=')+2, find(7, line, ',')-1));
						Color edgeC=new Color(Integer.parseInt(line.substring(find(5, line, '(')+1, find(5,line,')'))));
						Color innerC=new Color(Integer.parseInt(line.substring(find(6, line, '(')+1, find(6,line,')'))));
						
						HexagonAdapter h = new HexagonAdapter(new Point(x, y), radius, edgeC, innerC);
						insertCommand(new ModifyHexagonCmd(help, h),h, "Modified","Modified");
					}	
				}
				frame.repaint();
			}else if(line.contains("Selected")){
				if(line.contains("Undo")){
					undo();
				}else if(line.contains("Redo")){
					redo();
				}else {
					for(int i=0; i< model.getShapes().size(); i++) {
						if(s.equals(model.getShapes().get(i))) {
							s = model.getShapes().get(i);
						}
					}
					if(s.isSelected()) {
						selectedShapes.remove(s);
					}
					insertCommand(new SelectShapeCmd(this, s), s, "Deselected", "Selected");
					frame.repaint();
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
					
					//setOperationEditDelete();
				}
				
			}else if (line.contains("Deselected")){
				if(line.contains("Undo")){
					undo();
				}else if(line.contains("Redo")){
					redo();
				}else {
					for(int i=0; i< model.getShapes().size(); i++) {
						if(s.equals(model.getShapes().get(i))) {
							s = model.getShapes().get(i);
						}
					}
					insertCommand(new DeselectShapeCmd(this, s), s, "Selected", "Deselected");
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
					
					
				}
				frame.repaint();
			}
			
			else if(line.contains("ToBack")){
				if(line.contains("Undo")){
					undo();
				}else if(line.contains("Redo")){
					redo();
				}else { 
					//toBack();
					insertCommand(new ToBackCmd(model, selectedShapes.get(0)),selectedShapes.get(0), "ToFront", "ToBack");
				}
			}else if(line.contains("ToFront")){
				if(line.contains("Undo")){
					undo();
				}else if(line.contains("Redo")){
					redo();
				}else {
					insertCommand(new ToFrontCmd(model, selectedShapes.get(0)),selectedShapes.get(0), "ToBack", "ToFront");
				}
			}else if(line.contains("BringToBack")){
				if(line.contains("Undo")){
					undo();
				}else if(line.contains("Redo")){
					redo();
				}else {
					
					insertCommand(new BringToBackCmd(model, selectedShapes.get(0)),selectedShapes.get(0), "BringToFront", "BringToBack");
				}
			}else if(line.contains("BringToFront")){
				if(line.contains("Undo")){
					undo();
				}else if(line.contains("Redo")){
					redo();
				}else {
					
					insertCommand(new BringToFrontCmd(model, selectedShapes.get(0)),selectedShapes.get(0), "BringToBack", "BringToFront");
				}
			}
			currentLine++;
			frame.getView().repaint();
		
		}
		//setOperationEditDelete();
		
	}
	
	public boolean isEndLog() {
		return endLog;
	}


	public int find(int n, String line, char toFind){
		int occur=0;
		for(int i=0; i<line.length();i++){
			if(line.charAt(i)==toFind){
				occur+=1;
			}if(occur==n){
				return i;
			}
		}
		return -1;
	}
	
}