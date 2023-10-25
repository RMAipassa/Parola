package nl.han.oose.OOAD.Strategies;

import nl.han.oose.OOAD.Entity.QuizResult;

public class TotalTimeCalculationStrategy implements ScoreCalculationStrategy {
	@Override
	public int calculateScore(QuizResult quizResult) {
		return quizResult.getTotalTime();
	}
}
