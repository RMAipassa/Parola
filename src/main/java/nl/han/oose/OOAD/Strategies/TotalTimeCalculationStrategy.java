package nl.han.oose.OOAD.Strategies;

import nl.han.oose.OOAD.Entity.QuizResult;

public class TotalTimeCalculationStrategy implements ScoreCalculationStrategy {
	@Override
	public int calculateScore(QuizResult quizResult) {
		int score = quizResult.getTotalTime()/60;
		return score;
	}
}
