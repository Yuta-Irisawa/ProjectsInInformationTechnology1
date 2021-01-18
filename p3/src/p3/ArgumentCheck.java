package p3;

public class ArgumentCheck {
	int diff;
	
	void addValue(int v) {
		v = v + diff;
		System.out.println("Value of v in addValue: " + v);
	}
	
	void addValues(int[] u) {
		for(int i = 0; i < u.length; i++) {
			u[i] += diff;
			System.out.printf("Value of %d-th value of array u in addValue: %d\n", i,u[i]);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArgumentCheck test = new ArgumentCheck();
		test.diff = 2;
		int v = 10;
		int[] u = {1, 2, 3, 4, 5};
		
		// 値渡し
		System.out.println("Value of v in main: " + v);
		test.addValue(v);
		System.out.println("Value of v in main: " + v); //2行上と変わらない
		
		// 参照渡し
		for(int i = 0; i < u.length; i++) 
			System.out.printf("Value of %d-th value of array u in addValue: %d\n", i,u[i]);
		test.addValues(u);
		for(int i = 0; i < u.length; i++) 
			System.out.printf("Value of %d-th value of array u in addValue: %d\n", i,u[i]); //addValues内と同じ
	}

}
