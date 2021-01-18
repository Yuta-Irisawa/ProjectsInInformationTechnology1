// 15819013 Yuta Irisawa
package task13_1;

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
	
	public static void main(String[] args) {

	}
}