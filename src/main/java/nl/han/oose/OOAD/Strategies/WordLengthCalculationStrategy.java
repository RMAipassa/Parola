package nl.han.oose.OOAD.Strategies;

import nl.han.oose.OOAD.Entity.QuizResult;

public class WordLengthCalculationStrategy implements ScoreCalculationStrategy {
	@Override
	public int calculateScore(QuizResult quizResult) {
		return quizResult.getWordLength();
	}
}
