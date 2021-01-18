// 15819013 Yuta Irisawa
package task13_4;

import java.io.File;
import java.io.IOException;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.ImageIcon;

public class Image{

	static BufferedImage readImage(String fileName) {
		BufferedImage img = null;
		try{
			File f = new File(fileName); //image file path
			img = ImageIO.read(f);
			System.out.println("Reading complete.");
		}catch(IOException e){
			System.out.println("Error: "+e);
		}
		
		return img;
	}
	
	static void writeImage(BufferedImage img, String fileName) {
		try{
			File output = new File(fileName);  //output file path
			ImageIO.write(img, "jpg", output);
			System.out.println("Writing complete.");
		}catch(IOException e){
			System.out.println("Error: "+e);
		}
	}
	
	static BufferedImage convertGrayscale(BufferedImage img, int width, int height) {
		BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				int p = img.getRGB(x,y);

				int a = (p>>24)&0xff;
				int r = (p>>16)&0xff;
				int g = (p>>8)&0xff;
				int b = p&0xff;

				//calculate average
				int avg = (r+g+b)/3;

				//replace RGB value with avg
				p = (a<<24) | (avg<<16) | (avg<<8) | avg;

				grayImage.setRGB(x, y, p);
			}
		}
//		showImage(grayImage, width, height);
		writeImage(grayImage, "output_grayscale.png");
		return grayImage;
	}
	
	static BufferedImage flipHorizontally(BufferedImage img, int width, int height) {
		BufferedImage flipped = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				flipped.setRGB((width - 1) - x, y, img.getRGB(x, y));
			}
		}
//		showImage(flipped, width, height);
		writeImage(flipped, "output_flipHorizontally.png");
		return flipped;
	}
	
	static BufferedImage flipVertically(BufferedImage img, int width, int height) {
		BufferedImage flipped = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				flipped.setRGB(x, (height - 1) - y, img.getRGB(x, y));
			}
		}
//		showImage(flipped, width, height);
		writeImage(flipped, "output_flipVertically.png");
		return flipped;
	}
	
	static void concatenateImg(BufferedImage leftImg, BufferedImage rightImg) {
		int widthTotal = leftImg.getWidth() + rightImg.getWidth();
		int widthCurr = 0;
        BufferedImage concatImage = new BufferedImage(widthTotal, leftImg.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = concatImage.createGraphics();
        g2d.drawImage(leftImg, widthCurr, 0, null);
        widthCurr += leftImg.getWidth();
        g2d.drawImage(rightImg, widthCurr, 0, null);
        widthCurr += rightImg.getWidth();
        g2d.dispose();
        
//        showImage(concatImage, concatImage.getWidth(), concatImage.getHeight());
        writeImage(concatImage, "output_concatenate.png");
	}
	
	static void showImage(BufferedImage img, int width, int height) {
		Graphics grp = img.getGraphics();
		grp.drawString("original image", 20,20);
		JFrame frame = new JFrame();
		frame.setSize(width, height);
		frame.getContentPane().add(new JLabel(new ImageIcon(img)));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String args[]) throws IOException{
		if ( args.length <= 0 ) {
			System.out.println("image file name must be specified as command line argument");
			System.exit(1);
		}
		
		BufferedImage img = readImage(args[0]);	// read image ‚È‚Ç
		int width = img.getWidth();	//get image width
		int height = img.getHeight();	//get image height
		
		BufferedImage flipH = flipHorizontally(img, width, height);
		BufferedImage flipV = flipVertically(img, width, height);
		BufferedImage gray = convertGrayscale(img, width, height); // process the image || convert to grayscale		
		
		concatenateImg(flipH, flipV);
	}
}