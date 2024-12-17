package output;

import java.util.Arrays;

public class CSVGenerator {
    private static CSVGenerator csvGenerator = null;

    private CSVGenerator() {
    }

    public static CSVGenerator getInstance() {
        if (csvGenerator != null) return csvGenerator;

        return csvGenerator = new CSVGenerator();
    }

    public String generateStringValues(String... values) {
        return String.join(",", values);
    }

    private String generateValues(String format, Object... values) {
        String[] formats = new String[values.length];
        Arrays.fill(formats, format);
        return String.join(",", formats).formatted(values);
    }

    public String generateDoubleValues(int precision, Double... values) {
        return generateValues("%." + precision + "f", values);
    }

    public String generateDoubleValues(Double... values) {
        return generateDoubleValues(2, values);
    }

}
