// 15819013 Yuta Irisawa
package task13_3;

public class LinearEq {
	static double EPS = 1.0E-10;

	// Gauss eliminationによって方程式を解く
	// その解をベクトルとして返す(MM p47~48)
	static Vector solve(Matrix coefMatrix, Vector rightVec) {
		Matrix mData = coefMatrix;
		Vector vData = rightVec;
		int N = coefMatrix.rows;
		int ip;
		double amax; //列最大
		double tmp;
		double alpha;

		for(int k=0; k < N; k++) {
			/* ピポットの選択 */
			amax = Math.abs(coefMatrix.data[k][k]);
			ip = k;
			for(int i=k+1; i<N; i++) {
				if(Math.abs(coefMatrix.data[i][k])>amax) {
					amax = Math.abs(coefMatrix.data[i][k]);
					ip = i;
				}
			}

			/* 正則性の判定 */
			if(amax<EPS) {
				System.out.println("入力した行列は正則ではない！！\n");
			}

			/* 行交換 */
			if(ip!=k) {
				// Aを行交換
				for(int j=k; j<N; j++) {
					tmp = mData.data[k][j];
					mData.data[k][j] = mData.data[ip][j];
					mData.data[ip][j] = tmp;
				}
				// bを交換
				tmp = vData.data[k];
				vData.data[k] = vData.data[ip];
				vData.data[ip] = tmp;
			}
			
			/* 前進消去 */
			for(int i=k+1; i<N; i++) {
				alpha = - mData.data[i][k] / mData.data[k][k];
				for(int j=k; j<N; j++) {
					mData.data[i][j] += alpha * mData.data[k][j];
				}
				vData.data[i] = vData.data[i] + alpha * vData.data[k];
			}
		}

		/* 後退代入 */
		vData.data[N-1] = vData.data[N-1]/mData.data[N-1][N-1];
		for(int k=N-2; k>=0; k--) {
			tmp = vData.data[k];
			for(int j=k+1; j<N; j++) {
				tmp = tmp - mData.data[k][j] * vData.data[j];
			}
			vData.data[k] = tmp / mData.data[k][k];
		}
		
//		for(int i=0; i<N; i++) {
//			System.out.println(vData.data[i]);
//		}

		return vData;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
