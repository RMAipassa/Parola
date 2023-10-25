package nl.han.oose.OOAD.Strategies;

import nl.han.oose.OOAD.Entity.QuizResult;

public interface ScoreCalculationStrategy {
	int calculateScore(QuizResult quizResult);
}
