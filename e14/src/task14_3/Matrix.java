// 15819013 Yuta Irisawa
package task14_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Matrix {
	String fn;
	ArrayList<ArrayList<Double>> matrix=new ArrayList<ArrayList<Double>>();
	double data[][];
	int cols;
	int rows;

	Matrix(String s){
		cols=0;
		rows=0;
		fn=s;
		ReadFromFile(fn);
	}

	Matrix(int r,int c){
		this.rows=r;
		this.cols=c;
		this.data=new double[this.rows][this.cols];
	}

	void ReadFromFile(String filename){
		try { 
			BufferedReader br = new BufferedReader(new FileReader(filename));
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

				matrix.add(col);
			}

			br.close();
		}catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}

		data = new double[rows][cols];
		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				data[i][j]=matrix.get(i).get(j);
			}
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
			amax = Math.abs(data[k][k]);
			ip = k;
			for(int i=k+1; i<N; i++) {
				if(Math.abs(data[i][k])>amax) {
					amax = Math.abs(data[i][k]);
					ip = i;
				}
			}

			/* 行交換 */
			if(ip!=k) {
				// Aを行交換
				for(int j=k; j<N; j++) {
					tmp = data[k][j];
					data[k][j] = data[ip][j];
					data[ip][j] = tmp;
				}
				det *= -1; // 枢軸選択一回毎に-1をかける
			}

			/* 前進消去 */
			for(int i=k+1; i<N; i++) {
				alpha = - data[i][k] / data[k][k];
				for(int j=k+1; j<N; j++) {
					data[i][j] = data[i][j] + alpha * data[k][j];
				}
			}
			det *= data[k][k];	// 前進消去後のすべての対角要素をかける
		}

		return det;
	}
	
	static Matrix dot(Matrix matrix1, Matrix matrix2) {		
		int rows=matrix1.rows;
		int cols=matrix2.cols;
		Matrix matrix = new Matrix(rows, cols);
		
		for(int i=0;i<rows;i++) {		
			for(int j=0;j<cols;j++) {
				for(int k=0;k<cols;k++) {
					matrix.data[i][j] += matrix1.data[i][k] * matrix2.data[k][j];
				}
			}
		}
		
		return matrix;
	}


	public static void main(String[] args) {

	}
}