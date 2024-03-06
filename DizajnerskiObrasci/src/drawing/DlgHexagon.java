package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;

import geometry.Point;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import adapter.HexagonAdapter;

@SuppressWarnings("serial")
public class DlgHexagon extends JDialog {
	

	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtRadius;
	private JButton btnInnerColor, btnEdgeColor;
	
	private HexagonAdapter hexagonAdapter=null;
	
	
	private Color edgeColor = null, innerColor = null;
	
	 /*public static void main(String[] arrayOfStrings) {
	        try {
	            DlgHexagon dialog = new DlgHexagon();
	            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	            dialog.setVisible(true);
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	    }*/

	/**
	 * Create the dialog.
	 */
	public DlgHexagon() {
		setResizable(false);
		setTitle("IT 9-2019 Karcas Ana-Marija");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 300, 180);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel centralPnl = new JPanel();
			getContentPane().add(centralPnl, BorderLayout.CENTER);
			centralPnl.setLayout(new GridLayout(4, 2, 0, 0));
			{
				JLabel lblX = new JLabel("X coordinate", SwingConstants.CENTER);
				centralPnl.add(lblX);
			}
			{
				txtX = new JTextField();
				centralPnl.add(txtX);
				txtX.setColumns(10);
			}
			{
				JLabel lblY = new JLabel("Y coordinate");
				lblY.setHorizontalAlignment(SwingConstants.CENTER);
				centralPnl.add(lblY);
			}
			{
				txtY = new JTextField();
				centralPnl.add(txtY);
				txtY.setColumns(10);
			}
			{
				JLabel lblRadius = new JLabel("Radius");
				lblRadius.setHorizontalAlignment(SwingConstants.CENTER);
				centralPnl.add(lblRadius);
			}
			{
				txtRadius = new JTextField();
				centralPnl.add(txtRadius);
				txtRadius.setColumns(10);
			}
			{
				btnEdgeColor = new JButton("Edge color");
				btnEdgeColor.setHorizontalAlignment(SwingConstants.CENTER);
				//btnEdgeColor.setBackground(edgeColor);
				
				btnEdgeColor.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						btnEdgeColor.setBackground(edgeColor);
						edgeColor = JColorChooser.showDialog(null, "Choose edge color", edgeColor);
						if (edgeColor == null) edgeColor = Color.BLACK;
						btnEdgeColor.setBackground(edgeColor);
					}
				});
				centralPnl.add(btnEdgeColor);
			}
			{
				btnInnerColor = new JButton("Inner color");
				btnInnerColor.setHorizontalAlignment(SwingConstants.CENTER);
				//btnInnerColor.setBackground(innerColor);
				btnInnerColor.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						innerColor = JColorChooser.showDialog(null, "Choose inner color", innerColor);
						if (innerColor == null) innerColor = Color.WHITE;
						btnInnerColor.setBackground(innerColor);
					}
				});
				centralPnl.add(btnInnerColor);
			}
		}
		{
			JPanel buttonsPnl = new JPanel();
			getContentPane().add(buttonsPnl, BorderLayout.SOUTH);
			buttonsPnl.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
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
							//Point center= new Point(newX, newY);
							hexagonAdapter= new HexagonAdapter(new Point(newX, newY), newRadius, edgeColor, innerColor);
							dispose();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "You entered incorrect information!", "Error!", JOptionPane.ERROR_MESSAGE);
						}
					}

				});
				buttonsPnl.add(btnOk);
			}
			{
				JButton btnNotOk = new JButton("Cancel");
				btnNotOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonsPnl.add(btnNotOk);
			}
		}
	}

	
	
	public HexagonAdapter getHexagonAdapter() {
		System.out.println(hexagonAdapter.getCenter().getX());
		System.out.println(hexagonAdapter.getCenter().getY());
		return hexagonAdapter;
	
	}


	public void setPoint(Point point) {
		txtX.setText("" + point.getX());
		txtY.setText("" + point.getY());
	}
	
	public void setColors(Color edgeColor, Color innerColor) {
		this.edgeColor = edgeColor;
		this.innerColor = innerColor;
	}

	public void setHexagonAdapter(HexagonAdapter hexagonAdapter) {
		txtX.setText("" + hexagonAdapter.getCenter().getX());
		txtY.setText("" + hexagonAdapter.getCenter().getY());
		txtRadius.setText("" + hexagonAdapter.getRadius());
		edgeColor = hexagonAdapter.getHexagonEdgeColor();
		innerColor = hexagonAdapter.getHexagonInnerColor();
	}


	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}



	public JButton getBtnEdgeColor() {
		return btnEdgeColor;
	}
	
}
