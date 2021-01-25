// 15819013 Yuta Irisawa
package task14_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CSVReader {
	Matrix mat;
	String[] dataLabel;
	ArrayList<ArrayList<Double>> matrix=new ArrayList<ArrayList<Double>>();
	ArrayList<String> s = new ArrayList<String>();
	int cols=4;
	int rows;

	CSVReader(String filename){
		read(filename);
	}

	void read(String filename) {
		try { 
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while((line = br.readLine()) != null) {
				if(line.isEmpty()) {
					continue;
				}
				rows++;
				ArrayList<Double> col = new ArrayList<Double>();


				String[] values = line.split(",");

				for(int i = 0; i < cols+1; i++) {
					if(i<cols) {
						col.add(Double.parseDouble(values[i]));
					}else {
						s.add(values[i]);
					}
				}

				matrix.add(col);
			}

			br.close();
		}catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}

		mat = new Matrix(rows, cols);
		dataLabel = new String[rows];
		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				mat.data[i][j]=matrix.get(i).get(j);
			}
			dataLabel[i] = s.get(i);
		}
	}

	double getMatData(int i, int j) {
		return mat.data[i][j];
	}

	String getDataLabel(int i) {
		return dataLabel[i];
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CSVReader c = new CSVReader("iris.data");

		for(int i=0; i<c.rows; i++) {
			for(int j=0; j<c.cols; j++) {
				System.out.print(c.getMatData(i,j) + " ");
			}
			System.out.println(c.getDataLabel(i));
		}
	}
}
