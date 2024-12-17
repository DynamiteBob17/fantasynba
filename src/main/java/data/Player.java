package data;
import output.CSVGenerator;
import output.CSVPrintable;

import java.util.Optional;

public class Player implements CSVPrintable {
    private final String name; // full name
    private final String team; // abbr.
    private final double salary; // in millions
    private final double form; // how good they are currently compared to so far
    private final double tp; // total points
    private final Optional<Double> optional; // some data column that may or may not exist

    private static final double NO_OPT = -1; // no optional

    public Player(
            String name,
            String team,
            double salary,
            double form,
            double tp,
            double optional
    ) {
        this.name = name;
        this.team = team;
        this.salary = salary;
        this.form = form;
        this.tp = tp;
        this.optional = optional < 0 ? Optional.empty() : Optional.of(optional);
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
                NO_OPT
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

    public double getOptional() {
        return optional.orElse(NO_OPT);
    }

    @Override
    public String toCSVString(CSVGenerator csvGenerator) {
        return null;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", team='" + team + '\'' +
                ", salary=" + salary +
                ", form=" + form +
                ", tp=" + tp +
                ", optional=" + getOptional() +
                '}';
    }

}
