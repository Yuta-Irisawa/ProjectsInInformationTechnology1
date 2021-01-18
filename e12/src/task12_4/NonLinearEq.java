// 15819013 Yuta Irisawa
package task12_4;

public class NonLinearEq {
	private int maxIter;
	static final double EPS = 1.0E-10;
	double ans;
	boolean fail;
	
	NonLinearEq(double init_x0, double init_x1){
		maxIter = 30;
		ans = solveSecant(init_x0, init_x1);
	}
	
	// 割線法により解を返すメソッド(07/task/program44.cを参考)
	double solveSecant(double x0, double x1) throws ArithmeticException {
		int n = 1;
		double d = x1-x0;
		double y0 = f(x0);
		double y1;

		try {
			do {
				y1 = f(x1);
				d = - d * y1 / (y1 - y0);
				x1 = x1 + d;
				y0 = y1;
				n++;
			}while(Math.abs(d) > EPS && n < maxIter);
			failsExceptionSecant(d, x1);
		}catch(ArithmeticException e) {
			System.out.println(e);
		}

		return x1;
	}
	
	// failの内容をこの中に付け加える(solveSecant())
	private void failsExceptionSecant(double d, double x) {
		if(Math.abs(d) > EPS) {
			fail = true;
			System.out.print(": ");
			throw new ArithmeticException("Secant method didn't converge after " + maxIter + " iteration.");
		}else if(Double.isNaN(x)) {
			fail = true;
			System.out.print(": ");
			throw new ArithmeticException("Secant method didn't converge after 1 iteration.");
		}
	}

	// getter
	static double f(double x) {
		return Math.exp(x) - Math.sin(Math.PI * x / 3.0);
	}

	public static void main(String[] args) {
		
	}
}
