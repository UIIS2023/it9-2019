package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.ScrollPane;
import java.awt.List;
import java.awt.TextArea;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


@SuppressWarnings("unused")
public class DrawingFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private DrawingView view= new DrawingView();
	private DrawingController controller;
	
	private ButtonGroup btnGroupShapes = new ButtonGroup();
	private ButtonGroup btnGroupDrawSelect = new ButtonGroup();
	private ButtonGroup btnGroupEditDelete = new ButtonGroup();
	private ButtonGroup btnGroupUndoRedo = new ButtonGroup();
	
	private JButton btnEdgeColor;
	private JButton btnInnerColor;
	private JPanel colorPanel;
	private JPanel select_pnl;
	private JToggleButton btnSelect;
	private JPanel delete_edit_panel;
	private JButton btnDelete;
	private JButton btnEdit;
	private JPanel pnlShapes;
	private JButton btnUndo,btnRedo;
	
	private JTextArea logArea;
	
	private JButton btnToFront, btnToBack, btnBringToFront, btnBringToBack; 
	
	
	private JToggleButton btnPoint, btnLine,btnRectangle, btnCircle, btnDonut, btnHexagon;
	
	
	private Color edgeColor = new Color(0, 0, 0);
	private Color innerColor = new Color(255, 255, 255);
	private JToggleButton btnDraw;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmSaveLog;
	private JMenuItem mntmSaveDrawing;
	private JMenuItem mntmOpenLog;
	private JMenuItem mntmOpenDrawing;
	
	JButton btnLoadNext;
	

	

	public DrawingFrame() {
		setTitle("Ana-Marija Karcas");
		
		///////////////////////////////////////////////		CONTROL PANEL	/////////////////////////////////////////////////
		JPanel north = new JPanel();
		north.setBackground(Color.GRAY);
		getContentPane().add(north, BorderLayout.NORTH);
		
		select_pnl = new JPanel();
		select_pnl.setBackground(Color.GRAY);
		north.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnLoadNext = new JButton("Load Next");
		btnLoadNext.setEnabled(false);
		btnLoadNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					controller.loadNext();
					if(btnSelect.isSelected()){
						controller.setOperationEditDelete();
						controller.btnSelection(false);
					}else if(btnDraw.isSelected()){
						controller.setOperationDrawing();
						controller.btnSelection(true);
					}
				} catch (IndexOutOfBoundsException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		north.add(btnLoadNext);
		north.add(select_pnl);
		
		btnSelect = new JToggleButton("Select");
		
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setOperationEditDelete();
				controller.btnSelection(false);
				logArea.append("Select button enabled \n");
				
			}
		});
		
		btnDraw = new JToggleButton("Draw");
		
		btnDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setOperationDrawing();
				controller.btnSelection(true);
				logArea.append("Draw button enabled \n");
			}
		});
		btnDraw.setSelected(true);
		
		GroupLayout gl_select_pnl = new GroupLayout(select_pnl);
		gl_select_pnl.setHorizontalGroup(
			gl_select_pnl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_select_pnl.createSequentialGroup()
					.addGap(84)
					.addComponent(btnSelect)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDraw)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_select_pnl.setVerticalGroup(
			gl_select_pnl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_select_pnl.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_select_pnl.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDraw)
						.addComponent(btnSelect)))
		);
		select_pnl.setLayout(gl_select_pnl);
		
		colorPanel = new JPanel();
		colorPanel.setBackground(Color.GRAY);
		
		btnEdgeColor = new JButton("Edge color");
		btnEdgeColor.setBackground(edgeColor);
		btnEdgeColor.setForeground(Color.WHITE);
		
		btnEdgeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color temp = JColorChooser.showDialog(null, "Choose color", edgeColor);
				if (temp != null) {
					
					edgeColor = temp;
					btnEdgeColor.setBackground(edgeColor);
					btnEdgeColor.setForeground(Color.BLACK);
					
				}
				
			}
		});
		
		btnInnerColor = new JButton("Inner color");
		btnInnerColor.setBackground(innerColor);
		btnInnerColor.setForeground(Color.BLACK);
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color temp = JColorChooser.showDialog(null, "Choose color", innerColor);
				if (temp != null) {
					innerColor = temp;
					btnInnerColor.setBackground(innerColor);
				}
			}
		});
		
		
		delete_edit_panel = new JPanel();
		delete_edit_panel.setBackground(Color.GRAY);
		north.add(delete_edit_panel);
		
		btnDelete = new JButton("Delete");
		
		btnDelete.setEnabled(false);
		
		btnEdit = new JButton("Edit");
		
		btnEdit.setEnabled(false);
		GroupLayout gl_delete_edit_panel = new GroupLayout(delete_edit_panel);
		gl_delete_edit_panel.setHorizontalGroup(
			gl_delete_edit_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_delete_edit_panel.createSequentialGroup()
					.addGap(5)
					.addComponent(btnDelete)
					.addGap(5)
					.addComponent(btnEdit))
		);
		gl_delete_edit_panel.setVerticalGroup(
			gl_delete_edit_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_delete_edit_panel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_delete_edit_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnDelete)
						.addComponent(btnEdit)))
		);
		delete_edit_panel.setLayout(gl_delete_edit_panel);
		north.add(colorPanel);
		GroupLayout gl_colorPanel = new GroupLayout(colorPanel);
		gl_colorPanel.setHorizontalGroup(
			gl_colorPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_colorPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(btnEdgeColor)
					.addGap(5)
					.addComponent(btnInnerColor))
		);
		gl_colorPanel.setVerticalGroup(
			gl_colorPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_colorPanel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_colorPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnEdgeColor)
						.addComponent(btnInnerColor)))
		);
		colorPanel.setLayout(gl_colorPanel);
		
		////////////////////////////////////////////////   SHAPES PANEL   /////////////////////////////////////////////////
		pnlShapes = new JPanel();
		getContentPane().add(pnlShapes, BorderLayout.WEST);
		
		btnPoint = new JToggleButton("Point");
		
		btnLine = new JToggleButton("Line");
		
		btnRectangle = new JToggleButton("Rectangle");
		
		btnCircle = new JToggleButton("Circle");
		
		btnDonut = new JToggleButton("Donut");
		
		btnHexagon = new JToggleButton("Hexagon");
		GroupLayout gl_pnlShapes = new GroupLayout(pnlShapes);
		gl_pnlShapes.setHorizontalGroup(
			gl_pnlShapes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlShapes.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlShapes.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnHexagon, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnDonut, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnCircle, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnRectangle, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnLine, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnPoint, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnlShapes.setVerticalGroup(
			gl_pnlShapes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlShapes.createSequentialGroup()
					.addGap(40)
					.addComponent(btnPoint)
					.addGap(18)
					.addComponent(btnLine)
					.addGap(18)
					.addComponent(btnRectangle)
					.addGap(18)
					.addComponent(btnCircle)
					.addGap(18)
					.addComponent(btnDonut)
					.addGap(18)
					.addComponent(btnHexagon)
					.addContainerGap(246, Short.MAX_VALUE))
		);
		pnlShapes.setLayout(gl_pnlShapes);
		
		//////////////////////////////////// DRAWING VIEW /////////////////////////////////////////////

		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//obavestava kontroler da je na njega kliknuto 
				if(btnDraw.isSelected()){
					controller.addShape(e, edgeColor, innerColor);
				}
				if(btnSelect.isSelected()){
					//controller.setOperationEditDelete();
					controller.selectShape(e);
				}
			}
		});
		
		getContentPane().add(view, BorderLayout.CENTER);
		GroupLayout gl_view = new GroupLayout(view);
		gl_view.setHorizontalGroup(
			gl_view.createParallelGroup(Alignment.LEADING)
				.addGap(0, 656, Short.MAX_VALUE)
		);
		gl_view.setVerticalGroup(
			gl_view.createParallelGroup(Alignment.LEADING)
				.addGap(0, 526, Short.MAX_VALUE)
		);
		view.setLayout(gl_view);
		
		
		/************************ Group shape buttons ****************************************/
		btnGroupShapes.add(btnCircle);
		btnGroupShapes.add(btnPoint);
		btnGroupShapes.add(btnLine);
		btnGroupShapes.add(btnRectangle);
		btnGroupShapes.add(btnDonut);
		btnGroupShapes.add(btnHexagon);
		
		/************************ Group draw select buttons ****************************************/
		btnGroupDrawSelect.add(btnDraw);
		btnGroupDrawSelect.add(btnSelect);
		
		btnGroupEditDelete.add(btnEdit);
		btnGroupEditDelete.add(btnDelete);
		
		btnGroupUndoRedo.add(btnUndo);
		btnGroupUndoRedo.add(btnRedo);
		
		
		JPanel UndoRedoPanel = new JPanel();
		UndoRedoPanel.setBackground(Color.GRAY);
		north.add(UndoRedoPanel);
		
		btnUndo = new JButton("Undo");
		btnUndo.setEnabled(false);
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(btnUndo.isEnabled()){
					controller.undo();
					/*if(controller.isEndLog()){
						btnLoadNext.setEnabled(false);
					}*/
					if(btnDraw.isSelected()){
						controller.setOperationDrawing();
					}else if(btnSelect.isSelected()){
						controller.setOperationEditDelete();
					}
				}
				
			}
		});
		
		btnRedo = new JButton("Redo");
		btnRedo.setEnabled(false);
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnRedo.isEnabled()) {
					controller.redo();
					/*if(controller.isEndLog()){
						btnLoadNext.setEnabled(false);
					}else{
						btnLoadNext.setEnabled(true);
					}*/
					if(btnDraw.isSelected()){
						controller.setOperationDrawing();
					}else if(btnSelect.isSelected()){
						controller.setOperationEditDelete();
					}
				}
			}
		});
		btnRedo.setHorizontalAlignment(SwingConstants.LEADING);
		GroupLayout gl_UndoRedoPanel = new GroupLayout(UndoRedoPanel);
		gl_UndoRedoPanel.setHorizontalGroup(
			gl_UndoRedoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_UndoRedoPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(btnUndo)
					.addGap(5)
					.addComponent(btnRedo))
		);
		gl_UndoRedoPanel.setVerticalGroup(
			gl_UndoRedoPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_UndoRedoPanel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_UndoRedoPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnUndo)
						.addComponent(btnRedo)))
		);
		UndoRedoPanel.setLayout(gl_UndoRedoPanel);
		
		JPanel positionsPnl = new JPanel();
		getContentPane().add(positionsPnl, BorderLayout.EAST);
		
		btnToFront = new JButton("To Front");
		btnToFront.setEnabled(false);
		btnToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.toFront();
			}
		});
		
		btnToBack = new JButton("To Back");
		btnToBack.setEnabled(false);
		btnToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toBack();
			}
		});
		
		btnBringToFront = new JButton("Bring To Front");
		btnBringToFront.setEnabled(false);
		btnBringToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToFront();
			}
		});
		
		btnBringToBack = new JButton("Bring To Back");
		btnBringToBack.setEnabled(false);
		btnBringToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToBack();
			}
		});
		GroupLayout gl_positionsPnl = new GroupLayout(positionsPnl);
		gl_positionsPnl.setHorizontalGroup(
			gl_positionsPnl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_positionsPnl.createSequentialGroup()
					.addContainerGap(26, Short.MAX_VALUE)
					.addGroup(gl_positionsPnl.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnBringToBack, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnBringToFront, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnToBack, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnToFront, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_positionsPnl.setVerticalGroup(
			gl_positionsPnl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_positionsPnl.createSequentialGroup()
					.addGap(11)
					.addComponent(btnToFront)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnToBack)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnBringToFront)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnBringToBack)
					.addContainerGap(394, Short.MAX_VALUE))
		);
		positionsPnl.setLayout(gl_positionsPnl);
		
		JPanel logPnl = new JPanel();
		getContentPane().add(logPnl, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_logPnl = new GroupLayout(logPnl);
		gl_logPnl.setHorizontalGroup(
			gl_logPnl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_logPnl.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_logPnl.setVerticalGroup(
			gl_logPnl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_logPnl.createSequentialGroup()
					.addGap(6)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
					.addGap(6))
		);
		
		logArea = new JTextArea();
		logArea.setEditable(false);
		logArea.setForeground(Color.BLACK);
		scrollPane.setViewportView(logArea);
		logPnl.setLayout(gl_logPnl);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmSaveLog = new JMenuItem("Save Log");
		mntmSaveLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.saveLog();
			}
		});
		mnFile.add(mntmSaveLog);
		
		mntmSaveDrawing = new JMenuItem("Save Drawing");
		mntmSaveDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller.saveDrawing();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		});
		mnFile.add(mntmSaveDrawing);
		
		mntmOpenLog = new JMenuItem("Open Log");
		mntmOpenLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.openLog();
				btnLoadNext.setEnabled(true);
			}
		});
		mnFile.add(mntmOpenLog);
		
		mntmOpenDrawing = new JMenuItem("Open Drawing");
		mntmOpenDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller.openDrawing();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		});
		mnFile.add(mntmOpenDrawing);
		
		/************************ Actions ****************************************/
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.delete();
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.edit();
			}
		});
	}
	
	/************************ 	Getters and setters	 ****************************************/

	public ButtonGroup getBtnGroupShapes() {
		return btnGroupShapes;
	}
	
	public ButtonGroup getBtnGroupEditDelete() {
		return btnGroupEditDelete;
	}

	public void setBtnGroupShapes(ButtonGroup btnGroupShapes) {
		this.btnGroupShapes = btnGroupShapes;
	}

	public JButton getBtnEdgeColor() {
		return btnEdgeColor;
	}

	public void setBtnEdgeColor(JButton btnEdgeColor) {
		this.btnEdgeColor = btnEdgeColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public void setBtnInnerColor(JButton btnInnerColor) {
		this.btnInnerColor = btnInnerColor;
	}

	public JToggleButton getBtnSelect() {
		return btnSelect;
	}

	public void setBtnSelect(JToggleButton btnSelect) {
		this.btnSelect = btnSelect;
	}

	public JToggleButton getBtnPoint() {
		return btnPoint;
	}

	public void setBtnPoint(JToggleButton btnPoint) {
		this.btnPoint = btnPoint;
	}

	public JToggleButton getBtnLine() {
		return btnLine;
	}

	public void setBtnLine(JToggleButton btnLine) {
		this.btnLine = btnLine;
	}
	
	public JToggleButton getBtnHexagon() {
		return btnHexagon;
	}

	public void setBtnHexagon(JToggleButton btnHexagon) {
		this.btnHexagon = btnHexagon;
	}

	public JToggleButton getBtnRectangle() {
		return btnRectangle;
	}

	public void setBtnRectangle(JToggleButton btnRectangle) {
		this.btnRectangle = btnRectangle;
	}

	public JToggleButton getBtnCircle() {
		return btnCircle;
	}

	public void setBtnCircle(JToggleButton btnCircle) {
		this.btnCircle = btnCircle;
	}

	public JToggleButton getBtnDonut() {
		return btnDonut;
	}

	public void setBtnDonut(JToggleButton btnDonut) {
		this.btnDonut = btnDonut;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(JButton btnDelete) {
		this.btnDelete = btnDelete;
	}

	public JButton getBtnEdit() {
		return btnEdit;
	}

	public void setBtnEdit(JButton btnEdit) {
		this.btnEdit = btnEdit;
	}
	
	public ButtonGroup getBtnGroupDrawSelect() {
		return btnGroupDrawSelect;
	}
	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public void setBtnUndo(JButton btnUndo) {
		this.btnUndo = btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public void setBtnRedo(JButton btnRedo) {
		this.btnRedo = btnRedo;
	}

	public JButton getBtnToFront() {
		return btnToFront;
	}

	public JButton getBtnToBack() {
		return btnToBack;
	}

	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}

	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}

	public JToggleButton getBtnDraw() {
		return btnDraw;
	}

	public JTextArea getLogArea() {
		return logArea;
	}

	public JButton getBtnLoadNext() {
		return btnLoadNext;
	}
}