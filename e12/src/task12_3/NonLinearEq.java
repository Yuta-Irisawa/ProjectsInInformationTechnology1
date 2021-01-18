// 15819013 Yuta Irisawa
package task12_3;

public class NonLinearEq {
	private int maxIter;
	static final double EPS = 1.0E-10;
	double ans;
	boolean fail = false;
	
	// Constructor
	NonLinearEq(double init_x){
		maxIter = 30;
		ans = solveNewton(init_x);
	}
	
	// ニュートン法により解を返すメソッド
	double solveNewton(double init_x) throws ArithmeticException{
		int n = 0;
		double d;
		double x = init_x;

		try {
			do {
				d = -f(x)/df(x);
				x = x + d;
				n++;
			}while(Math.abs(d) > EPS && n < maxIter);
			failsException(d, x);
		}catch(ArithmeticException e) {
			System.out.println(e);
		}
		
		return x;
	}
	
	// failの内容をこの中に付け加える
	private void failsException(double d, double x) {
		if(Math.abs(d) > EPS) {
			fail = true;
			System.out.print(": ");
			throw new ArithmeticException("Newton method didn't converge after " + maxIter + " iteration.");
		}else if(Double.isNaN(x)) {
			fail = true;
			System.out.print(": ");
			throw new ArithmeticException("Newton method didn't converge after 1 iteration.");
		}
	}

	// getter
	static double f(double x) {
		return Math.exp(x) - Math.sin(Math.PI * x / 3.0);
	}
	
	double df(double x) {
		return Math.exp(x) - Math.PI / 3.0 * Math.cos(Math.PI * x / 3.0);
	}

	public static void main(String[] args) {
		
	}
}
