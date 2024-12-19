package data;

import data.calculation.visitor.PlayerVisitor;
import io.csv.CSVLineGenerator;
import io.csv.CSVPrintable;

import java.util.ArrayList;
import java.util.List;

public class Player implements CSVPrintable {

    public record AdditionalData(double value, String name) {
    }

    private static final double NO_ADDITIONAL = -1;

    private final String name; // full name
    private final String team; // abbr.
    private final double salary; // in millions
    private final double form; // how good they are currently compared to so far
    private final double tp; // total points
    private final AdditionalData additional; // some data column that may or may not exist

    public Player(
            String name,
            String team,
            double salary,
            double form,
            double tp,
            double additionalValue,
            String additionalName
    ) {
        this.name = name;
        this.team = team;
        this.salary = salary;
        this.form = form;
        this.tp = tp;
        this.additional = additionalValue == NO_ADDITIONAL
                ? null
                : new AdditionalData(additionalValue, additionalName);
    }

    public Player(
            String name,
            String team,
            double salary,
            double form,
            double tp
    ) {
        this(
                name,
                team,
                salary,
                form,
                tp,
                NO_ADDITIONAL,
                ""
        );
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

}
