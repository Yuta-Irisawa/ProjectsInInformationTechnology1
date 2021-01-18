package p1;

public class ShowAverage {

	public static void main(String[] args) {
		
		int i, j;
		float StudentAve = 0, SubjectAve = 0;
		int[][] score = {{87, 58, 39}, {49, 93, 58}, {75, 84, 58}};
		
		// average score over the three subjects for each student
		for(i = 0; i < score.length; i++) {
			for(j = 0; j < score[i].length; j++) {
				StudentAve += score[i][j];
			}
			StudentAve = StudentAve / score[i].length;
			System.out.printf("%d番目の人の平均得点 ： %.1f", i+1, StudentAve);
			System.out.println("");
			StudentAve = 0;
		}
		
		// average score over the three students for each subject
		
		for(j = 0; j < score[0].length; j++) {
			for(i = 0; i < score.length; i++) {
				SubjectAve += score[i][j];
			}
			SubjectAve = SubjectAve / score.length;
			System.out.printf("%d番目の科目の平均得点 ： %.1f", j+1, SubjectAve);
			System.out.println("");
			SubjectAve = 0;
		}
	}

}
