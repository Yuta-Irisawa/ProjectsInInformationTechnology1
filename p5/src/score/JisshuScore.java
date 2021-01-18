// 15819013 Yuta Irisawa
package score;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;

//import org.apache.commons.lang3.StringUtils;

class SubjectScore {
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
		if(i < 0 || i >= point.length) {
			throw new Exception(i + " is a wrong index.");
		}
		if(j < 0 || j > 101) {
			throw new Exception(j + " is a wrong score.");
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

public class JisshuScore extends SubjectScore{
	private int[] report;	// array for scores of reports of each subject
	
	// constructor
	JisshuScore(){
		super();
	}
	
	JisshuScore(String s){
		super(s);
	}
	
	JisshuScore(int i){
		super(i);
		report = new int[getNum()];	// memory allocation
	}
	
	JisshuScore(String s, int i){
		super(s, i);
		report = new int[getNum()];
	}
	
	// method
	void setReportScore(int i, int j) throws Exception{
		if(i >= report.length) {
			throw new Exception(i + " is a wrong index.");	//i > report.length
		}
		if(j<0 || j > 100) {
			throw new Exception(j + " is a wrong score."); //0<=j<=100
		}
		report[i] = j;
	}
	
	int getReportScore(int i) throws Exception{
		if(i >= report.length) {
			throw new Exception(i + " is a wrong index."); 
		}
		return report[i];
	}
	
	double calAverage(int i) {
		if( i == 0) {
			return super.calAverage();
		}else {
			double ans = 0.0;
			int count = 0;
			
			for(int n = 0; n < report.length; n++) {
				// getSubject has Exception in SubjectScore.java
				try {
					if(getSubject(n) == null) {
						
					}else {
						ans += report[n];
						count++;	//counts the number of registered subjects
					}
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}

			return ans / count;
		}
	}
	
	// Exercise 5
	void readScores(String fname) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fname));
			
			String line;
			
			// SubjectName
			if((line = br.readLine()) != null) {
				String[] token = line.split("\\s+");
				for(int i = 0; i < token.length; i++) {
					try {
						setSubject(i, token[i]);
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
			
			// Score
			if((line = br.readLine()) != null) {
				String[] token = line.split("\\s+");
				for(int i = 0; i < token.length; i++) {
					try {
						setScore(i, Integer.parseInt(token[i]));	//setScore(int i, int j)
					}catch(Exception e) {
						System.out.println(e.getMessage());	
					}
				}
			}
			
			// Report
			if((line = br.readLine()) != null) {
				String[] token = line.split("\\s+");
				for(int i = 0; i < token.length; i++) {
					try {
						setReportScore(i, Integer.parseInt(token[i]));
					}catch(Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
			
			br.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	void writeScores(String fname) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fname));
			PrintWriter pw = new PrintWriter(bw);
			
			// average score
			pw.printf("The average score of %s: %.2f\n", getStudentName(), calAverage(0));
			pw.printf("The average report score of %s: %.2f\n", getStudentName(), calAverage(1));
			pw.printf("The maximum score of %s: %d [%s]\n", getStudentName(), getMaxScore(), getMaxSubject());		
			pw.printf("The minimum score of %s: %d [%s]\n", getStudentName(), getMinScore(), getMinSubject());
			
			pw.close();
		}catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		if (args.length == 2) {
			String inputFileName = args[0];
			String outputFileName = args[1];
			switch (inputFileName) {
			case "TaroInput.txt":
				JisshuScore taro = new JisshuScore("Taro", 9);
				taro.readScores(inputFileName);
				taro.writeScores(outputFileName);
				break;
			case "HanakoInput.txt":
				JisshuScore hanako = new JisshuScore("Hanako", 6);
				hanako.readScores(inputFileName);
				hanako.writeScores(outputFileName);
				break;
			case "JiroInput.txt":
				JisshuScore jiro = new JisshuScore("Jiro", 5);
				jiro.readScores(inputFileName);
				jiro.writeScores(outputFileName);
				break;
			case "SaburoInput.txt":
				JisshuScore saburo = new JisshuScore("Saburo", 3);
				saburo.readScores(inputFileName);
				saburo.writeScores(outputFileName);
				break;
			default:
				System.err.println("The input file is not found.");
				break;
			}
		} else {
			System.err.println("java JisshuScore [input_file] [output_file].");
		}
	}
}


