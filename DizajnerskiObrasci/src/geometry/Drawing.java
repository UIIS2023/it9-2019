package geometry;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Drawing extends JPanel {

	public Drawing() {
	}
	
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Drawing");
		frame.setSize(800, 600);
		Drawing drawing = new Drawing();
		frame.getContentPane().add(drawing);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void paint(Graphics g) {
		
		Point p = new Point(150, 150, true);
		//p.draw(g);
		
		Line l = new Line(new Point(200, 350), new Point(400, 300), false);
		//l.draw(g);
		
		Rectangle r = new Rectangle(new Point(450, 80), 60, 120, true, Color.GREEN, Color.PINK);
		//r.draw(g);
		
		Circle c = new Circle(new Point(500, 450), 50, true);
		//c.draw(g);
		
		Donut d = new Donut(new Point(600, 350), 80, 50, true);
		//d.draw(g);
		Donut d1 = new Donut(new Point(150, 350), 100, 60, true, Color.ORANGE, Color.red);
		
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		shapes.add(p);
		shapes.add(l);
		shapes.add(c);
		shapes.add(d);
		shapes.add(r);
		
		Iterator<Shape> it = shapes.iterator();
		while(it.hasNext())//dok ima elemenata ispisuj
			System.out.println(it.next().isSelected());
		
		// iscrtati treci element iz liste
		//shapes.get(2).draw(g);
		
		
		// iscrtati poslednji element liste
		shapes.get(shapes.size() - 1).draw(g);
		//dodaj jos jedan element 
		shapes.add(d1);
		//ispisi novi poslednji element
		shapes.get(shapes.size()-1).draw(g);
		// iscrtati element sa indeksom 3
		//shapes.get(3).draw(g);
		
		// dodati i iscrtati liniju u listu tako da ona bude na 4. poziciji u listi
		//shapes.add(5, new Line(new Point(200, 200), new Point(500, 500)));
		//shapes.get(3).draw(g);
		
		// ukloniti drugi element liste
		//shapes.remove(1);
		
		System.out.println(shapes.get(4).toString());
	}
}
