// 15819013 Yuta Irisawa
package task9_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Matrix2D {
	int rows;
	int cols;
	ArrayList<ArrayList<Integer>> data = new ArrayList<ArrayList<Integer>>();	// ArrayList縺ｯ隕∫ｴ�謨ｰ縺悟庄螟峨〒縺ゅｋ縺九ｉ縺薙％縺ｧ蛻晄悄蛹�
	
	Matrix2D(){

	}
	
	Matrix2D(String fileName){
		Matrix2D matrix2d = new Matrix2D();
		readFromFile(fileName);
	}
	
	void readFromFile(String fileName){
		try {
			FileReader fr = new FileReader(new File(fileName));
			BufferedReader br = new BufferedReader(fr);
			String line;
			
			while((line = br.readLine()) != null) {
				rows++;
				ArrayList<Integer> row = new ArrayList<Integer>();
				
				String[] values = line.trim().split("[\\s]+", 0);
				if(cols == 0) {
					cols = values.length;
				}else if(cols != values.length) {
					System.out.println(
							"Inconsistent column size at row: " + rows + "columns: " + values.length + " " + cols);
					System.exit(1);
				}
				
				for(int i = 0; i < cols; i++) {
					row.add(Integer.parseInt(values[i]));
				}
				data.add(row);
			}
			
			br.close();
			fr.close();
		}catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}
	
	void writeToFile(String fileName) {
		try {
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < data.size(); i++) {
				for(int j = 0; j < data.get(0).size(); j++) {
					builder.append(data.get(i).get(j));
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
	
	void show() {
		for(int i = 0; i < data.size(); i++) {
			for(int j = 0; j < data.get(0).size(); j++) {
				System.out.print(data.get(i).get(j));
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		if(args.length!=2) {
			System.out.println("Matrix2D [input_file] [output_file]");
			System.exit(1);
		}
		
		Matrix2D matrix2d = new Matrix2D(args[0]);
		matrix2d.show();
		matrix2d.writeToFile(args[1]);
	}

}
