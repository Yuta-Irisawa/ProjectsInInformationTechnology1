// 15819013 Yuta Irisawa
package task11_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Matrix {
	int rows;
	int cols;
	ArrayList<ArrayList<Double>> data= new ArrayList<ArrayList<Double>>();
	double[][] dataArray;
	
	// constructor
	Matrix(){
		
	}
	
	Matrix(int rows, int cols){
		dataArray = new double[rows][cols];
		this.rows = rows;
		this.cols = cols;
	}
	
	void show(String s) {
		System.out.println(s);
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				System.out.print(" " + dataArray[i][j]);
			}
			System.out.println();
		}
	}

	void writeToFile(String fileName) {
		try {
			StringBuilder builder = new StringBuilder();
			
			for(int i = 0; i < dataArray.length; i++) {
				for(int j = 0; j < dataArray[0].length; j++) {
					builder.append(dataArray[i][j]);
					builder.append(" ");
				}
				builder.append("\n");
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			bw.write(builder.toString());
			bw.close();
		}catch(IOException e){
			System.out.println(e);
			System.exit(1);
		}
	}
}
