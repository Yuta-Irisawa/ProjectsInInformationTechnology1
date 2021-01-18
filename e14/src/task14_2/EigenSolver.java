// 15819013 Yuta Irisawa
package task14_2;

public class EigenSolver {
	private Vector eigenValues;
	private Matrix eigenVectors;
	int maxIter = 100;
	static double EPS = 1.0E-10;

	EigenSolver(Matrix points){
		solve(points);
	}

	void solve(Matrix a) {
//		// rowとcolが違ったらエラー出力
//		for(int i=0; i<a.rows;i++) {
//			for(int j=0; j<a.cols;j++) {
//				if(a.data[i][j]!=a.data[j][i]) {
//					System.out.println("Input matrix is not symmetric.");
//					System.exit(1);
//				}
//			}
//		}

		int m = 0, k = 0;	/*index of rotated elements */
		int it;		/* iteration counter */

		double s;	/* the maximum element */
		double t, u;
		double cos_t, sin_t;

		int n = a.rows;

		Matrix r = new Matrix(n, n);
		Vector a_row_m = new Vector(n);
		Vector a_row_k = new Vector(n);
		Vector a_col_m = new Vector(n);
		Vector a_col_k = new Vector(n);

		/* initialize eigenvalues x and eigenvector R */
		/* 単位行列を作る */
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				r.data[i][j] = 0.0;
			}
			r.data[i][i] = 1.0;
		}

		/* repeat until all no diagonal elements are zero */
		for(it=0; it<maxIter; it++) {
			eigenValues = new Vector(n);
			eigenVectors = new Matrix(n, n);
			/* check ending condition (whether A*x is zero or not),
		       and find the maximum element a[m][k] */
			s = EPS;
			for(int i=0; i<n; i++) {
				for(int j=i+1; j<n; j++) {
					if(Math.abs(a.data[i][j]) > s) {
						s = Math.abs(a.data[i][j]);
						k = i;
						m = j;
					}
				}
			}
			if(s==EPS) {
				for(int i=0; i<n; i++) {
					for(int j=0; j<n; j++) {
						eigenVectors.data[i][j] = r.data[i][j];
					}
					eigenValues.data[i] = a.data[i][i];
				}
				break;
			}

			/* calcurate rotation parameter t(theta), cos(t), and sin(t) */
			if(Math.abs(a.data[k][k] - a.data[m][m]) < EPS) {
				cos_t = 1 / Math.sqrt(2.0);
				sin_t = a.data[k][m] > 0.0 ? 1.0 / Math.sqrt(2.0) : -1.0 / Math.sqrt(2.0);
			}else {
				t = (2.0 * a.data[k][m]) / (a.data[k][k] - a.data[m][m]);
				u = Math.sqrt(1.0 + t * t);
				cos_t = Math.sqrt((1.0 + u)/(2.0 * u));
				sin_t = Math.sqrt((-1.0 + u)/(2.0 * u)) * (t>0.0 ? 1.0 : -1.0);
			}

			/* apply R'*A*R */
			for(int j=0; j<n; j++) {
				a_row_k.data[j] = a.data[k][j] * cos_t + a.data[m][j] * sin_t;
				a_row_m.data[j] = -1.0 * a.data[k][j] * sin_t + a.data[m][j] * cos_t;
			}
			for(int i=0; i<n; i++) {
				a_col_k.data[i] = a.data[i][k] * cos_t + a.data[i][m] * sin_t;
				a_col_m.data[i] = -1.0 * a.data[i][k] * sin_t + a.data[i][m] * cos_t;
			}
			a_col_k.data[k] = a.data[k][k] * cos_t * cos_t 
					+ a.data[k][m] * cos_t * sin_t 
					+ a.data[m][k] * cos_t * sin_t
					+ a.data[m][m] * sin_t * sin_t;
			a_col_m.data[m] = a.data[k][k] * sin_t * sin_t 
					- a.data[m][k] * cos_t * sin_t
					- a.data[k][m] * cos_t * sin_t 
					+ a.data[m][m] * cos_t * cos_t;
			a_col_k.data[m] = 0.0;
			a_col_m.data[k] = 0.0;
			for (int j=0; j<n; j++){
				a.data[k][j] = a_row_k.data[j];
				a.data[m][j] = a_row_m.data[j];
			}
			for (int i=0; i<n; i++){
				a.data[i][k] = a_col_k.data[i];
				a.data[i][m] = a_col_m.data[i];
			}

			/* apply X*R */
			for(int i=0; i<n; i++) {
				a_col_k.data[i] = r.data[i][k] * cos_t + r.data[i][m] * sin_t;
				a_col_m.data[i] = -1.0 * r.data[i][k] * sin_t + r.data[i][m] * cos_t;
			}
			for(int i=0; i<n; i++) {
				r.data[i][k] = a_col_k.data[i];
				r.data[i][m] = a_col_m.data[i];
			}
		}
	}

	public static void main(String[] args) {
		Matrix a = new Matrix(args[0]);
		EigenSolver e = new EigenSolver(a);

		int n = a.rows;
		a = new Matrix(args[0]);

		for(int i=0; i<n; i++) {
			System.out.println("===========     " + "lambda" + (i+1) + "     ===========");
			System.out.println("A - lambda * I");
			Matrix ans = new Matrix(n, n);
			for(int j=0; j<n; j++){
				for(int k=0; k<n; k++){
					if(j==k) {
						ans.data[j][k]=a.data[j][k]-e.eigenValues.data[i];
					}else {
						ans.data[j][k]=a.data[j][k];
					}
					System.out.printf(ans.data[j][k]+" ");
				}
				System.out.println();
			}

			System.out.println("det(A - lambda * I)="+ans.det(EPS));

			for(int j=0; j<n; j++){
				for(int k=0; k<n; k++){
					if(j==k) {
						ans.data[j][k]=a.data[j][k]-e.eigenValues.data[i];
					}else {
						ans.data[j][k]=a.data[j][k];
					}
				}
			}

			System.out.println("(A - lambda * I)x");
			for(int j=0; j<n; j++){
				double sum=0.0;
				for(int k=0; k<n; k++){
					sum+= ans.data[j][k]*e.eigenVectors.data[k][i];
				}
				System.out.printf(sum+" ");
			}
			System.out.println();
		}
	}
}