// 15819013 Yuta Irisawa
package task12_1;

public class NonLinearEq {
	private int maxIter;
	static final double EPS = 1.0E-10;
	double ans;
	
	// Constructor
	NonLinearEq(double init_x){
		maxIter = 30;
		ans = solveNewton(init_x);
	}
	
	// ニュートン法により解を返すメソッド
	double solveNewton(double init_x){
		int n = 0;
		double d;
		double x = init_x;
		do {
			d = -f(x)/df(x);
			x = x + d;
			n++;
		}while(Math.abs(d) > EPS && n < maxIter);
		
		return x;
	}
	
	// getter
	double f(double x) {
		return x-Math.exp(-x);
	}
	
	double df(double x) {
		return 1+Math.exp(-x);
	}

	public static void main(String[] args) {
		NonLinearEq Ans;
		int failedNum = 0;
		for(int i=0; i<args.length; i++) {
			System.out.printf("searching x from %s", args[i]);			
			Ans = new NonLinearEq(Double.parseDouble(args[i]));
			if(Double.isNaN(Ans.ans)) {
				System.out.println(" failed.");
				failedNum++;
				continue;
			}
			System.out.println(", solution is " + Ans.ans + ".");
		}
		if(failedNum==args.length) {
			System.exit(1);
		}
	}
}
