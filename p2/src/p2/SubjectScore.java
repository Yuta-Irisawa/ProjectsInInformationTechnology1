// 15819013 Yuta Irisawa
package p2;

public class SubjectScore {
	private String subject;
	private int point;
	
	// constructor
	SubjectScore(){
		this("unknown", 60);
		//subject = "unknown";
		//point = 60;
	}
	SubjectScore(String s){
		this(s, 60);
		//subject = s;
		//point = 60;
	}
	SubjectScore(String s, int i){
		subject = s;
		point = i;
	}
	
//	SubjectScore(String subject, int point){
//		this.subject = subject;
//		this.point = point;
//	}
	
	// member method
	void showScore() {
		System.out.println(subject + ": " + point);
	}
	
	// getter setter
	String getSubject() {
		return subject;
	}
	
	int getScore() {
		return point;
	}
	
	void setSubject(String s) {
		subject = s;
	}
	
	void setScore(int i) {
		point = i;
	}
	
	public static void main(String[] args) {
		SubjectScore s1 = new SubjectScore();
		System.out.println("Subject: " + s1.subject + " Score: " + s1.point);
		s1.showScore();
		SubjectScore s2 = new SubjectScore("Mathematics");
		System.out.println("Subject: " + s2.subject + " Score: " + s2.point);
		s2.showScore();
		SubjectScore s3 = new SubjectScore("English", 100);
		System.out.println("Subject: " + s3.subject + " Score: " + s3.point);
		s3.showScore();
	}
}
