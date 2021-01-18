package e2;

//import e2.SubjectScore;

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
			taro.setSubject(i, subject[i]);
			System.out.println("Set " + taro.getSubject(i) + " to the name of the No." + (i + 1) + " subject of " + taro.getStudentName());
			taro.setScore(i, score[i]);
			System.out.println("Set " + taro.getScore(i) + " to the score of the No." + (i + 1) + " subject of " + taro.getStudentName());
		}
		
		System.out.println("The name of 10th subject of Taro: " + taro.getSubject(9));
		
		System.out.printf("The average score of %s: %.2f\n", taro.getStudentName(), taro.calAverage());
		System.out.printf("The maximum score of %s: %d [%s]\n", taro.getStudentName(), taro.getMaxScore(), taro.getMaxSubject());		
		System.out.printf("The minimum score of %s: %d [%s]\n", taro.getStudentName(), taro.getMinScore(), taro.getMinSubject());

		System.out.println("Student name of Hanako: " + hanako.getStudentName());
		System.out.println("Number of subjects for Hanako: " + hanako.getNum());
		hanako.setStudentName("Hanako");
		System.out.println("Student name of Hanako: " + hanako.getStudentName());
		
		System.out.println("Student name of Jiro: " + jiro.getStudentName());
		System.out.println("Number of subjects for Jiro: " + jiro.getNum());
		
		System.out.println("Student name of Saburo: " + saburo.getStudentName());
		System.out.println("Number of subjects for Saburo: " + saburo.getNum());
		
		System.out.println("The name and score of the 1st subject of Saburo: " + saburo.getSubject(1)+ "," + saburo.getScore(1));
		saburo.setScore(1);
		saburo.setSubject(1);
		System.out.println("The name and score of the 1st subject of Saburo: " + saburo.getSubject(1)+ "," + saburo.getScore(1));
	}

}
