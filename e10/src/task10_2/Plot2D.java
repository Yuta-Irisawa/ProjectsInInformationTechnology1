// 15819013 Yuta Irisawa
package task10_2;

import javax.swing.*;
import java.awt.*;

public class Plot2D extends JFrame {
	Plot2D(String title){
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		Matrix points = new Matrix(args[0]);
		Plot2D plot = new Plot2D("Yuta Irisawa"); 
		plot.getContentPane().setPreferredSize(new Dimension(800, 400));
		plot.pack();
		GraphPanel panel=new GraphPanel(points);
		plot.getContentPane().add(panel);
		plot.setVisible(true);
	}
}
