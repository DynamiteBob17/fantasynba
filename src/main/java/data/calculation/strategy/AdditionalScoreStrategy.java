package data.calculation.strategy;

import data.Player;

public class AdditionalScoreStrategy implements ScoreCalculationStrategy {
    @Override
    public double calculateScore(Player player) {
        return player.getAdditionalValue();
    }
}
