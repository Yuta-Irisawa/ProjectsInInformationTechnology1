package score;

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
			throw new Exception(i + " is a wrong index"); 
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
					if(this.getSubject(n) == null) {
						
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
