package drawing;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.border.TitledBorder;

import drawing.DlgCircle;
import drawing.DlgDonut;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import stack.DlgStack;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import java.awt.Component;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Cursor;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;

public class FrmDrawing extends JFrame {

	private final int OPERATION_DRAWING = 1;
	private final int OPERATION_EDIT_DELETE = 0;
	
	private int activeOperation = OPERATION_DRAWING;
	
	private PnlDrawing pnlDrawing = new PnlDrawing();
	private ButtonGroup btnsOperation = new ButtonGroup();
	private ButtonGroup btnsShapes = new ButtonGroup();
	
	private JToggleButton btnOperationDrawing = new JToggleButton("Draw");
	private JToggleButton btnSelect = new JToggleButton("Select");
	private JButton btnActionEdit = new JButton("Edit");
	private JButton btnActionDelete = new JButton("Delete");
	private JToggleButton btnShapePoint = new JToggleButton("Point");
	private JToggleButton btnShapeLine = new JToggleButton("Line");
	private JToggleButton btnShapeRectangle = new JToggleButton("Rectangle");
	private JToggleButton btnShapeCircle = new JToggleButton("Circle");
	private JToggleButton btnShapeDonut = new JToggleButton("Donut");
	private JButton btnColorEdge = new JButton("Edge color");
	private JButton btnColorInner = new JButton("Inner color");
	
	private Color edgeColor = Color.BLACK, innerColor = Color.WHITE;
	
