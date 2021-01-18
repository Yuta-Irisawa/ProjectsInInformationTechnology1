// 15819013 Yuta Irisawa
package task13_1;

// ラグランジュ補間(MM11 task/program62.c)
public class Interpolation {
	
	public static double lagrange(Vector[] p, double x) {
		double y = 0;
		double li;
		
		// ラグランジュ補間多項式の計算
		for(int i=0; i<p.length; i++) {
			li = 1.0;
			for(int k=0; k<p.length; k++) {
				if(k!=i) {
					li *= (x - p[k].data[0]) / (p[i].data[0] - p[k].data[0]);
				}
			}
			y+=li*p[i].data[1];
		}
		return y;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
