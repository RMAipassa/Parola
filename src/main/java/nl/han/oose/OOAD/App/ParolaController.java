package nl.han.oose.OOAD.App;

import nl.han.oose.OOAD.Managers.GameManager;

public class ParolaController {
    private static ParolaController controller;

    private GameManager gameManager;
    public static ParolaController getInstance() {
        if(controller == null){
            controller = new ParolaController();
        }
        return controller;
    }

    public void startQuiz(String playername) {
    }

    public boolean nextQuestion(String playername) {
        return false;
    }

    public void processAnswer(String playername, String answer) {
    }

    public boolean quizFinished(String playername) {
        return false;
    }

    public String getLettersForRightAnswers(String playername) {
        return null;
    }

    public int calculateScore(String playername, String word) {
        return 0;
    }


}
