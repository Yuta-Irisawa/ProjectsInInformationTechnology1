// 15819013 Yuta Irisawa
package task11_1;

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
	Matrix aug_matrix; // �g��W���s��

	// constructor
	public LinearEq(String inputFile) {
		readFromFile(inputFile);
	}
	
	// getter
	Matrix A() {
		return mData;
	}
	
	Vector b() {
		return vData;
	}
	
	// method
	void readFromFile(String inputFileName) {
		try {
			FileReader fr = new FileReader(new File(inputFileName));
			BufferedReader br = new BufferedReader(fr);
			String line;
			
			// 1�s�ڂŗ�̐����擾����
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
			
			// Matrix���擾
			for(int row=0; row<rows; row++) {
				line = br.readLine();
				ArrayList<Double> col1 = new ArrayList<Double>();
				
				String[] values1 = line.trim().split("[\\s]+", 0);
				for(int i=0; i<cols; i++) {
					col1.add(Double.parseDouble(values1[i]));
				}
				mData.data.add(col1);
			}
			
			// Vector���擾
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
		
		// Matrix��dataArray�ɒl����
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				mData.dataArray[i][j] = mData.data.get(i).get(j);
			}
		}
		
		// Vector��data�ɒl����
		vData = new Vector(rows);
		
		for(int i=0; i<rows; i++) {
			vData.data[i] = vec.get(i);
		}
		
		// aug_matrix���쐬
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
	}

	public static void main(String[] args) {
		LinearEq solver = new LinearEq(args[0]);
		solver.A().show("A");
		solver.b().show("b");
		solver.aug_matrix.show("Augmented Matrix");
		
		solver.A().writeToFile(args[1]);
		solver.b().writeToFile(args[1], true);
	}
}
