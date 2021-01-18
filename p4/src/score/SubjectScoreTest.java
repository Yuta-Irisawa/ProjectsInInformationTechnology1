package score;

public class SubjectScoreTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String subject [] = {"AA", "BB", "CC", "DD", "EE", "FF", "GG", "HH", "II"};
		int score [] = {90, 80, 99, 60, 99, 95, 90, 80, 93};

		SubjectScore taro = new SubjectScore(10);
		SubjectScore hanako = new SubjectScore();
		SubjectScore jiro = new SubjectScore("Jiro");
		SubjectScore saburo = new SubjectScore("Saburo", 3);
		
		System.out.println("Student name of taro: " + taro.getStudentName());
		System.out.println("Number of subjects for taro: " + taro.getNum());
		taro.setStudentName("Taro");
		for(int i = 0; i < subject.length; i++){
			try {
				taro.setSubject(i, subject[i]);
				System.out.println("Set " + taro.getSubject(i) + " to the name of the No." + (i + 1) + " subject of " + taro.getStudentName());
				taro.setScore(i, score[i]);
				System.out.println("Set " + taro.getScore(i) + " to the score of the No." + (i + 1) + " subject of " + taro.getStudentName());				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		try {
			System.out.println("The name of 10th subject of Taro: " + taro.getSubject(9));			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.printf("The average score of %s: %.2f\n", taro.getStudentName(), taro.calAverage());
		System.out.printf("The maximum score of %s: %d [%s]\n", taro.getStudentName(), taro.getMaxScore(), taro.getMaxSubject());		
		System.out.printf("The minimum score of %s: %d [%s]\n", taro.getStudentName(), taro.getMinScore(), taro.getMinSubject());

		System.out.println("Student name of hanako: " + hanako.getStudentName());
		System.out.println("Number of subjects for hanako: " + hanako.getNum());
		hanako.setStudentName("Hanako");
		System.out.println("Student name of hanako: " + hanako.getStudentName());
		
		System.out.println("Student name of jiro: " + jiro.getStudentName());
		System.out.println("Number of subjects for jiro: " + jiro.getNum());
		
		System.out.println("Student name of saburo: " + saburo.getStudentName());
		System.out.println("Number of subjects for saburo: " + saburo.getNum());
		/* for Practice 3-1 */
		try {
			System.out.println("The name and score of the 1st subject of Saburo: " + saburo.getSubject(1) + ", " + saburo.getScore(1));  
			saburo.setScore(1);
			saburo.setSubject(1);
			System.out.println("The name and score of the 1st subject of Saburo: " + saburo.getSubject(1) + ", " + saburo.getScore(1));  			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		/* for Exercise 3 */
		String [] xy_subjects = {"XX", "YY", "ZZ"};
		int [] xy_scores = {45, 56, 32};
		String [] empty_subjects = new String [taro.getNum()];
		int [] empty_scores = new int [taro.getNum()];
		//String [] empty_subjects = null;
		//int [] empty_scores = null;
		
		taro.setSubjects(xy_subjects);
		taro.setScores(xy_scores);
		System.out.println("Taro's updated scores");
		for(int i = 0; i < taro.getNum(); i++)
			try {
				System.out.printf("  %s: %d\n", taro.getSubject(i), taro.getScore(i));				
			} catch  (Exception e) {
				System.out.println(e.getMessage());
			}
		
		taro.copySubjects(empty_subjects);
		taro.copyScores(empty_scores);
		//empty_subjects = taro.copySubjects();
		//empty_scores = taro.copyScores();
		System.out.println("Scores copied from Taro's");
		for(int i = 0; i < empty_scores.length; i++)
			System.out.printf("  %s: %d\n", empty_subjects[i], empty_scores[i]);
		System.out.printf("Average over Taro's first 5 scores: %.2f\n", taro.calAverage(5));
		System.out.printf("Average over Taro's first 15 scores: %.2f\n", taro.calAverage(15));	

		/* for Exercise 4 */
		try {
			System.out.printf("  %s: %d\n", taro.getSubject(10), taro.getScore(10));				
		} catch  (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			taro.setSubject(10, "IT-Pro1");				
		} catch  (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			taro.setScore(9, 110);				
		} catch  (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
