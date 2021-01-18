//15819013 Yuta Irisawa
package p7;
import java.util.*;
import java.util.Map.Entry;

public class SortTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("apple", 10);
		map.put("banana", 7);
		map.put("lemon", 20);
//		System.out.println(map);
		List<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(map.entrySet());
		Collections.sort(entries, new Comparator<Entry<String, Integer>>(){
			public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2)
			{
//				System.out.println(obj2.getValue().compareTo(obj1.getValue()));
				return obj2.getValue().compareTo(obj1.getValue());
			}
		});
		for(Entry<String, Integer> entry : entries)
			System.out.println(entry.getKey() + "(" + entry.getValue() + ")");
	}

}
