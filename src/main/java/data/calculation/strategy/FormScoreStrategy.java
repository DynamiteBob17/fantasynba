package data.calculation.strategy;

import data.Player;

public class FormScoreStrategy implements ScoreCalculationStrategy {
    @Override
    public double calculateScore(Player player) {
        return player.getForm();
    }
}
