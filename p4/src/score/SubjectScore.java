//15819013 Yuta Irisawa
package score;

public class SubjectScore {
	// member variable
	protected String studentName;	//variable to store the student's name
	private int num;			//variable to store the number of subjects
	private String[] subject;	//array to store the names of subjects
	private int[] point;		//array to store the scores of subjects
	static int MaxNum = 10; 	//the maximum number of subjects
	
	// constructor
	SubjectScore(String s, int i){
		studentName = s;
		if ((0 < i) && (i <= SubjectScore.MaxNum)) {
			num = i;
		}else {
			num = SubjectScore.MaxNum;
		}
		subject = new String[num];	//memory allocation for subject array
		point = new int[num];		//memory allocation for point array
	}
	
	SubjectScore(int i){
		this("unknown", i);
	}
	
	SubjectScore(String s){
		this(s, 5);
	}
	
	SubjectScore(){
		this("unknown", 5);
	}
	
	// getter & setter
	int getNum() {
		return num;
	}
	
	String getStudentName() {
		return studentName;
	}
	
	void setStudentName(String s) {
		studentName = s;
	}
	
	String getSubject(int i) throws Exception {
		if(i < 0 || i >= subject.length) {
			throw new Exception(i + " is a wrong index.");
		}
		return subject[i];
	}
	
	int getScore(int i) throws Exception {
		if(i < 0 || i >= point.length) {
			throw new Exception(i + " is a wrong index.");
		}
		return point[i];
	}
	
	void setSubject(int i, String s) throws Exception{
		if(i < 0 || i >= subject.length) {
			throw new Exception(i + " is a wrong index.");
		}
		subject[i] = s;
	}
	
	/* for Practice 3-1 */
	void setSubject(int i) throws Exception{
		if(i < 0 || i >= subject.length) {
			throw new Exception(i + " is a wrong index.");
		}
		subject[i] = "unknown";
	}
	
	void setScore(int i, int j) throws Exception {
		if((i < 0 || i >= point.length) || (j < 0 || j > 101)) {
			throw new Exception(j + " is a wrong index.");
		}
		point[i] = j;
	}
	
	/* for Practice 3-1 */
	void setScore(int i) throws Exception{
		if(i < 0 || i >= point.length) {
			throw new Exception(i + " is a wrong index.");
		}
		point[i] = 60;
	}
	
	//calculate the average score of registered subjects
	double calAverage() {
		double ans = 0.0;
		int count = 0;
		
		for(int num = 0; num < this.num; num++) {
			if(subject[num] == null) {
				
			}else {
				ans += point[num];
				count++;	//counts the number of registered subjects
			}
		}

		return ans / count;
	}
	
	int getMaxScore() {
		int max;
		max = point[0];	//First, 0th point is the maximum score
		
		for(int num=1; num<this.num; num++) {
			if(subject[num] == null) {
				continue;
			}
			if(point[num] > max) {
				max = point[num];
			}
		}
		return max;
	}
	
	int getMinScore() {
		int min;
		min = point[0]; //First, 0th point is the minimum score
		
		for(int num=1; num<this.num; num++) {
			if(subject[num] == null) {
				continue;
			}
			if(point[num] < min) {
				min = point[num];
			}
		}
		return min;
	}
	
	String getMaxSubject() {
		int index = 0;	//variable to store index of maximum subject score
		int max = point[index];
		
		for(int num=0; num<this.num; num++) {
			if(subject[num] == null) {
				continue;
			}
			if(point[num] > max) {
				max = point[num];
				index = num;
			}
		}
		
		return subject[index];
	}
	
	String getMinSubject() {
		int index = 0;	//variable to store index of minimum subject score
		int min = point[index];
		
		for(int num=0; num<this.num; num++) {
			if(subject[num] == null) {
				continue;
			}
			if(point[num] < min) {
				min = point[num];
				index = num;
			}
		}
		
		return subject[index];
	}
	
	// Exercise 3
	double calAverage(int m) {
		double ans = 0.0;
		int count;
		
		if(m >= this.num) {
			count = this.num;
		}else {
			count = m;
		}
		
		for(int num = 0; num < count; num++) {
			if(subject[num] == null) {
				count--; //countを1つ減す(最後にcountで割る際に必要)
			}else {
				ans += point[num];
			}
		}

		return ans / count;
	}
	
	void setScores(int[] p) {
		if(p.length > point.length) {
			for(int i = 0; i < point.length; i++) {
				point[i] = p[i];
			}
		}else {
			for(int i = 0; i < p.length; i++) {
				point[i] = p[i];
			}
		}
	}
	
	void setSubjects(String[] s) {
		if(s.length > subject.length) {
			for(int i = 0; i < subject.length; i++) {
				subject[i] = s[i];
			}
		}else {
			for(int i = 0; i < s.length; i++) {
				subject[i] = s[i];
			}
		}
	}
	
	void copyScores(int[] p) {
		for(int i = 0; i < point.length; i++) {
			p[i] = point[i];
		}
	}
	
	void copySubjects(String[] s) {
		for(int i = 0; i < subject.length; i++) {
			s[i] = subject[i];
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}


