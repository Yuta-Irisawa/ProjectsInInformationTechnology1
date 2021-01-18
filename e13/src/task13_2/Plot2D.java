// 15819013 Yuta Irisawa
package task13_2;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Plot2D extends JFrame{
	Plot2D(String title){
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		if(args.length!=3) {
			System.out.println("Command line arguments : [Points file name] [Degree of approximation] [Image file name]");
			System.exit(1);
		}
		
		Matrix points = new Matrix(args[0]);
		Plot2D plot = new Plot2D("Yuta Irisawa");
		plot.getContentPane().setPreferredSize(new Dimension(600, 600));
		plot.pack();
		GraphPanel panel=new GraphPanel(points, Integer.parseInt(args[1]));
		plot.getContentPane().add(panel);
		plot.setVisible(true);
		
		panel.saveImage(plot, args[2]);
	}
}