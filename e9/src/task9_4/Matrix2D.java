// 15819013 Yuta Irisawa
package task9_4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Matrix2D {
	int rows = 0;
	int cols = 0;
	ArrayList<ArrayList<Double>> data= new ArrayList<ArrayList<Double>>();
	boolean append = false;
	
	// コンストラクタ
	Matrix2D(){
		
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
				
				String[] values = line.trim().split("[\\s]+", 0); // 1つor複数の空白で分割
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
	}
	
	void writeToFile(String fileName, boolean append) {
		try {
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < data.size(); i++) {
				for(int j = 0; j < data.get(0).size(); j++) {
					builder.append(data.get(i).get(j));
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
	
	static Matrix2D add(Matrix2D matrix1, Matrix2D matrix2) {
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
		
		Matrix2D matrix = new Matrix2D();
		for(int i = 0; i < Ni; i++) {
			ArrayList<Double> row = new ArrayList<Double>();
			for(int j = 0; j < Nj; j++) {
				double cal = matrix1.data.get(i).get(j) + matrix2.data.get(i).get(j);
				System.out.print(cal + " ");
				row.add(cal);
			}
			System.out.println();
			matrix.data.add(row);
		}
		
		return matrix;
	}
	
	static Matrix2D sub(Matrix2D matrix1, Matrix2D matrix2) {
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
		
		Matrix2D matrix = new Matrix2D();
		for(int i = 0; i < Ni; i++) {
			ArrayList<Double> row = new ArrayList<Double>();
			for(int j = 0; j < Nj; j++) {
				double cal = matrix1.data.get(i).get(j) - matrix2.data.get(i).get(j);
				System.out.print(cal + " ");
				row.add(cal);
			}
			System.out.println();
			matrix.data.add(row);
		}
		
		return matrix;
	}
	
	static Matrix2D mul(Matrix2D matrix1, Matrix2D matrix2) {
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
		
		Matrix2D matrix = new Matrix2D();
		for(int i = 0; i < Ni; i++) {
			ArrayList<Double> row = new ArrayList<Double>();
			for(int j = 0; j < Nj; j++) {
				double cal = matrix1.data.get(i).get(j) * matrix2.data.get(i).get(j);
				System.out.print(cal + " ");
				row.add(cal);
			}
			System.out.println();
			matrix.data.add(row);
		}
		
		return matrix;
	}
	
	static Matrix2D dot(Matrix2D matrix1, Matrix2D matrix2) {
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
		
		Matrix2D matrix = new Matrix2D();
		for(int i = 0; i < Ni; i++) {
			ArrayList<Double> row = new ArrayList<Double>();
			for(int j = 0; j < Nj; j++) {
				double cal = 0;
				for(int k = 0; k < Nk; k++) {
					cal +=  matrix1.data.get(i).get(k) * matrix2.data.get(k).get(j);
				}
				System.out.print(cal + " ");
				row.add(cal);
			}
			System.out.println();
			matrix.data.add(row);
		}
		
		return matrix;
	}

	public static void main(String[] args) {
		// コマンドライン引数の数のチェック
		if(args.length!=3) {
			System.out.println("Matrix2D [matrix1] [matrix2] [output_file]");
			System.exit(1);
		}
		Matrix2D matrix = new Matrix2D();
		Matrix2D matrix1 = new Matrix2D();
		Matrix2D matrix2 = new Matrix2D();
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
