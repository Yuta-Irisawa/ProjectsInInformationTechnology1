// 15819013 Yuta Irisawa
package task13_3;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

class DataPanel implements ActionListener {
    private boolean initialized;
    private int titleIndex, xTitleIndex, yTitleIndex;
    private int xLowerIndex, xUpperIndex, xIntervalIndex;
    private int yLowerIndex, yUpperIndex, yIntervalIndex;
    private int polyIndex;
    private JFrame frame;
    private JPanel panel;
    private String title;
    private String xTitle;
    private String yTitle;
    private float xLower, xUpper, xInterval;
    private float yLower, yUpper, yInterval;
    private int polynominals;
    private Point2D.Float[] points;
    private JLabel[] paramLabels;
    private JTextField[] paramFields;
    private JTextField[] dataFields;

    DataPanel(JFrame newFrame) {
        initialized = false;
        titleIndex = 0;
        xTitleIndex = 1;
        yTitleIndex = 2;
        xLowerIndex = 3;
        xUpperIndex = 4;
        xIntervalIndex = 5;
        yLowerIndex = 6;
        yUpperIndex = 7;
        yIntervalIndex = 8;
        polyIndex = 9;
        paramLabels = new JLabel[10];
        paramLabels[titleIndex] = new JLabel("Title");
        paramLabels[xTitleIndex] = new JLabel("X Axis Title");
        paramLabels[yTitleIndex] = new JLabel("Y Axis Title");
        paramLabels[xLowerIndex] = new JLabel("X lower bound");
        paramLabels[xUpperIndex] = new JLabel("X upper bound");
        paramLabels[xIntervalIndex] = new JLabel("X tick interval");
        paramLabels[yLowerIndex] = new JLabel("Y lower bound");
        paramLabels[yUpperIndex] = new JLabel("Y upper bound");
        paramLabels[yIntervalIndex] = new JLabel("Y tick interval");
        paramLabels[polyIndex] = new JLabel("Order of polynominals");
        paramFields = new JTextField[10];
        paramFields[titleIndex] = new JTextField("Test Title");
        paramFields[xTitleIndex] = new JTextField("X");
        paramFields[yTitleIndex] = new JTextField("Y");
        paramFields[xLowerIndex] = new JTextField("-10");
        paramFields[xUpperIndex] = new JTextField("130");
        paramFields[xIntervalIndex] = new JTextField("14");
        paramFields[yLowerIndex] = new JTextField("10");
        paramFields[yUpperIndex] = new JTextField("190");
        paramFields[yIntervalIndex] = new JTextField("18");
        paramFields[polyIndex] = new JTextField("5");
        
        frame = newFrame;
        panel = new JPanel(new FlowLayout());
        frame.getContentPane().add(panel, "West");
    }

    public void actionPerformed(ActionEvent e) {
        JFrame fileFrame = new JFrame();
        JPanel filePanel = new JPanel();
        JFileChooser fileChooser = new JFileChooser();
        fileFrame.getContentPane().add(filePanel);
        filePanel.add(fileChooser);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(filePanel);
        if (result != JFileChooser.APPROVE_OPTION) {
            JLabel msg = new JLabel("No file selected");
            panel.add(msg);
            return;
        }

        File datafile = fileChooser.getSelectedFile();
        initialized = readFile(datafile);
        panel.update(panel.getGraphics());
        frame.pack();
        frame.setVisible(true);
    }

