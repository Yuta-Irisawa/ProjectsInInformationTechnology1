// 15819013 Yuta Irisawa
package task12_3;

import java.awt.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Line2D.Float;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GraphPanel extends JPanel{
	Matrix points;	// plot����_�̍s��
	double x1, x2;	// �\���͈�(x1<x<x2)
	int d;	// d�̓_
	
	int beginX = 60;
	int beginY = 30;
	int width = 510;
	int height = 510;
	int endX = beginX + width;
	int endY = beginY + height;
	float diam = 4; // �v���b�g�̓_(�~)�̒��a
	
	GraphPanel(double x1, double x2, int d){
		points = new Matrix(d, 2); //d�s2�������āA(x, y)�̑g���쐬
		double _x = (x2 - x1) / (d - 1);
		
		for(int i=0; i<d; i++) {
			points.data[i][0] = x1 + i * _x;
			points.data[i][1] = NonLinearEq.f(points.data[i][0]);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setPaint(Color.black);
		g2d.setStroke(new BasicStroke(2));
		
		float xLower = getXLower();
		float xUpper = getXUpper();
		float yLower = getYLower();
		float yUpper = getYUpper();
		
		float dx = xUpper - xLower;
		float dy = yUpper - yLower;
		
		float xInterval = dx / 10;	// �Ԃ̕���
		float yInterval = dy / 10;
		
		float x0 = beginX + width * xUpper / dx;
		float y0 = beginY + height * yUpper / dy;
		
		/* ========= �v���b�g�O�̘g�g�쐬 =========*/
		g2d.setPaint(Color.white);
		g.fillRect(beginX, beginY, width, height);	// (����x, ����y, ��, ����)
		
		g2d.setPaint(Color.black);
		g2d.draw(new Line2D.Float(beginX, endY, beginX, beginY));	// (�nx, �ny, �Ix, �Iy) �c�ڐ���
		g2d.draw(new Line2D.Float(beginX, endY, endX, endY));	// ���ڐ���

		int num = 0;
		for(float x=beginX; x<=endX; x+=width*xInterval/dx){
			g2d.setPaint(new Color(200, 200, 200, 200));
			if(num!=0) {
				g2d.draw(new Line2D.Float(x, endY, x, beginY));	// �Ԃ̏c��
			}
			g2d.setPaint(Color.black);
			g2d.draw(new Line2D.Float(x, endY, x, endY-5));	// ���̋�؂�
			drawCenteredString(g2d, String.format("%.1f", (xLower + xInterval * (float)num)), x, endY+10, 0);
			num++;
		}
		
		num = 0;
		for(float y=beginY; y<=endY; y+=height*yInterval/dy) {
			g2d.setPaint(new Color(200, 200, 200, 200));
			if(num!=10) {
				g2d.draw(new Line2D.Float(beginX, y, endX, y));	// �Ԃ̉���
			}
        	g2d.setPaint(Color.black);
        	g2d.draw(new Line2D.Float(beginX, y, beginX+5, y));	// ���̋�؂�
            drawCenteredString(g2d, String.format("%.1f",(yUpper - yInterval*(float)num)), beginX-20,  y-3, 0);
            num++;
		}
		
		g2d.setPaint(new Color(35, 75, 35, 255));
		g2d.draw(new Line2D.Float(beginX, y0, endX, y0));	// x��
		/* ===================================*/
		
		/* ============= �v���b�g��� ============= */
		int pointsNum = getNumOfPoints();
		
		for(int i=0; i<pointsNum-1; i++) {
			g2d.setPaint(new Color(30, 30, 30, 180));
			float xi = width * (getPointX(i) - xLower) / dx + beginX;
			float yi = -height * (getPointY(i) - yLower) / dy + endY;
			float xi1 = width * (getPointX(i+1) - xLower) / dx + beginX;
			float yi1 = -height * (getPointY(i+1) - yLower) / dy + endY;
			
			// �_�Ɠ_�����Ԑ������ň���
			g2d.draw(new Line2D.Float(xi, yi, xi1, yi1));
			
			xi -= diam / 2;
			yi -= diam / 2;
			
			// �ד��m�̍��W��+-���قȂ�ꍇ
			if(getPointY(i)>0 && getPointY(i+1)<0 || getPointY(i)<0 && getPointY(i+1)>0) {
				NonLinearEq Ans = new NonLinearEq(getPointX(i+1));
				double solution_x = Ans.ans;
				double solution_y = Ans.f(solution_x);
				
				solution_x = width * (solution_x - xLower) / dx + beginX;
				solution_x -= diam / 2;
				solution_y = -height * (solution_y - yLower) / dy + endY;
				solution_y -= diam / 2;
				
				// �������ň���
				g2d.setPaint(new Color(140, 40, 140, 255));
				g2d.draw(new Line2D.Float(xi+diam/2, yi+diam/2, xi1, yi1));
				
				// �_���v���b�g
				g2d.setColor(Color.green);
				g2d.fill(new Ellipse2D.Double(solution_x, solution_y, diam, diam));
			}
		}
		
		// �ΈȊO�̓_���v���b�g(x>0�F��, x<0�F��) 
		for(int i=0; i<pointsNum; i++) {
			float xi = width * (getPointX(i) - xLower) / dx + beginX;
			xi -= diam / 2;
			float yi = -height * (getPointY(i) - yLower) / dy + endY;
			yi -= diam / 2;
			
			if(getPointY(i)>0) {
				g2d.setPaint(Color.red);
			}else if(getPointY(i)<0) {
				g2d.setPaint(Color.blue);
			}
			
			g2d.fill(new Ellipse2D.Float(xi, yi, diam, diam));
		}
		/* ===================================*/
	}
	
	int getNumOfPoints() {
		return points.data.length;
	}
	
	float getXLower() {
		return (float)points.data[0][0];
	}
	
	float getXUpper() {
		return (float)points.data[getNumOfPoints()-1][0];
	}
	
	float getYLower() {
		float yLower = (float)points.data[0][1];
		for(int i=1; i<getNumOfPoints(); i++) {
			if(yLower>(float)points.data[i][1]) {
				yLower = (float)points.data[i][1];
			}
		}
		return yLower;
	}
	
	float getYUpper() {
		float yUpper = (float)points.data[0][1];
		for(int i=1; i<getNumOfPoints(); i++) {
			if(yUpper<(float)points.data[i][1]) {
				yUpper = (float)points.data[i][1];
			}
		}
		return yUpper;
	}
	
	private void drawCenteredString(Graphics2D g2d, String string,
			float x0, float y0, double angle) {
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
	
	float getPointX(int i) {
		return (float)points.data[i][0];
	}
	
	float getPointY(int i) {
		return (float)points.data[i][1];
	}
	
	public void saveImage(Plot2D plot, String fname) {
		Container frame = plot.getContentPane();
		BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		frame.printAll(g);
		g.dispose();
		
		try {
			ImageIO.write(image, "png", new File(fname));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
