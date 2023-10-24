package nl.han.oose.OOAD.App;

import java.util.Scanner;

public class ParolaMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParolaController parola = ParolaController.getInstance();

        System.out.println("Enter your player name: ");
        String playername = scanner.nextLine();

        System.out.println("The 8-question quiz starts. Good luck!");
        parola.startQuiz(playername);
        do {
            System.out.println(parola.nextQuestion(playername));
            System.out.print("Give your answer to this question: ");
            String answer = scanner.nextLine();
            parola.processAnswer(playername, answer);
        } while (!parola.quizFinished(playername));

        System.out.println("You've earned the following letters: " + parola.getLettersForRightAnswers(playername));
        System.out.print("Make a word, as long as possible, that contains these letters: ");
        String word = scanner.nextLine();

        int score = parola.calculateScore(playername, word);
        System.out.println("Score: " + score);
    }
}
