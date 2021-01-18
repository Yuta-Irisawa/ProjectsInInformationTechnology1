//15819013 Yuta Irisawa
package p3;
import java.util.Date;
import java.util.Calendar;	// import the package containing the classes Date and Calendar
// import java.util.*	//java.util内の全てのパッケージをimport

public class CalendarTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date today = new Date();
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		int day = now.get(Calendar.DATE);
		int hour = now.get(Calendar.HOUR);
		int minute = now.get(Calendar.MINUTE);
		int second = now.get(Calendar.SECOND);
		
		System.out.printf("%d/%d/%d %d:%d:%d\n", year,month+1,day,hour,minute,second);
		System.out.println(today);
	}

}
