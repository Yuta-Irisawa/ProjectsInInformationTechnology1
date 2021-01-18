// 15819013 Yuta Irisawa
package task11_3;

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
	double[][] dataArrayStock;
	static double EPS = 1.0E-10;
	
	Matrix(String fileName){
		readFromFile(fileName);
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
		dataArrayStock = new double[rows][cols];
		
		// ArrayList<ArrayList<Double>> data-> double[][] data (COPY)
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				dataArray[i][j] = data.get(i).get(j);
				dataArrayStock[i][j] = dataArray[i][j];
			}
		}
	}
	
	void show() {
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				System.out.print(" " + dataArrayStock[i][j]);
			}
			System.out.println();
		}
	}
	
	double det() {
		int N = rows;
		int ip;
		double amax; // 列最大
		double tmp;
		double alpha;
		double det = 1;
		
		for(int k=0; k < N; k++) {
			/* ピポットの選択 */
			amax = Math.abs(dataArray[k][k]);
			ip = k;
			for(int i=k+1; i<N; i++) {
				if(Math.abs(dataArray[i][k])>amax) {
					amax = Math.abs(dataArray[i][k]);
					ip = i;
				}
			}
			
			/* 正則性の判定 */
			if(amax<EPS) {
				System.out.println("入力した行列は正則ではない！！\n");
			}
			
			/* 行交換 */
			if(ip!=k) {
				// Aを行交換
				for(int j=k; j<N; j++) {
					tmp = dataArray[k][j];
					dataArray[k][j] = dataArray[ip][j];
					dataArray[ip][j] = tmp;
				}
				det *= -1; // 枢軸選択一回毎に-1をかける
			}
			
			/* 前進消去 */
			for(int i=k+1; i<N; i++) {
				alpha = - dataArray[i][k] / dataArray[k][k];
				for(int j=k+1; j<N; j++) {
					dataArray[i][j] = dataArray[i][j] + alpha * dataArray[k][j];
				}
			}
			det *= dataArray[k][k];	// 前進消去後のすべての対角要素をかける
		}
		
		return det;
	}
	
	public static void main(String[] args) {
		Matrix m = new Matrix(args[0]);
		double det = m.det();
		m.show();
		System.out.println("Det: " + det);
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
			writer.write(String.valueOf(det));
			writer.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
