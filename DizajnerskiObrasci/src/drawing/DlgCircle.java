package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;

import geometry.Circle;
import geometry.Point;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgCircle extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtRadius;
	private JButton btnEdgeColor, btnInnerColor;
	private Circle circle = null;
	private Color edgeColor = null, innerColor = null;
	

	public JButton getBtnEdgeColor() {
		return btnEdgeColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	/**
	 * Create the dialog.
	 */
	public DlgCircle() {
		setResizable(false);
		setTitle("IT 9-2019 Karcas Ana-Marija");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 300, 180);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(new GridLayout(4, 2, 0, 0));
			{
				JLabel lblNewLabel_1 = new JLabel("X coordinate", SwingConstants.CENTER);
				panel.add(lblNewLabel_1);
			}
			{
				txtX = new JTextField();
				panel.add(txtX);
				txtX.setColumns(10);
			}
			{
				JLabel lblNewLabel = new JLabel("Y coordinate");
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(lblNewLabel);
			}
			{
				txtY = new JTextField();
				panel.add(txtY);
				txtY.setColumns(10);
			}
			{
				JLabel lblNewLabel_2 = new JLabel("Radius");
				lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
				panel.add(lblNewLabel_2);
			}
			{
				txtRadius = new JTextField();
				panel.add(txtRadius);
				txtRadius.setColumns(10);
			}
			{
				 btnEdgeColor = new JButton("Edge color");
				btnEdgeColor.setHorizontalAlignment(SwingConstants.CENTER);
				btnEdgeColor.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Color temp = JColorChooser.showDialog(null, "Choose edge color", edgeColor);
						if (temp!= null) {
							edgeColor=temp;
							if (edgeColor.equals(Color.WHITE))  btnEdgeColor.setForeground(Color.BLACK);
							else{
								btnEdgeColor.setBackground(edgeColor);
							}
						}
					}
				});
				panel.add(btnEdgeColor);
			}
			{
				btnInnerColor = new JButton("Inner color");
				btnInnerColor.setHorizontalAlignment(SwingConstants.CENTER);
				btnInnerColor.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Color temp = JColorChooser.showDialog(null, "Choose inner color", innerColor);
						if (temp != null) {
							innerColor=temp;
							if (innerColor.equals(Color.BLACK)) btnInnerColor.setForeground(Color.WHITE);
							else if (innerColor.equals(Color.WHITE)) btnInnerColor.setForeground(Color.BLACK);
							btnInnerColor.setBackground(innerColor);
						}
					}
				});
				panel.add(btnInnerColor);
			}
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.SOUTH);
			panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton btnOk = new JButton("Ok");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							int newX = Integer.parseInt(txtX.getText());
							int newY = Integer.parseInt(txtY.getText());
							int newRadius = Integer.parseInt(txtRadius.getText());

							if(newX < 0 || newY < 0 || newRadius < 1) {
								JOptionPane.showMessageDialog(null, "You entered incorrect information!", "Error!", JOptionPane.ERROR_MESSAGE);
								return;
							}
							circle = new Circle(new Point(newX, newY), newRadius, edgeColor, innerColor);
							dispose();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "You entered incorrect information!", "Error!", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				panel.add(btnOk);
			}
			{
				JButton btnNotOk = new JButton("Cancel");
				btnNotOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				panel.add(btnNotOk);
			}
		}
	}

	public Circle getCircle() {
		return circle;
	}
	
	public void setPoint(Point point) {
		txtX.setText("" + point.getX());
		txtY.setText("" + point.getY());
	}
	
	public void setColors(Color edgeColor, Color innerColor) {
		this.edgeColor = edgeColor;
		this.innerColor = innerColor;
	}
	
	public void setCircle(Circle circle) {
		txtX.setText("" + circle.getCenter().getX());
		txtY.setText("" + circle.getCenter().getY());
		txtRadius.setText("" + circle.getRadius());
		edgeColor = circle.getEdgeColor();
		innerColor = circle.getInnerColor();
	}
}
