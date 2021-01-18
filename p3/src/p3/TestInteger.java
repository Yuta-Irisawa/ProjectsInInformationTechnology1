//15819013 Yuta Irisawa
package p3;

public class TestInteger {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int v = 5;
		
		String s = Integer.toString(v);
		System.out.println("v = " + s);
		System.out.println("v = " + v); //implicit conversion
		
		Integer U = 100; //boxing conversion(Integer U = new Integer(100))
		int u = U.intValue();
		System.out.println("u = " + u);
		u = U; //unboxing conversion(int u = U.intValue())
		System.out.println("u = " + u);
		u = Integer.valueOf("123"); // = atoi() (unboxing conversion)
		u += 100;
		System.out.println("u = " + u);
	}

}
