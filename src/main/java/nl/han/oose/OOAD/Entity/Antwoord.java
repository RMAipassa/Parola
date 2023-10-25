package nl.han.oose.OOAD.Entity;

public class Antwoord {
    private String antwoordText;
    private boolean isCorrect;

    public Antwoord(String antwoordText, boolean isCorrect) {
        this.antwoordText = antwoordText;
        this.isCorrect = isCorrect;
    }

    public String getAntwoordText() {
        return antwoordText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
