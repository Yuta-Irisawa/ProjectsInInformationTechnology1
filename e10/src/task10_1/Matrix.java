package task10_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Matrix {
	int rows = 0;
	int cols = 0;
	ArrayList<ArrayList<Double>> data= new ArrayList<ArrayList<Double>>();
	double[][] dataArray;
	boolean append = false;
	
	Matrix(){
		
	}
	
	Matrix(int rows, int cols){
		dataArray = new double[rows][cols];
	}
	
	boolean getAppend() {
		return append;
	}
	
	void setAppend(boolean bool) {
		append = bool;
	}
	
	void readFromFile(String fileName){
		try {
			FileReader fr = new FileReader(new File(fileName));
			BufferedReader br = new BufferedReader(fr);
			String line;
			
			while((line = br.readLine()) != null) {
				if(line.isEmpty()) {
					continue;
				}
				rows++;
				ArrayList<Double> col = new ArrayList<Double>();
				
				String[] values = line.trim().split("[\\s]+", 0);
				if(cols == 0) {
					cols = values.length;
				}else if(cols != values.length) {
					System.out.println(
							"Inconsistent column size at row: " + rows + "columns: " + values.length + " " + cols);
					System.exit(1);
				}
				
				for(int i = 0; i < cols; i++) {
					col.add(Double.parseDouble(values[i]));
				}
				
				data.add(col);
			}
			
			br.close();
			fr.close();
		}catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		
		dataArray = new double[rows][cols];
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				dataArray[i][j] = data.get(i).get(j);
			}
		}
	}

	void writeToFile(String fileName, boolean append) {
		try {
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < dataArray.length; i++) {
				for(int j = 0; j < dataArray[0].length; j++) {
					builder.append(dataArray[i][j]);
					builder.append(" ");
				}
				builder.append("\n");
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, append));
			bw.write(builder.toString());
			bw.close();
		}catch(IOException e){
			System.out.println(e);
			System.exit(1);
		}
	}
	
	static Matrix add(Matrix matrix1, Matrix matrix2) {
		System.out.println("a+b");
		int Ni = 0, Nj = 0;
		if(matrix1.rows != matrix2.rows) {
			System.out.println("Cannot calcurate add! Difference of rows.");
			System.exit(1);
		}else if(matrix1.cols != matrix1.cols) {
			System.out.println("Cannot calcurate add! Difference of cols.");
			System.exit(1);
		}else {
			Ni = matrix1.rows;
			Nj = matrix1.cols;
		}
		
		Matrix matrix = new Matrix(Ni, Nj);
		for(int i = 0; i < Ni; i++) {
			for(int j = 0; j < Nj; j++) {
				matrix.dataArray[i][j] = matrix1.dataArray[i][j] + matrix2.dataArray[i][j];
				System.out.print(matrix.dataArray[i][j] + " ");
			}
			System.out.println();
		}
		
		return matrix;
	}
	
	static Matrix sub(Matrix matrix1, Matrix matrix2) {
		System.out.println("a-b");
		int Ni = 0, Nj = 0;
		if(matrix1.rows != matrix2.rows) {
			System.out.println("Cannot calcurate add! Difference of rows.");
			System.exit(1);
		}else if(matrix1.cols != matrix1.cols) {
			System.out.println("Cannot calcurate add! Difference of cols.");
			System.exit(1);
		}else {
			Ni = matrix1.rows;
			Nj = matrix1.cols;
		}
		
		Matrix matrix = new Matrix(Ni, Nj);
		for(int i = 0; i < Ni; i++) {
			for(int j = 0; j < Nj; j++) {
				matrix.dataArray[i][j] = matrix1.dataArray[i][j] - matrix2.dataArray[i][j];
				System.out.print(matrix.dataArray[i][j] + " ");
			}
			System.out.println();
		}
		return matrix;
	}
	
	static Matrix mul(Matrix matrix1, Matrix matrix2) {
		System.out.println("a*b");
		int Ni = 0, Nj = 0;
		if(matrix1.rows != matrix2.rows) {
			System.out.println("Cannot calcurate add! Difference of rows.");
			System.exit(1);
		}else if(matrix1.cols != matrix1.cols) {
			System.out.println("Cannot calcurate add! Difference of cols.");
			System.exit(1);
		}else {
			Ni = matrix1.rows;
			Nj = matrix1.cols;
		}
		
		Matrix matrix = new Matrix(Ni, Nj);
		for(int i = 0; i < Ni; i++) {
			for(int j = 0; j < Nj; j++) {
				matrix.dataArray[i][j] = matrix1.dataArray[i][j] * matrix2.dataArray[i][j];
				System.out.print(matrix.dataArray[i][j] + " ");
			}
			System.out.println();
		}
		return matrix;
	}
	
	static Matrix dot(Matrix matrix1, Matrix matrix2) {
		System.out.println("a・b");
		int Ni = 0, Nj = 0, Nk = 0;
		if(matrix1.cols != matrix2.rows) {
			System.out.println("Cannot calcurate mul!");
			System.exit(1);
		}else {
			Ni = matrix1.rows;
			Nj = matrix2.cols;
			Nk = matrix1.cols;
		}
		
		Matrix matrix = new Matrix(matrix1.rows, matrix2.cols);
		for(int i = 0; i < Ni; i++) {
			for(int j = 0; j < Nj; j++) {
				for(int k = 0; k < Nk; k++) {
					matrix.dataArray[i][j] = matrix.dataArray[i][j] + matrix1.dataArray[i][k] * matrix2.dataArray[k][j];
				}
				System.out.print(matrix.dataArray[i][j] + " ");
			}
			System.out.println();
		}
		
		return matrix;
	}

	public static void main(String[] args) {
		if(args.length!=3) {
			System.out.println("Matrix2D [matrix1] [matrix2] [output_file]");
			System.exit(1);
		}
		Matrix matrix = new Matrix();
		Matrix matrix1 = new Matrix();
		Matrix matrix2 = new Matrix();
		matrix1.readFromFile(args[0]);
		matrix2.readFromFile(args[1]);
		String outputFileName = args[2];
		
		
		matrix = add(matrix1, matrix2);
		matrix.writeToFile(outputFileName, matrix.getAppend());
		
		matrix = sub(matrix1, matrix2);
		matrix.setAppend(true);
		matrix.writeToFile(outputFileName, matrix.getAppend());
		
		matrix = mul(matrix1, matrix2);
		matrix.setAppend(true);
		matrix.writeToFile(outputFileName, matrix.getAppend());
		
		matrix = dot(matrix1, matrix2);
		matrix.setAppend(true);
		matrix.writeToFile(outputFileName, matrix.getAppend());
	}
}
