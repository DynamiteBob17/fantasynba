package data.calculation.strategy.combination;

import data.calculation.strategy.score.AdditionalScoreStrategy;

public class BestCombinationByAdditional extends PlayerCombinationCalculator {
    public BestCombinationByAdditional() {
        scoreCalculationStrategy = AdditionalScoreStrategy.getInstance();
    }
}
