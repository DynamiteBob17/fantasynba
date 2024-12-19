package io.csv;

public class CSVGenerator {
    private final CSVLineGenerator csvLineGenerator;

    public CSVGenerator() {
        this.csvLineGenerator = CSVLineGenerator.getInstance();
    }

    public String generateCompleteCSV(CSVPrintable csvPrintable) {
        return csvPrintable.getCSVHeader(csvLineGenerator) + '\n' +
                csvPrintable.getCSVValues(csvLineGenerator);
    }

    public String generateCompleteCSV(CSVPrintable csvPrintableHeaderDefinition, CSVPrintable... csvPrintables) {
        StringBuilder csv = new StringBuilder();

        csv.append(csvPrintableHeaderDefinition.getCSVHeader(csvLineGenerator)).append('\n');

        for (CSVPrintable csvPrintable : csvPrintables) {
            csv.append(csvPrintable.getCSVValues(csvLineGenerator)).append('\n');
        }

        return csv.toString();
    }

}
