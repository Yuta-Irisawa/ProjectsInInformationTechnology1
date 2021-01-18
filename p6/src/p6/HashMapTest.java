//15819013 Yuta Irisawa
package p6;
import java.util.*;

public class HashMapTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		int[] classes = {0, 2, 0, 2, 1, 3, 0};
		HashMap<String, Integer> weekly_class = new HashMap<String, Integer>();	// key : String , value : Integer
		
		for(int i = 0; i < days.length; i++)
			weekly_class.put(days[i], classes[i]);	// key : days[i] , value : classes[i]
		
		for(String x: days)
			System.out.printf("Number of classes on %s: %d\n", x, weekly_class.get(x));	// key : x
		
		weekly_class.put("Mon", 0);
		System.out.printf("Updated number of classes on Mon: %d\n", weekly_class.get("Mon"));
		
		Set<String> set = weekly_class.keySet();	// keySet()でkeyの集合(Set)として取得
		Iterator<String> it = set.iterator();
		while(it.hasNext()) {	// hasNext() : 次の要素がある→true, ない→false
			String day = it.next();	//next() : 次の要素を取得
			System.out.printf("Number of classes on %s: %d\n", day, weekly_class.get(day));
		}
		
//		for(String key: weekly_class.keySet())
//			System.out.printf("Number of classes on %s: %d\n", key, weekly_class.get(key));
	}

}
