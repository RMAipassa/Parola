package nl.han.oose.OOAD.Managers;

import java.security.PrivateKey;

public class ParolaController {
    private static ParolaController controller;
    public static ParolaController getInstance() {
        if(controller == null){
            controller = new ParolaController();
        }
        return controller;
    }

    public void startQuiz(String playername) {
    }

    public boolean nextQuestion(String playername) {
    }

    public void processAnswer(String playername, String answer) {
    }

    public boolean quizFinished(String playername) {
    }

    public String getLettersForRightAnswers(String playername) {
    }

    public int calculateScore(String playername, String word) {
    }
}
