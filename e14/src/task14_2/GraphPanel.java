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
    
    Vector[] pointsVec;	// plotする点のxのみの配列とyのみの配列
    Vector approCoef;	// 近似関数の係数
	Vector[] approPoints;	// 近似関数の点の配列
	int n;	// n個の点
    static int separateNum = 500;
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
            int polynominals = d.getPolynominals();
            Float dx = xUpper - xLower;
            Float dy = yUpper - yLower;
            
            n = d.getNumberOfPoints();
            pointsVec = new Vector[2];
            for(int i=0; i<2; i++) {
            	pointsVec[i] = new Vector(n);
            }
            
            for(int i=0; i<n; i++) {
            	pointsVec[0].data[i] = d.getPoint(i).x;
            	pointsVec[1].data[i] = d.getPoint(i).y;
            } 
            
            approCoef = LeastSquare.minimize(pointsVec, polynominals);
            
            approPoints = new Vector[separateNum+1];
    		float _x = xLower;
    		for(int i=0; i<separateNum+1; i++) {
    			approPoints[i] = new Vector(2);
    			approPoints[i].data[0] = _x;
    			for(int j=polynominals; j>=0; j--) {
    				approPoints[i].data[1] += approCoef.data[j] * Math.pow(_x, j);
    			}
    			_x = _x + dx / separateNum;
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
    		int pointsNum = n;
    		
    		for(int i=0; i<pointsNum; i++) {
    			float xi = width * (getPointX(i) - xLower) / dx + beginX;
    			float yi = -height * (getPointY(i) - yLower) / dy + endY;
    			
    			xi -= diam / 2;
    			yi -= diam / 2;
    			
    			// 点をプロット
    			if(getPointX(i)>=xLower && getPointX(i)<=xUpper &&
    					getPointY(i)>=yLower && getPointY(i)<=yUpper) {
    				g2d.setColor(Color.red);
        			g2d.fill(new Ellipse2D.Float(xi, yi, diam, diam));
    			}
    		}
    		
    		for(int i=0; i<separateNum; i++) {
    			float xi = width * (getApproPointX(i) - xLower) / dx + beginX;
    			float yi = -height * (getApproPointY(i) - yLower) / dy + endY;
    			float xi1 = width * (getApproPointX(i+1) - xLower) / dx + beginX;
    			float yi1 = -height * (getApproPointY(i+1) - yLower) / dy + endY;
    			
    			// 線を引く ※色(30, 30, 30, 180)
    			g2d.setColor(new Color(30, 30, 30, 180));
    			if(getApproPointY(i)>=yLower && getApproPointY(i+1)<=yUpper) {
    				g2d.draw(new Line2D.Float(xi, yi, xi1, yi1));
    			}
    		}	
    		/* ================================*/
        }
    }
    
    float getPointX(int i) {
    	return d.getPoint(i).x;
    }
    
    float getPointY(int i) {
    	return d.getPoint(i).y;
    }
    
    float getApproPointX(int i) {
		return (float)approPoints[i].data[0];
	}
	
	float getApproPointY(int i) {
		return (float)approPoints[i].data[1];
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