package data;

import data.calculation.strategy.score.ScoreCalculationStrategy;
import data.calculation.visitor.PlayerVisitor;
import io.csv.CSVLineGenerator;
import io.csv.CSVPrintable;

import java.util.ArrayList;
import java.util.List;

public class Player implements CSVPrintable {

    public record AdditionalData(String name, double value) {
    }

    private static final double NO_ADDITIONAL = -1;

    private final String name; // full name
    private final String team; // abbr.
    private final double salary; // in millions
    private final double form; // how good they are currently compared to so far
    private final double tp; // total points
    private final AdditionalData additional; // some data column that may or may not exist

    private Player(Builder builder) {
        this.name = builder.name;
        this.team = builder.team;
        this.salary = builder.salary;
        this.form = builder.form;
        this.tp = builder.tp;
        this.additional = builder.additionalValue == NO_ADDITIONAL
                ? null
                : new AdditionalData(builder.additionalName, builder.additionalValue);
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public double getSalary() {
        return salary;
    }

    public double getForm() {
        return form;
    }

    public double getTp() {
        return tp;
    }

    public double getAdditionalValue() {
        return existsAdditional() ? additional.value() : NO_ADDITIONAL;
    }

    public String getAdditionalName() {
        return existsAdditional() ? additional.name() : "";
    }

    public boolean existsAdditional() {
        return additional.value() != NO_ADDITIONAL;
    }

    public void accept(PlayerVisitor visitor) {
        visitor.visit(this);
    }

    public double calculateScore(ScoreCalculationStrategy strategy) {
        return strategy.calculateScore(this);
    }

    @Override
    public String getCSVHeader(CSVLineGenerator csvLineGenerator) {
        List<String> header = new ArrayList<>(List.of("name", "team", "salary", "form", "tp"));
        if (existsAdditional()) header.add(additional.name());
        return csvLineGenerator.generateStringValues(header.toArray(new String[0]));
    }

    @Override
    public String getCSVValues(CSVLineGenerator csvLineGenerator) {
        List<Double> values = new ArrayList<>(List.of(salary, form, tp));
        if (existsAdditional()) values.add(additional.value());

        return csvLineGenerator.generateStringValues(
                name, team, csvLineGenerator.generateDoubleValues(values.toArray(new Double[0]))
        );
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", team='" + team + '\'' +
                ", salary=" + salary +
                ", form=" + form +
                ", tp=" + tp +
                (existsAdditional() ? (", %s=%f").formatted(additional.name(), additional.value()) : "") +
                '}';
    }

    public static class Builder {
        private String name;
        private String team;
        private double salary;
        private double form;
        private double tp;
        private String additionalName = "";
        private double additionalValue = NO_ADDITIONAL;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder team(String team) {
            this.team = team;
            return this;
        }

        public Builder salary(double salary) {
            this.salary = salary;
            return this;
        }

        public Builder form(double form) {
            this.form = form;
            return this;
        }

        public Builder tp(double tp) {
            this.tp = tp;
            return this;
        }

        public Builder withAdditional(String additionalName, double additionalValue) {
            this.additionalName = additionalName;
            this.additionalValue = additionalValue;
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }

}
