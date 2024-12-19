package data.calculation.strategy;

import data.Player;

public class TotalPointsScoreStrategy implements ScoreCalculationStrategy {
    @Override
    public double calculateScore(Player player) {
        return player.getTp();
    }
}
