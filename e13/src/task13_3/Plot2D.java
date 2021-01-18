// 15819013 Yuta Irisawa
package task13_3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class Plot2D{
	public static void main(String[] args) {
		JFrame frame = new JFrame("Yuta Irisawa");
		Dimension d = new Dimension();
        d.setSize(850, 670);
        frame.setPreferredSize(d);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        String className = UIManager.getSystemLookAndFeelClassName();
        try { 
        	UIManager.setLookAndFeel(className);
        } catch (Exception e) {
        	System.out.println(e);
        	System.exit(1);
        }

        JPanel commandPanel = new JPanel(new FlowLayout());
        JButton openButton = new JButton("Open  Ctrl-O");
        JButton plotButton = new JButton("Plot  Ctrl-P");
        JButton quitButton = new JButton("Quit  Ctrl-Q");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        commandPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }, KeyStroke.getKeyStroke("control Q"), JComponent.WHEN_IN_FOCUSED_WINDOW);
        commandPanel.add(openButton);
        commandPanel.add(plotButton);
        commandPanel.add(quitButton);
        frame.getContentPane().add(commandPanel, "North");

        GraphPanel graphpanel = new GraphPanel(frame);
        plotButton.addActionListener(graphpanel);
        commandPanel.registerKeyboardAction(graphpanel,
            KeyStroke.getKeyStroke("control P"),
            JComponent.WHEN_IN_FOCUSED_WINDOW);

        openButton.addActionListener(graphpanel.getDataPanel());
        commandPanel.registerKeyboardAction(graphpanel.getDataPanel(),
            KeyStroke.getKeyStroke("control O"),
            JComponent.WHEN_IN_FOCUSED_WINDOW);

        frame.setVisible(true);
        frame.pack();
    }
}