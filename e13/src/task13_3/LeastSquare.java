// 15819013 Yuta Irisawa
package task13_3;

//�ŏ�2��ߎ�
public class LeastSquare {
	
	static Vector minimize(Vector[] points, int n) {
		Matrix coefMatrix = new Matrix(n+1, n+1);
		Vector rightVec = new Vector(n+1);
		Vector ansVec = new Vector(n+1);
		int pointNum = points[0].cols;
		Vector x = points[0];
		Vector y = points[1];
		
		// �E�Ӄx�N�g���̍쐬
		for(int i=0; i<n+1; i++) {
			rightVec.data[i] = 0;
			for(int j=0; j<pointNum; j++) {
				rightVec.data[i] += y.data[j] * Math.pow(x.data[j], i);
			}
//			System.out.println(rightVec.data[i]);
		}
		
		// �W���s��̍쐬
		for(int i=0; i<n+1; i++) {
			for(int j=0; j<i+1; j++) {
				coefMatrix.data[i][j] = 0;
				for(int k=0; k<pointNum; k++) {
					coefMatrix.data[i][j] += Math.pow(x.data[k], (double)(i+j));
				}
				coefMatrix.data[j][i] = coefMatrix.data[i][j];
			}
		}
		
		/* �A��1��������������. ���ʂ�ansVec�ɏ㏑�� */
		ansVec = LinearEq.solve(coefMatrix, rightVec);
		
		return ansVec;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
