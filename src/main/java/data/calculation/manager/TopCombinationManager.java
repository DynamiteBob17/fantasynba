package data.calculation.manager;

import data.Team;
import data.calculation.strategy.score.ScoreCalculationStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class TopCombinationManager {
    private final int maxCombinations;
    private final List<Team> topCombinations;
    protected ScoreCalculationStrategy scoreCalculationStrategy;

    protected TopCombinationManager(int maxCombinations) {
        this.maxCombinations = maxCombinations;
        this.topCombinations = new ArrayList<>();
    }

    public void addCombinationIfBetter(Team team) {
        topCombinations.add(team);
        topCombinations.sort(Comparator.comparingDouble(t -> ((Team) t).calculateScore(scoreCalculationStrategy)).reversed());
        if (topCombinations.size() > maxCombinations) {
            topCombinations.removeLast();
        }
    }

    public List<Team> getTopCombinations() {
        return Collections.unmodifiableList(topCombinations);
    }
}
