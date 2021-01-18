// 15819013 Yuta Irisawa
package task11_4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Matrix {
	int rows;
	int cols;
	ArrayList<ArrayList<Double>> data= new ArrayList<ArrayList<Double>>();
	double[][] dataArray;
	static double EPS = 1.0E-10;
	
	Matrix(String fileName){
		readFromFile(fileName);
	}
	
	Matrix(Matrix m){
		rows = m.rows;
		cols = m.cols;
		dataArray = new double[rows][cols];
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				dataArray[i][j] = m.dataArray[i][j];
			}
		}
	}
	
	Matrix(int rows, int cols){
		dataArray = new double[rows][cols];
		this.rows = rows;
		this.cols = cols;
	}
	
	void readFromFile(String fileName) {
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
		
		// ArrayList<ArrayList<Double>> data-> double[][] data (COPY)
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				dataArray[i][j] = data.get(i).get(j);
			}
		}
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
	
	Matrix inv() {
		Matrix rightMat = new Matrix(rows, cols);
		
		// rightMatを単位行列にする
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				if(i==j) {
					rightMat.dataArray[i][j] = 1;
				}else {
					rightMat.dataArray[i][j] = 0;
				}
			}
		}
		
		int N = rows;
		int ip;
		double amax;
		double tmp;
		double alpha;
		
		for(int k=0; k < N; k++) {
			//ピポットの選択
			amax = Math.abs(dataArray[k][k]);
			ip = k;
			for(int i=k+1; i<N; i++) {
				if(Math.abs(dataArray[i][k])>amax) {
					amax = Math.abs(dataArray[i][k]);
					ip = i;
				}
			}
			
			//正則性の判定
			if(amax<EPS) {
				System.out.println("入力した行列は正則ではない！！\n");
			}
			
			//行交換
			if(ip!=k) {
				// 左側を行交換
				for(int j=k; j<N; j++) {
					tmp = dataArray[k][j]; dataArray[k][j] = dataArray[ip][j]; dataArray[ip][j] = tmp;
				}
				// 右側を行交換
				for(int j=0; j<N; j++) {
					tmp = rightMat.dataArray[k][j]; rightMat.dataArray[k][j] = rightMat.dataArray[ip][j]; rightMat.dataArray[ip][j] = tmp;
				}
			}
			
			// 前進消去
			for(int i=0; i<N; i++) {
				if(i!=k) {
					alpha = - dataArray[i][k] / dataArray[k][k];
					for(int j=k; j<N; j++) {
						dataArray[i][j] = dataArray[i][j] + alpha * dataArray[k][j];
					}
					for(int j=0; j<N; j++) {
						rightMat.dataArray[i][j] = rightMat.dataArray[i][j] + alpha * rightMat.dataArray[k][j];
					}
				}
			}
		}
		
		//ピボットを1にする
		for(int k=0; k<N; k++) {
			double s = dataArray[k][k];
			for(int i=0; i<N; i++) {
				dataArray[k][i] = dataArray[k][i] / s;
				rightMat.dataArray[k][i] = rightMat.dataArray[k][i] / s;
			}
		}
		
		return rightMat;
	}
		
	static Matrix dot(Matrix matrix1, Matrix matrix2) {
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
			}
		}
		return matrix;
	}
	
	void writeToFile(String fileName) {
		try {
			StringBuilder builder = new StringBuilder();
			for(int i=0; i<rows; i++) {
				for(int j=0; j<cols; j++) {
					builder.append(" " + dataArray[i][j]);
				}
				builder.append("\n");
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(builder.toString());
			writer.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Matrix m = new Matrix(args[0]);
		Matrix mStock = new Matrix(m);
		
		Matrix mInv = m.inv();
		mInv.show("A^(-1)");
		mInv.writeToFile(args[1]);
		
		Matrix mDot1 = dot(mInv, mStock);
		mDot1.show("A^(-1)A");
		
		Matrix mDot2 = dot(mStock, mInv);
		mDot2.show("AA^(-1)");
	}
}
