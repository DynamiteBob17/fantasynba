package data;

import data.calculation.strategy.ScoreCalculationStrategy;
import data.calculation.visitor.SumVisitor;
import io.csv.CSVLineGenerator;
import io.csv.CSVPrintable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Team implements CSVPrintable {
    private final String name;
    private final List<Player> players;

    public Team(String name, List<Player> players) {
        this.name = name;
        this.players = new ArrayList<>(players);
    }

    public Team(String name) {
        this.name = name;
        this.players = new ArrayList<>();
    }

    public static Team merge(String newName, Team team1, Team team2) {
        List<Player> newPlayers = new ArrayList<>(team1.getPlayers());
        newPlayers.addAll(team2.getPlayers());
        return new Team(newName, newPlayers);
    }

    public void addPlayer(Player player) {
        if (player != null) players.add(player);
    }

    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public double calculateSum(Function<Player, Double> fieldExtractor) {
        SumVisitor visitor = new SumVisitor(fieldExtractor);
        players.forEach(p -> p.accept(visitor));
        return visitor.getSum();
    }

    public double calculateScore(ScoreCalculationStrategy strategy) {
        return players.stream()
                .mapToDouble(strategy::calculateScore)
                .sum();
    }

    private static Player findPlayerWithAdditional(Team team) {
        return team.getPlayers().stream()
                .filter(Player::existsAdditional)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String getCSVHeader(CSVLineGenerator csvLineGenerator) {
        List<String> header = new ArrayList<>(List.of("name", "totalSalary", "totalForm", "totalTotalPoints"));

        Player playerWithAdditional = findPlayerWithAdditional(this);
        if (playerWithAdditional != null) {
            char additionalFirstChar = playerWithAdditional.getAdditionalName().charAt(0);
            header.add("total" +
                    Character.toUpperCase(additionalFirstChar)
                    + playerWithAdditional.getAdditionalName().substring(1)
            );
        }

        return csvLineGenerator.generateStringValues(header.toArray(new String[0]));
    }

    @Override
    public String getCSVValues(CSVLineGenerator csvLineGenerator) {
        List<Double> values = new ArrayList<>(List.of(
                calculateSum(Player::getSalary),
                calculateSum(Player::getForm),
                calculateSum(Player::getTp)
        ));

        Player playerWithAdditional = findPlayerWithAdditional(this);
        if (playerWithAdditional != null) {
            values.add(calculateSum(Player::getAdditionalValue));
        }

        return csvLineGenerator.generateStringValues(
                name, csvLineGenerator.generateDoubleValues(values.toArray(new Double[0]))
        );
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", players=" + players +
                '}';
    }
}
