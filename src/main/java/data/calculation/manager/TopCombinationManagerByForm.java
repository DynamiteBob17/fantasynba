package data.calculation.manager;

import data.calculation.strategy.score.FormScoreStrategy;

public class TopCombinationManagerByForm extends TopCombinationManager {
    public TopCombinationManagerByForm(int maxCombinations) {
        super(maxCombinations);
        this.scoreCalculationStrategy = FormScoreStrategy.getInstance();
    }
}
