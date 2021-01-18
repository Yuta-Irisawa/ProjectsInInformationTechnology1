//15819013 Yuta Irisawa
package p6;
import java.util.*;

public class ArrayListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		// ArrayList Declaration
		ArrayList<String> words = new ArrayList<String>();
		
		// add a new element to ArrayList words
		for(int i = 0; i < days.length; i++)
			words.add(days[i]);
		
		// get word from ArrayList words with get() method
		for(int i = 0; i < words.size(); i++)	// size() method is similar to length of array
			System.out.println(words.get(i));
		
		// remove word from ArrayList words with remove() method
		System.out.println("Remove Tuesday");
		words.remove("Tue");
		
		// Enhanced for loop
		// Upper for loop and this Enhanced for loop are the same 
		for(String x: words)
			System.out.println(x);
	}
}
