// 15819013 Yuta Irisawa
package task13_3;

public class LinearEq {
	static double EPS = 1.0E-10;

	// Gauss elimination�ɂ���ĕ�����������
	// ���̉����x�N�g���Ƃ��ĕԂ�(MM p47~48)
	static Vector solve(Matrix coefMatrix, Vector rightVec) {
		Matrix mData = coefMatrix;
		Vector vData = rightVec;
		int N = coefMatrix.rows;
		int ip;
		double amax; //��ő�
		double tmp;
		double alpha;

		for(int k=0; k < N; k++) {
			/* �s�|�b�g�̑I�� */
			amax = Math.abs(coefMatrix.data[k][k]);
			ip = k;
			for(int i=k+1; i<N; i++) {
				if(Math.abs(coefMatrix.data[i][k])>amax) {
					amax = Math.abs(coefMatrix.data[i][k]);
					ip = i;
				}
			}

			/* �������̔��� */
			if(amax<EPS) {
				System.out.println("���͂����s��͐����ł͂Ȃ��I�I\n");
			}

			/* �s���� */
			if(ip!=k) {
				// A���s����
				for(int j=k; j<N; j++) {
					tmp = mData.data[k][j];
					mData.data[k][j] = mData.data[ip][j];
					mData.data[ip][j] = tmp;
				}
				// b������
				tmp = vData.data[k];
				vData.data[k] = vData.data[ip];
				vData.data[ip] = tmp;
			}
			
			/* �O�i���� */
			for(int i=k+1; i<N; i++) {
				alpha = - mData.data[i][k] / mData.data[k][k];
				for(int j=k; j<N; j++) {
					mData.data[i][j] += alpha * mData.data[k][j];
				}
				vData.data[i] = vData.data[i] + alpha * vData.data[k];
			}
		}

		/* ��ޑ�� */
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
