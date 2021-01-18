// 15819013 Yuta Irisawa
package task11_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class LinearEq {
	Matrix mData;
	Vector vData;
	int cols;
	int rows;
	ArrayList<Double> vec;
	Matrix aug_matrix; // 拡大係数行列
	static double EPS = 1.0E-10;
	
	double[][] data;	// 拡大係数行列をデータとして保管しておく(mDataとvDataは前進消去によって変化するから)

	// constructor
	public LinearEq(String inputFile) {
		data = readFromFile(inputFile);
	}
	
	// getter
	Matrix A() {
		Matrix A = new Matrix(data.length, data.length);
		for(int i=0; i<A.rows; i++) {
			for(int j=0; j<A.cols; j++) {
				A.dataArray[i][j] = data[i][j];
			}
		}
		return A;
	}
	
	Vector b() {
		Vector b = new Vector(data.length);
		for(int i=0; i<b.rows; i++) {
			b.data[i] = data[i][data[0].length-1];
		}
		return b;
	}
	
	// method
	double[][] readFromFile(String inputFileName) {
		try {
			FileReader fr = new FileReader(new File(inputFileName));
			BufferedReader br = new BufferedReader(fr);
			String line;
			
			// 1行目で列の数を取得する
			line = br.readLine();
			ArrayList<Double> col = new ArrayList<Double>();
			
			String[] values = line.trim().split("[\\s]+", 0);
			cols = values.length;
			rows = cols;
			
			for(int i=0; i<cols; i++) {
				col.add(Double.parseDouble(values[i]));
			}
			
			mData = new Matrix(rows, cols);
			
			mData.data.add(col);
			
			// Matrixを取得
			for(int row=0; row<rows; row++) {
				line = br.readLine();
				ArrayList<Double> col1 = new ArrayList<Double>();
				
				String[] values1 = line.trim().split("[\\s]+", 0);
				for(int i=0; i<cols; i++) {
					col1.add(Double.parseDouble(values1[i]));
				}
				mData.data.add(col1);
			}
			
			// Vectorを取得
			vec = new ArrayList<Double>();
			
			String[] values2 = line.trim().split("[\\s]+", 0);
			for(int i=0; i<cols; i++) {
				vec.add(Double.parseDouble(values2[i]));
			}
			
			br.close();
			fr.close();
		}catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
		
		// MatrixのdataArrayに値を代入
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				mData.dataArray[i][j] = mData.data.get(i).get(j);
			}
		}
		
		// Vectorのdataに値を代入
		vData = new Vector(rows);
		
		for(int i=0; i<rows; i++) {
			vData.data[i] = vec.get(i);
		}
		
		// aug_matrixを作成
		aug_matrix = new Matrix(rows, cols + 1);
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols+1; j++) {
				if(j<cols) {
					aug_matrix.dataArray[i][j] = mData.dataArray[i][j];
				}else {
					aug_matrix.dataArray[i][j] = vData.data[i];
				}
			}
		}
		return aug_matrix.dataArray;
	}
	
	// Gauss eliminationによって方程式を解く
	// その解をベクトルとして返す(MM p47~48)
	Vector solve() {
		int N = mData.rows;
		int ip;
		double amax; //列最大
		double tmp;
		double alpha;
		
		for(int k=0; k < N; k++) {
			/* ピポットの選択 */
			amax = Math.abs(mData.dataArray[k][k]);
			ip = k;
			for(int i=k+1; i<N; i++) {
				if(Math.abs(mData.dataArray[i][k])>amax) {
					amax = Math.abs(mData.dataArray[i][k]);
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
					tmp = mData.dataArray[k][j];
					mData.dataArray[k][j] = mData.dataArray[ip][j];
					mData.dataArray[ip][j] = tmp;
				}
				// bを交換
				tmp = vData.data[k];
				vData.data[k] = vData.data[ip];
				vData.data[ip] = tmp;
			}
			
			/* 前進消去 */
			for(int i=k+1; i<N; i++) {
				alpha = - mData.dataArray[i][k] / mData.dataArray[k][k];
				for(int j=k+1; j<N; j++) {
					mData.dataArray[i][j] = mData.dataArray[i][j] + alpha * mData.dataArray[k][j];
				}
				vData.data[i] = vData.data[i] + alpha * vData.data[k];
			}
		}
		
		/* 後退代入 */
		vData.data[N-1] = vData.data[N-1]/mData.dataArray[N-1][N-1];
		for(int k=N-2; k>=0; k--) {
			tmp = vData.data[k];
			for(int j=k+1; j<N; j++) {
				tmp = tmp - mData.dataArray[k][j] * vData.data[j];
			}
			vData.data[k] = tmp / mData.dataArray[k][k];
		}
		
		return vData;
	}
	
	public static void main(String[] args) {
		LinearEq solver = new LinearEq(args[0]);
		solver.A().show("A");
		solver.b().show("b");
		solver.aug_matrix.show("Augmented Matrix");
		
		Vector solution = solver.solve();
		solution.show("solution");
		Vector.sub(solver.A().dot(solution), solver.b()).show("Ax-b"); // 本当にAx=bになったか確かめるためにAx-b=零ベクトルになるかどうかを調査する
		solution.writeToFile(args[1]);
	}
}
