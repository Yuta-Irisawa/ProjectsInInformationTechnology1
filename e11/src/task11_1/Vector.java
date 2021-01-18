// 15819013 Yuta Irisawa
package task11_1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Vector {
	double[] data; // vector data‚ð•Û‘¶
	boolean append = false;
	int rows = 0;
	
	Vector(){
		
	}
	
	Vector(int rows){
		data = new double[rows];
		this.rows = rows;
	}
	
	void show(String s) {
		System.out.println(s);
		for(int i=0; i<rows; i++) {
			System.out.print(data[i] + " ");
		}
		System.out.println();
	}
	
	void writeToFile(String fileName, boolean append) {
		try {
			StringBuilder builder = new StringBuilder();
			
			for(int i = 0; i < data.length; i++) {
				builder.append(data[i]);
				builder.append(" ");
			}
			builder.append("\n");
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, append));
			bw.write(builder.toString());
			bw.close();
		}catch(IOException e){
			System.out.println(e);
			System.exit(1);
		}
	}
}
