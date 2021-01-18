// 15819013 Yuta Irisawa
package task11_2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Vector {
	double[] data; // vector data‚ð•Û‘¶
	int rows;
	
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
	
	void writeToFile(String fileName) {
		try {
			StringBuilder builder = new StringBuilder();
			
			for(int i = 0; i < data.length; i++) {
				builder.append(data[i]);
				builder.append(" ");
			}
			builder.append("\n");
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			bw.write(builder.toString());
			bw.close();
		}catch(IOException e){
			System.out.println(e);
			System.exit(1);
		}
	}
	
	static Vector sub(Vector vec1, Vector vec2) {
		if(vec1.rows != vec2.rows) {
			System.out.println("—v‘f”‚ªˆÙ‚È‚é‚½‚ßAŒvŽZ‚Å‚«‚Ü‚¹‚ñ");
			System.exit(1);
		}
		Vector ans = new Vector(vec1.rows);
		for(int i=0; i<ans.rows; i++) {
			ans.data[i] = vec1.data[i] - vec2.data[i];
		}
		return ans;
	}
}