    // The data file contains two sections.
    // The first section contains parameters used for configuring the graph.
    // Each line starts with a single word identifier followed by a space.
    // The rest of the line is the value.
    // It is terminated by a line containing the word "Data".
    // The second section contains the data pairs that will be graphed.
    private boolean readFile(File datafile) {
        int numAllocated = 10;
        int numRead = 0;
        int numDataPoints = 0;
        String[] dataStrings = new String[numAllocated];
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(datafile));
            String text;
            while ((text = reader.readLine()) != null) {
                if (numRead >= numAllocated) {
                    numAllocated = 2 * numAllocated;
                    String[] temp = dataStrings;
                    dataStrings = new String[numAllocated];
                    System.arraycopy(temp, 0, dataStrings, 0, numRead);
                }

                dataStrings[numRead] = text;
                numRead = numRead + 1;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        try {
            if (reader != null)
                reader.close();
        } catch (IOException e) {
            System.out.println("IO Exception on close");
        }

        int thisCase = -2;
        int thisDataPoint = 0;
        for (int i = 0; i < numRead; i++) {
            String[] segments = dataStrings[i].split(" ");
            if (segments[0].equals("Title")) {
                thisCase = titleIndex;
            } else if (segments[0].equals("xTitle")) {
                thisCase = xTitleIndex;
            } else if (segments[0].equals("yTitle")) {
                thisCase = yTitleIndex;
            } else if (segments[0].equals("xLower")) {
                thisCase = xLowerIndex;
            } else if (segments[0].equals("xUpper")) {
                thisCase = xUpperIndex;
            } else if (segments[0].equals("xInterval")) {
                thisCase = xIntervalIndex;
            } else if (segments[0].equals("yLower")) {
                thisCase = yLowerIndex;
            } else if (segments[0].equals("yUpper")) {
                thisCase = yUpperIndex;
            } else if (segments[0].equals("yInterval")) {
                thisCase = yIntervalIndex;
            } else if (segments[0].equals("polynominals")) {
            	thisCase = polyIndex;
            } else if (segments[0].equals("Data")) {
                thisCase = -1;
                numDataPoints = numRead - i - 1;
                dataFields = new JTextField[2 * numDataPoints];
                points = new Point2D.Float[numDataPoints];
            } else if (thisCase != -1) {
                thisCase = -2;
            }

            if (thisCase >= 0 && segments.length > 1) {
                StringBuilder temp = new StringBuilder(segments[1]);
                for (int j = 2; j < segments.length; j++)
                    temp.append(" ").append(segments[j]);
                paramFields[thisCase].setText(temp.toString());
                thisCase = -2;
            } else if (thisCase == -1 && !segments[0].equals("Data")
                && thisDataPoint < numDataPoints) {
                dataFields[2 * thisDataPoint] = new JTextField(segments[0]);
                dataFields[2 * thisDataPoint + 1] = new JTextField(segments[1]);
                thisDataPoint++;
            }
        }


        frame.getContentPane().remove(panel);
        panel = new JPanel(new GridLayout(10 + numDataPoints, 2));
        for (int i = 0; i < 10; i++) {
            panel.add(paramLabels[i]);
            panel.add(paramFields[i]);
            paramLabels[i].setBorder(new LineBorder(new Color(240, 240, 240, 240), 4));
            paramFields[i].setBorder(new LineBorder(new Color(240, 240, 240, 240), 4));
        }
        for (int i = 0; i < numDataPoints; i++) {
            panel.add(dataFields[2 * i]);
            panel.add(dataFields[2 * i + 1]);
            dataFields[2 * i].setBorder(new LineBorder(new Color(240, 240, 240, 240), 4));
            dataFields[2 * i + 1].setBorder(new LineBorder(new Color(240, 240, 240, 240), 4));
        }
        frame.getContentPane().add(panel, "West");

        return true;
    }

    // Read data from panel in case user made any changes
    void refreshData() {
        if (!initialized) {
            return;
        }
        title = paramFields[titleIndex].getText();
        xTitle = paramFields[xTitleIndex].getText();
        yTitle = paramFields[yTitleIndex].getText();
        xLower = Float.parseFloat(paramFields[xLowerIndex].getText());
        xUpper = Float.parseFloat(paramFields[xUpperIndex].getText());
        xInterval = Float.parseFloat(paramFields[xIntervalIndex].getText());
        yLower = Float.parseFloat(paramFields[yLowerIndex].getText());
        yUpper = Float.parseFloat(paramFields[yUpperIndex].getText());
        yInterval = Float.parseFloat(paramFields[yIntervalIndex].getText());
        polynominals = Integer.parseInt(paramFields[polyIndex].getText());
        for (int i = 0; i < points.length; i++) {
            Float x = Float.parseFloat(dataFields[2 * i].getText());
            Float y = Float.parseFloat(dataFields[2 * i + 1].getText());
            points[i] = new Point2D.Float(x, y);
        }
    }

    boolean isInitialized() {
        return initialized;
    }

    String getTitle() {
        return title;
    }

    String getXTitle() {
        return xTitle;
    }

    String getYTitle() {
        return yTitle;
    }

    float getXLower() {
        return xLower;
    }

    float getXUpper() {
        return xUpper;
    }

    float getXInterval() {
        return xInterval;
    }

    float getYLower() {
        return yLower;
    }

    float getYUpper() {
        return yUpper;
    }

    float getYInterval() {
        return yInterval;
    }
    
    int getPolynominals() {
    	return polynominals;
    }

    int getNumberOfPoints() {
        return points.length;
    }

    Point2D.Float getPoint(int i) {
        if (i < 0) {
            i = 0;
        } else if (i >= points.length) {
            i = points.length - 1;
        }
        return points[i];
    }
}