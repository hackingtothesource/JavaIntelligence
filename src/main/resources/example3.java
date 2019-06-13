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

        switch (grade) {
            case 'A':
                break;
            case 'B':
                System.out.println("hi");
                break;
            case 'C':
                System.out.println("hello");
                break;
            default:
                System.out.println("how are you");
                break;
        }

        if (isFailed) {
            System.out.println("Your parents will be invited to the school.");
        }

        for(int i = 0, j = 0; i < 10; i++, j++) {
            System.out.println(i);
        }
    }
}