package data.calculation.strategy.combination;

import data.calculation.strategy.score.TotalPointsScoreStrategy;

public class BestCombinationByTotalPoints extends PlayerCombinationCalculator {
    public BestCombinationByTotalPoints() {
        scoreCalculationStrategy = TotalPointsScoreStrategy.getInstance();
    }
}
