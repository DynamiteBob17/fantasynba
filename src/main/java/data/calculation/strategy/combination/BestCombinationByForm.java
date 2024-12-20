package data.calculation.strategy.combination;

import data.calculation.strategy.score.FormScoreStrategy;

public class BestCombinationByForm extends PlayerCombinationCalculator {
    public BestCombinationByForm() {
        scoreCalculationStrategy = FormScoreStrategy.getInstance();
    }
}
