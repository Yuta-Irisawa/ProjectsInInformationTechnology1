// 15819013 Yuta Irisawa
package task14_2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


class GraphPanel implements ActionListener {
    private DataPanel datapanel;
    private JFrame frame;
    private GraphicPanel panel;

    GraphPanel(JFrame newFrame) {
        frame = newFrame;
        panel = new GraphicPanel();
        panel.setDisplayPlot(false);
        datapanel = new DataPanel(frame);
        panel.setDataPanel(datapanel);
        frame.getContentPane().add(panel, "Center");
    }

    public void actionPerformed(ActionEvent e) {
        if (!datapanel.isInitialized()) {
            return;
        }
        datapanel.refreshData();
        panel.setDisplayPlot(true);
        panel.update(panel.getGraphics());
        frame.setVisible(true);
        frame.pack();
    }

    ActionListener getDataPanel() {
        return datapanel;
    }
}

class GraphicPanel extends JPanel {
    private boolean display_plot;
    private DataPanel d;
    Matrix points;		// 行列の要素
	Vector[] eigenVectors;	// 固有ベクトルの配列
	Vector[] vecPoints;		// 固有ベクトルの線を引く時の端の値
	Matrix[][] blueVecPoints;	// A*(x,y)の線を引く時の端の値
    int beginX = 60;
	int beginY = 30;
	int width = 510;
	int height = 510;
	int endX = beginX + width;
	int endY = beginY + height;
	int titleX = beginX + width / 2;
	int titleY = beginY - 20;
	int xtitleX = titleX;
	int xtitleY = endY + 30;
	int ytitleX = beginX - 50;
	int ytitleY = beginY + height / 2;
	float diam = 4; // プロットの点(円)の直径
	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.black);
        g2d.setStroke(new BasicStroke());
        g2d.setFont(new Font("Century Schoolbook", Font.PLAIN, 12));
        if (d.isInitialized() && display_plot) {
            d.refreshData();
            Float xLower = d.getXLower();
            Float xUpper = d.getXUpper();
            Float xInterval = d.getXInterval();
            Float yLower = d.getYLower();
            Float yUpper = d.getYUpper();
            Float yInterval = d.getYInterval();
            Float dx = xUpper - xLower;
            Float dy = yUpper - yLower;
            
            int n = d.getNumberOfPoints();
            points = new Matrix(n, n);
            for(int i=0; i<n; i++) {
            	points.data[i][0] = d.getPoint(i).x;
            	points.data[i][1] = d.getPoint(i).y;
            }
            
            EigenSolver e = new EigenSolver(points);
            points = new Matrix(n, n);
            
            eigenVectors = new Vector[n];
            
            for(int i=0; i<n; i++) {
    			Matrix ans = new Matrix(n, n);
    			for(int j=0; j<n; j++){
    				for(int k=0; k<n; k++){
    					if(j==k) {
    						ans.data[j][k]=points.data[j][k]-e.getEigenValue(i);
    					}else {
    						ans.data[j][k]=points.data[j][k];
    					}
    				}
    			}

    			for(int j=0; j<n; j++){
    				for(int k=0; k<n; k++){
    					if(j==k) {
    						ans.data[j][k]=points.data[j][k]-e.getEigenValue(i);
    					}else {
    						ans.data[j][k]=points.data[j][k];
    					}
    				}
    			}
    			eigenVectors[i] = new Vector(n);
    			for(int j=0; j<n; j++){
    				double sum=0.0;
    				for(int k=0; k<n; k++){
    					sum+= ans.data[j][k]*e.eigenVectorsData(k, i);
    				}
    				eigenVectors[i].data[j] = sum;
    			}
            }
            
            // 固有ベクトルの線を書くための計算
            vecPoints = new Vector[4];
            int j=0;
            for(int i=0; i<4; i+=2) {
            	vecPoints[i] = new Vector(n);
            	vecPoints[i+1] = new Vector(n);
            	if(Math.abs(eigenVectors[j].data[0]) > Math.abs(eigenVectors[j].data[1])) {
            		vecPoints[i].data[0] = xUpper;
            		vecPoints[i+1].data[0] = xLower;
            		double tmp = xUpper * eigenVectors[j].data[1] / eigenVectors[j].data[0];
            		vecPoints[i].data[1] = tmp;
            		vecPoints[i+1].data[1] = -tmp;
            	}else {
            		vecPoints[i].data[1] = yUpper;
            		vecPoints[i+1].data[1] = yLower;
            		double tmp = yUpper * eigenVectors[j].data[0] / eigenVectors[j].data[1];
            		vecPoints[i].data[0] = tmp;
            		vecPoints[i+1].data[0] = -tmp;
            	}
            	j++;
            }
    		
    		/* ========= プロット前の枠組作成 =========*/
    		g2d.setPaint(Color.white);
    		g.fillRect(beginX, beginY, width, height);	// (左上x, 左上y, 幅, 高さ)
    		
    		g2d.setPaint(Color.black);
    		g2d.draw(new Line2D.Float(beginX, endY, beginX, beginY));	// (始x, 始y, 終x, 終y) 縦目盛り
    		g2d.draw(new Line2D.Float(beginX, endY, endX, endY));	// 横目盛り
    		
    		int num = 0;
    		for(float x=beginX; x<=endX; x+=width*xInterval/dx) {
    			g2d.setPaint(new Color(200, 200, 200, 200));
    			if(num!=0) {
    				g2d.draw(new Line2D.Float(x, endY, x, beginY));	// 間の縦線
    			}
    			g2d.setPaint(Color.black);
    			g2d.draw(new Line2D.Float(x, endY, x, endY-5));	// 下の区切り
    			drawCenteredString(g2d, String.format("%.1f", (xLower + xInterval * (float)num)), (int) x, endY+10, 0);
    			num++;
    		}
    		
    		num = 0;
    		for(float y=beginY; y<=endY; y+=height*yInterval/dy) {
    			g2d.setPaint(new Color(200, 200, 200, 200));
    			if(num!=0) {
    				g2d.draw(new Line2D.Float(beginX, y, endX, y));	// 間の横線
    			}
    			g2d.setPaint(Color.black);
            	g2d.draw(new Line2D.Float(beginX, y, beginX+5, y));	// 左の区切り
            	drawCenteredString(g2d, String.format("%.1f",(yUpper - yInterval*(float)num)), beginX-20,  (int) (y-3), 0);
                num++;
    		}
    		
    		g2d.setColor(Color.black);
            drawCenteredString(g2d, d.getTitle(), titleX, titleY, (float) 0.);
            drawCenteredString(g2d, d.getXTitle(), xtitleX, xtitleY, (float) 0.);
            drawCenteredString(g2d, d.getYTitle(), ytitleX, ytitleY, (float) -Math.PI / 2);
    		/* ================================*/
    		
    		/* ============ プロット作業 =========== */   		
    		for(int i=0; i<4; i+=2) {
    			float xi = width * (getVecPointsX(i) - xLower) / dx + beginX;
    			float yi = -height * (getVecPointsY(i) - yLower) / dy + endY;
    			float xi1 = width * (getVecPointsX(i+1) - xLower) / dx + beginX;
    			float yi1 = -height * (getVecPointsY(i+1) - yLower) / dy + endY;
    			
    			g2d.setColor(Color.red);
    			if(getVecPointsY(i)>=yLower && getVecPointsY(i+1)<=yUpper) {
    				g2d.draw(new Line2D.Float(xi, yi, xi1, yi1));
    			}
    		}
            
    		/* ~~~~~~~~~~ 青い線を引く作業 ~~~~~~~~~ */
    		blueVecPoints = new Matrix[(int)(dx-1)][(int)(dy-1)];
    		points = new Matrix(n, n);
            for(int i=0; i<n; i++) {
            	points.data[i][0] = d.getPoint(i).x;
            	points.data[i][1] = d.getPoint(i).y;
            }
    		int row=0, col=0;
            for(float y=yUpper-1; y>yLower; y-=yInterval) {
            	for(float x=xLower+1; x<xUpper; x+=xInterval) {
            		Matrix pointXY = new Matrix(1, 2);
            		pointXY.data[0][0] = x;
            		pointXY.data[0][1] = y;
            		
            		blueVecPoints[row][col] = Matrix.dot(pointXY, points);
            		double l = Math.sqrt(Math.pow(getBlueVecPointsX(row, col) - x, 2) 
            				+ Math.pow(getBlueVecPointsY(row, col) - y, 2));
            		blueVecPoints[row][col].data[0][0] = x + (getBlueVecPointsX(row, col) - x) / l;
            		blueVecPoints[row][col].data[0][1] = y + (getBlueVecPointsY(row, col) - y) / l;
            		
            		float xi = width * (x - xLower) / dx + beginX;
        			float yi = -height * (y - yLower) / dy + endY;
        			float xi1 = width * (getBlueVecPointsX(row, col) - xLower) / dx + beginX;
        			float yi1 = -height * (getBlueVecPointsY(row, col) - yLower) / dy + endY;
        			
        			g2d.setColor(Color.blue);
        			g2d.draw(new Line2D.Float(xi, yi, xi1, yi1));
        			
            		col++;
            	}
            	System.out.println();
            	row++;
            	col=0;
            }
            /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
            
    		/* ================================ */
        }
    }
    
	float getPointX(int i) {
    	return d.getPoint(i).x;
    }
    
    float getPointY(int i) {
    	return d.getPoint(i).y;
    }
    
    float getVecPointsX(int i) {
		return (float)vecPoints[i].data[0];
	}
	
	float getVecPointsY(int i) {
		return (float)vecPoints[i].data[1];
	}
	
	float getBlueVecPointsX(int row, int col) {
		return (float)blueVecPoints[row][col].data[0][0];
	}
	
	float getBlueVecPointsY(int row, int col) {
		return (float)blueVecPoints[row][col].data[0][1];
	}
	
    void setDataPanel(DataPanel new_d) {
        d = new_d;
    }

    void setDisplayPlot(boolean new_display) {
        display_plot = new_display;
    }
    
    private void drawCenteredString(Graphics2D g2d, String string,
                                    int x0, int y0, float angle) {
        FontRenderContext frc = g2d.getFontRenderContext();
        Rectangle2D bounds = g2d.getFont().getStringBounds(string, frc);
        LineMetrics metrics = g2d.getFont().getLineMetrics(string, frc);
        if (angle == 0) {
            g2d.drawString(string, x0 - (float) bounds.getWidth() / 2,
                y0 + metrics.getHeight() / 2);
        } else {
            g2d.rotate(angle, x0, y0);
            g2d.drawString(string, x0 - (float) bounds.getWidth() / 2,
                y0 + metrics.getHeight() / 2);
            g2d.rotate(-angle, x0, y0);
        }
    }
}