	private boolean lineWaitingForSecondPoint = false;
	private Point lineFirstPoint;
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmDrawing frame = new FrmDrawing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmDrawing() {
		setTitle("IT 9-2019 Karcas Ana-Marija");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 700);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(1100, 700));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		pnlDrawing.addMouseListener(pnlDrawingClickListener());
		contentPane.add(pnlDrawing, BorderLayout.CENTER);
		
		JPanel kontrolniPanel = new JPanel();
		contentPane.add(kontrolniPanel, BorderLayout.WEST);
		kontrolniPanel.setLayout(new GridLayout(4, 0, 0, 0));
		
		JPanel draw_select_panel = new JPanel();
		draw_select_panel.setBackground(new Color(255, 228, 181));
		kontrolniPanel.add(draw_select_panel);
		btnOperationDrawing.setFont(new Font("Arial", Font.BOLD, 15));
		btnOperationDrawing.setBackground(new Color(255, 192, 203));
		
		btnOperationDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOperationDrawing();
			}
		});
		btnOperationDrawing.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsOperation.add(btnOperationDrawing);
		btnSelect.setForeground(new Color(128, 0, 0));
		btnSelect.setFont(new Font("Arial", Font.BOLD, 15));
		btnSelect.setBackground(new Color(255, 192, 203));
		btnOperationDrawing.setForeground(new Color(128, 0, 0));
		
		
		
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOperationEditDelete();
			}
		});
		btnSelect.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsOperation.add(btnSelect);
		
		JPanel edit_delete_panel = new JPanel();
		edit_delete_panel.setBackground(new Color(255, 228, 181));
		kontrolniPanel.add(edit_delete_panel);
		btnActionEdit.setFont(new Font("Arial", Font.BOLD, 15));
		btnActionEdit.setBackground(new Color(255, 228, 225));
		
		btnActionEdit.addActionListener(btnActionEditClickListener());
		btnActionEdit.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnActionDelete.setFont(new Font("Arial", Font.BOLD, 15));
		//btnActionDelete.addActionListener(new ActionListener() {
		//});
		btnActionDelete.setBackground(new Color(255, 228, 225));
		
		btnActionDelete.addActionListener(btnActionDeleteClickListener());
		btnActionDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
		GroupLayout gl_edit_delete_panel = new GroupLayout(edit_delete_panel);
		gl_edit_delete_panel.setHorizontalGroup(
			gl_edit_delete_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_edit_delete_panel.createSequentialGroup()
					.addGroup(gl_edit_delete_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_edit_delete_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnActionDelete, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_edit_delete_panel.createSequentialGroup()
							.addGap(62)
							.addComponent(btnActionEdit, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)))
					.addGap(56))
		);
		gl_edit_delete_panel.setVerticalGroup(
			gl_edit_delete_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_edit_delete_panel.createSequentialGroup()
					.addGap(32)
					.addComponent(btnActionEdit)
					.addGap(31)
					.addComponent(btnActionDelete)
					.addContainerGap(46, Short.MAX_VALUE))
		);
		edit_delete_panel.setLayout(gl_edit_delete_panel);
		
		
		JPanel shapes_panel = new JPanel();
		shapes_panel.setBackground(new Color(255, 228, 181));
		kontrolniPanel.add(shapes_panel);
		btnShapePoint.setForeground(new Color(220, 20, 60));
		btnShapePoint.setFont(new Font("Arial", Font.BOLD, 15));
		btnShapePoint.setBackground(new Color(255, 105, 180));
		
		btnShapePoint.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapePoint);
		btnShapeLine.setForeground(new Color(220, 20, 60));
		btnShapeLine.setFont(new Font("Arial", Font.BOLD, 15));
		btnShapeLine.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnShapeLine.setBackground(new Color(255, 105, 180));
		btnsShapes.add(btnShapeLine);
		btnShapeRectangle.setForeground(new Color(220, 20, 60));
		btnShapeRectangle.setFont(new Font("Arial", Font.BOLD, 15));
		btnShapeRectangle.setBackground(new Color(255, 105, 180));
		
		btnShapeRectangle.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeRectangle);
		btnShapeCircle.setForeground(new Color(220, 20, 60));
		btnShapeCircle.setFont(new Font("Arial", Font.BOLD, 15));
		btnShapeCircle.setBackground(new Color(255, 105, 180));
		
		btnShapeCircle.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeCircle);
		btnShapeDonut.setForeground(new Color(220, 20, 60));
		btnShapeDonut.setFont(new Font("Arial", Font.BOLD, 15));
		btnShapeDonut.setBackground(new Color(255, 105, 180));
		
		btnShapeDonut.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeDonut);
		
		btnOperationDrawing.setSelected(true);
		GroupLayout gl_draw_select_panel = new GroupLayout(draw_select_panel);
		gl_draw_select_panel.setHorizontalGroup(
			gl_draw_select_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_draw_select_panel.createSequentialGroup()
					.addGroup(gl_draw_select_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_draw_select_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnSelect, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_draw_select_panel.createSequentialGroup()
							.addGap(42)
							.addComponent(btnOperationDrawing, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGap(47))
		);
		gl_draw_select_panel.setVerticalGroup(
			gl_draw_select_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_draw_select_panel.createSequentialGroup()
					.addGap(26)
					.addComponent(btnOperationDrawing, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addComponent(btnSelect, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
					.addGap(21))
		);
		draw_select_panel.setLayout(gl_draw_select_panel);
		setOperationDrawing();
		btnShapePoint.setSelected(true);
		GroupLayout gl_shapes_panel = new GroupLayout(shapes_panel);
		gl_shapes_panel.setHorizontalGroup(
			gl_shapes_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_shapes_panel.createSequentialGroup()
					.addGap(45)
					.addGroup(gl_shapes_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnShapeDonut, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnShapeCircle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnShapeLine, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnShapeRectangle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnShapePoint, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
					.addContainerGap(48, Short.MAX_VALUE))
		);
		gl_shapes_panel.setVerticalGroup(
			gl_shapes_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_shapes_panel.createSequentialGroup()
					.addComponent(btnShapePoint)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnShapeLine)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnShapeRectangle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnShapeCircle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnShapeDonut)
					.addGap(0, 0, Short.MAX_VALUE))
		);
		shapes_panel.setLayout(gl_shapes_panel);
		
		JPanel color_panel = new JPanel();
		color_panel.setBackground(new Color(255, 228, 181));
		kontrolniPanel.add(color_panel);
	
		btnColorEdge = new JButton("Edge color");
		btnColorEdge.setFont(new Font("Arial", Font.BOLD, 15));
		btnColorEdge.setBackground(new Color(240, 128, 128));
		btnColorEdge.addActionListener(btnColorEdgeClickListener());
		btnColorEdge.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnColorInner = new JButton("Inner color");
		btnColorInner.setFont(new Font("Arial", Font.BOLD, 15));
		btnColorInner.setBackground(new Color(240, 128, 128));
		btnColorInner.addActionListener(btnColorInnerClickListener());
		btnColorInner.setAlignmentX(Component.CENTER_ALIGNMENT);
		GroupLayout gl_color_panel = new GroupLayout(color_panel);
		gl_color_panel.setHorizontalGroup(
			gl_color_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_color_panel.createSequentialGroup()
					.addGap(50)
					.addGroup(gl_color_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnColorInner)
						.addComponent(btnColorEdge))
					.addContainerGap(55, Short.MAX_VALUE))
		);
		gl_color_panel.setVerticalGroup(
			gl_color_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_color_panel.createSequentialGroup()
					.addGap(35)
					.addComponent(btnColorEdge)
					.addGap(29)
					.addComponent(btnColorInner)
					.addContainerGap(45, Short.MAX_VALUE))
		);
		color_panel.setLayout(gl_color_panel);
	}
	
	private MouseAdapter pnlDrawingClickListener() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point mouseClick = new Point(e.getX(), e.getY());
				pnlDrawing.deselect();
				if (!btnShapeLine.isSelected()) lineWaitingForSecondPoint = false;
				
				if (activeOperation == OPERATION_EDIT_DELETE) {
					pnlDrawing.select(mouseClick);
					return;
				}
				
				if (btnShapePoint.isSelected()) {
					pnlDrawing.addShape(new Point(mouseClick.getX(), mouseClick.getY(), edgeColor));
					return;
				} 
				else if (btnShapeLine.isSelected()) {
					if (lineWaitingForSecondPoint) {
						pnlDrawing.addShape(new Line(lineFirstPoint, mouseClick, edgeColor));
						lineWaitingForSecondPoint = false;
						return;
					}
					
					lineFirstPoint = mouseClick;
					lineWaitingForSecondPoint = true;
					return;
				} 
				else if (btnShapeRectangle.isSelected()) {
					DlgRectangle dlgRectangle = new DlgRectangle();
					dlgRectangle.setPoint(mouseClick);
					dlgRectangle.setColors(edgeColor, innerColor);
					dlgRectangle.setVisible(true);
					dlgRectangle.setModal(true);
					
					if(dlgRectangle.getRectangle() != null) pnlDrawing.addShape(dlgRectangle.getRectangle());
					return;
				} 
				else if (btnShapeCircle.isSelected()) {
					DlgCircle dlgCircle = new DlgCircle();
					dlgCircle.setPoint(mouseClick);
					dlgCircle.setColors(edgeColor, innerColor);
					dlgCircle.setVisible(true);
					dlgCircle.setModal(true);
					
					if(dlgCircle.getCircle() != null) pnlDrawing.addShape(dlgCircle.getCircle());
					return;
				} 
				else if (btnShapeDonut.isSelected()) {
					DlgDonut dlgDonut = new DlgDonut();
					dlgDonut.setPoint(mouseClick);
					dlgDonut.setColors(edgeColor, innerColor);
					dlgDonut.setVisible(true);
					dlgDonut.setModal(true);
					
					if(dlgDonut.getDonut() != null) pnlDrawing.addShape(dlgDonut.getDonut());
					return;
				}
			}
		};
	}
	
	private ActionListener btnActionEditClickListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = pnlDrawing.getSelected();
				if (index == -1) return;
				
				Shape shape = pnlDrawing.getShape(index);
				
				if (shape instanceof Point) {
					DlgPoint dlgPoint = new DlgPoint();
					dlgPoint.setPoint((Point)shape);
					dlgPoint.setVisible(true);
					dlgPoint.setModal(true);
					
					if(dlgPoint.getPoint() != null) {
						pnlDrawing.setShape(index, dlgPoint.getPoint());
						pnlDrawing.repaint();
					}
				} else if (shape instanceof Line) {
					DlgLine dlgLine = new DlgLine();
					dlgLine.setLine((Line)shape);
					dlgLine.setVisible(true);
					dlgLine.setModal(true);
					
					if(dlgLine.getLine() != null) {
						pnlDrawing.setShape(index, dlgLine.getLine());
						pnlDrawing.repaint();
					}
				} else if (shape instanceof Rectangle) {
					DlgRectangle dlgRectangle = new DlgRectangle();
					dlgRectangle.setRectangle((Rectangle)shape);
					dlgRectangle.setVisible(true);
					dlgRectangle.setModal(true);
					
					if(dlgRectangle.getRectangle() != null) {
						pnlDrawing.setShape(index, dlgRectangle.getRectangle());
						pnlDrawing.repaint();
					}
				
				}else if (shape instanceof Donut) {
						DlgDonut dlgDonut = new DlgDonut();
						dlgDonut.setDonut((Donut)shape);
						dlgDonut.setVisible(true);
						dlgDonut.setModal(true);
						
						if(dlgDonut.getDonut() != null) {
							pnlDrawing.setShape(index, dlgDonut.getDonut());
							pnlDrawing.repaint();
						}
				} else if (shape instanceof Circle) {
					DlgCircle dlgCircle = new DlgCircle();
					dlgCircle.setCircle((Circle)shape);
					dlgCircle.setVisible(true);
					dlgCircle.setModal(true);
					
					if(dlgCircle.getCircle() != null) {
						pnlDrawing.setShape(index, dlgCircle.getCircle());
						pnlDrawing.repaint();
					}
				} 
			}
		};
	}
	
	private ActionListener btnActionDeleteClickListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pnlDrawing.isEmpty()) return;
				if (JOptionPane.showConfirmDialog(null, "Do you really want to delete the selected shapes?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0)
					pnlDrawing.removeSelected();
			}
		};
	}
	

	private ActionListener btnColorEdgeClickListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edgeColor = JColorChooser.showDialog(null, "Choose edge color", edgeColor);
				if (edgeColor == null) edgeColor = Color.BLACK;
			}
		};
	}
	
	private ActionListener btnColorInnerClickListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				innerColor = JColorChooser.showDialog(null, "Choose inner color", innerColor);
				if (innerColor == null) innerColor = Color.WHITE;
			}
		};
	}

	private void setOperationDrawing() {
		activeOperation = OPERATION_DRAWING;
		
		pnlDrawing.deselect();
		
		btnActionEdit.setEnabled(false);
		btnActionDelete.setEnabled(false);
		
		btnShapePoint.setEnabled(true);
		btnShapeLine.setEnabled(true);
		btnShapeRectangle.setEnabled(true);
		btnShapeCircle.setEnabled(true);
		btnShapeDonut.setEnabled(true);
		
		btnColorEdge.setEnabled(true);
		btnColorInner.setEnabled(true);
	}
	
	private void setOperationEditDelete() {
		activeOperation = OPERATION_EDIT_DELETE;
		
		btnActionEdit.setEnabled(true);
		btnActionDelete.setEnabled(true);
		
		btnShapePoint.setEnabled(false);
		btnShapeLine.setEnabled(false);
		btnShapeRectangle.setEnabled(false);
		btnShapeCircle.setEnabled(false);
		btnShapeDonut.setEnabled(false);
		
		btnColorEdge.setEnabled(false);
		btnColorInner.setEnabled(false);
	}
}