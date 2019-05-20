class IfElseDemo {
    public static void main(String[] args) {

        int testscore = 76;
        char grade;

        if (testscore >= 90) {
            grade = 'A';
        } else if (testscore >= 80) {
            grade = 'B';
        } else if (testscore >= 70) {
            grade = 'C';
        } else if (testscore >= 60) {
            grade = 'D';
        } else {
            grade = 'F';
        }

        System.out.println("Grade = " + grade);

        boolean isFailed = grade == 'F';

        if (grade == 'A' || grade == 'B') {
            System.out.println("Good!");
        } else if (isfailed && (isFailed || testscore < 50) && testscore < 50) {
            System.out.println("Fatal failed.");
        }

        if (isFailed) {
            System.out.println("Your parents will be invited to the school.");
        }
    }
}