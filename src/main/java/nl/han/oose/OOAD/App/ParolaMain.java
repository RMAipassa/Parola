package nl.han.oose.OOAD.App;

import java.util.Scanner;

public class ParolaMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParolaController controller = ParolaController.getInstance();


        String username = null;
        String password;
        boolean isAuthenticated = false;
        while (!isAuthenticated) {
            System.out.println("Enter your username: ");
            username = scanner.nextLine();
            System.out.println("Enter your password: ");
            password = scanner.nextLine();


            isAuthenticated = controller.authenticateUser(username, password);

            if (!isAuthenticated) {
                System.out.println("Authentication failed. Do you want to create a new account? (yes/no)");
                String createAccountOption = scanner.nextLine();
                if (createAccountOption.equalsIgnoreCase("yes")) {
                    System.out.println("Creating a new account...");
                    boolean isUserCreated = controller.createUser(username, password);
                    if (isUserCreated) {
                        System.out.println("Account created successfully.");
                    } else {
                        System.out.println("Account creation failed. Please choose a different username.");
                    }
                }
            }
        }

        System.out.println("You are now logged in. Welcome, " + username);


        boolean continuePlaying = true;
        while (continuePlaying) {
            System.out.println("The 8-question quiz starts. Good luck!");
            controller.startQuiz(username);
            do {
                System.out.println(controller.nextVraag(username));
                System.out.print("Give your answer to this question: ");
                String answer = scanner.nextLine();
                controller.processAnswer(username, answer);
            } while (!controller.quizFinished(username));

            System.out.println("You've earned the following letters: " + controller.getLettersForRightAnswers(username));
            System.out.print("Make a word, as long as possible, that contains these letters: ");
            String word = scanner.nextLine();

            int score = controller.calculateScore(username, word);
            System.out.println("Score: " + score);
            System.out.print("Do you want to play another quiz? (yes/no): ");
            String playAnotherQuizOption = scanner.nextLine();
            if (!playAnotherQuizOption.equalsIgnoreCase("yes")) {
                continuePlaying = false;
            }
        }

        System.out.println("Thank you for playing!");
    }
}
