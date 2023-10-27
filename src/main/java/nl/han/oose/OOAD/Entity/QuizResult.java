package nl.han.oose.OOAD.Entity;

public class QuizResult {
    private int correctAnswerCount;
    private int wordLength;
    private int totalTime;

    public QuizResult(int correctAnswerCount, int wordLength, int totalTime) {
        this.correctAnswerCount = correctAnswerCount;
        this.wordLength = wordLength;
        this.totalTime = totalTime;
    }

    public int getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    public int getWordLength() {
        return wordLength;
    }

    public int getTotalTime() {
        return totalTime;
    }
}
