// 15819013 Yuta Irisawa
package task12_4;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Plot2D extends JFrame{
	Plot2D(String title){
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		double x1 = Double.parseDouble(args[0]);
		double x2 = Double.parseDouble(args[1]);
		int d = Integer.parseInt(args[2]);
		
		Plot2D plot = new Plot2D("Yuta Irisawa");
		plot.getContentPane().setPreferredSize(new Dimension(600, 600));
		plot.pack();
		GraphPanel panel = new GraphPanel(x1, x2, d);
		plot.getContentPane().add(panel);
		plot.setVisible(true);	// コンポーネントを表示
		
		panel.saveimage(plot, "output12_4.png");
		
		System.out.printf("x1, x2, and d are %d %d %d, respectively\n", (int)x1, (int)x2, d);
	}

}
