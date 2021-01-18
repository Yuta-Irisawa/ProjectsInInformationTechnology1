//15819013 Yuta Irisawa
package p6;
import java.util.*;

public class LinkedListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		// LinkedList declaration
		LinkedList<String> words = new LinkedList<String>();
		
		for(String x: days)
			words.add(x);
		
		for(int i = 0; i < words.size(); i++)
			System.out.println(words.get(i));
		
		System.out.println("Remove Tuesday");
		words.remove("Tue");
		
		for(String x: words)
			System.out.println(x);
	}

}
