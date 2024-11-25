package org.cis1200;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Operations for working with CSV data.
 *
 * For our purposes, CSV data is a series of text lines, each of which is
 * considered to be a <i>record</i> consisting of <i>fields</i> separated by
 * the comma ',' character. (Some variants of CSV allow for multi-line
 * data representations, but we disregard that possibility here.)
 *
 *
 * For example, the file {@code files/illustrative_example.csv} contains two
 * CSV records, one on each line of the file:
 * 
 * <pre>
 * col0, col1, a table and a chair
 * cola, colb, a banana! and a banana?
 * </pre>
 * 
 * Each of the records in this example contains three fields, but there is no
 * requirement that each record have the same number of fields.
 *
 * <p>
 * There is one subtlety to parsing CSV records: to allow for the possibility
 * of a field that contains the ',' character itself, CSV treats the
 * double quote character '"' specially. You can quote a field that contains
 * commas.
 *
 * <p>
 * For example, the following line has <i>two</i> fields, the first of which
 * has a quoted comma:
 * 
 * <pre>
 * "this , is quoted",but there are none in this field
 * </pre>
 */
public class CSV {

    /**
     * Define constants for the "special" characters used in CSV files.
     */
    private final static char DOUBLE_QUOTES = '"';
    private final static char COMMA = ',';

    /**
     * Parses one line of a CSV file as a record of fields separated by commas.
     * Returns the sequence of fields as a list of {@code String}s.
     *
     * <p>
     * The parser maintains a boolean state: whether it is in quotation mode.
     *
     * <p>
     * To process the {@code csvLine}, the parser scans through it character
     * by character (the {@code toCharArray()} method might be useful here),
     * accumulating the current field.
     *
     * <p>
     * We recommend using a {@code StringBuilder}
     * to accumulate the {@code String} for each field. You can "empty"
     * a {@code StringBuilder} by instantiating a new one
     * with {@code new StringBuilder()}.
     *
     * <p>
     * If the current character is <i>not</i> DOUBLE_QUOTES:
     * <ul>
     *
     * <li>if the character is a COMMA and the parser isn't in quotation mode,
     * then the COMMA ends the current field. The field is then added to
     * the results list and the parser continues processing the next field (this
     * COMMA is not part of any field).
     *
     * <li>otherwise, append the character to the current field, and continue.
     * </ul>
     *
     * <p>
     * If the current character is a DOUBLE_QUOTE, flip the current quotation mode
     * state.
     * (If the parser is in quotation mode, flip it to not be in quotation mode, and
     * vice versa.)
     * This is because if a DOUBLE_QUOTE is seen, we are either starting or ending a
     * quotation.
     * <p>
     * Finally, no matter what state the parser is in, when it reaches the end of
     * the line, it adds whatever partial field has been accumulated to the result
     * list.
     * </p>
     *
     * <p>
     * <b>Kudos:</b> Parsing quoted quotes
     *
     * <p>
     * Aside from quoting commas, it is also possible to quote quotes by writing a
     * pair of
     * quotes next to each other (somewhere inside quotes).
     * (This is analogous to how, for Java, one must use a backslash to
     * allow quotation marks inside a {@code String} literal as in: "\"".)
     *
     * <p>
     * For example, the following line has a quoted field that includes quoted
     * quotes:
     * 
     * <pre>
     * "He said ""hello""!"
     * </pre>
     *
     * <p>
     * For more examples, see the tests written for this kudos problem in
     * {@code CSVKudosTest} and the file {@code files/quotes_and_commas.csv}.
     *
     * <p>
     * To parse a CSV line containing quotes in quotes, you will have to
     * maintain an extra state: whether the last character seen was
     * an unpaired DOUBLE_QUOTES character.
     * You should also update the states according to the current character.
     *
     * @param csvLine The line of text data to be treated as a CSV record
     * @return The sequence of fields of the CSV record as a list of
     *         {@code String}s.
     */
    public static List<String> parseRecord(String csvLine) {
        // TODO: Complete this method.
        if (csvLine == null) {
            throw new IllegalArgumentException("CSV line cannot be null");
        }
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        boolean quotationMode = false;

        for (char c : csvLine.toCharArray()) {
            if (c == DOUBLE_QUOTES) {
                quotationMode = !quotationMode;
            }
            else if (c == COMMA && !quotationMode) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder();
            }
            else {
                currentLine.append(c);
            }
        }
        lines.add(currentLine.toString());
        return lines;
    }

    /**
     * Given a {@code String} that represents a CSV line and an
     * {@code int} column index, returns the contents of that column.
     * Columns in the buffered reader are zero indexed.
     *
     * @param csvLine   the {@code String} containing the CSV record
     * @param csvColumn the column index of the CSV field whose contents ought to be
     *                  returned
     * @return the field of csvLine corresponding to {@code csvColumn}
     * @throws IllegalArgumentException  if {@code csvLine} is null
     * @throws IndexOutOfBoundsException if {@code csvColumn} is not a
     *                                   valid field index of the record
     */
    static String extractColumn(String csvLine, int csvColumn) {
        // TODO: Complete this method.
        if (csvLine == null || csvLine.isEmpty()) {
            throw new IllegalArgumentException("CSV line cannot be empty");
        }
        List<String> strings = parseRecord(csvLine);
        if (csvColumn < 0 || csvColumn >= strings.size()) {
            throw new IndexOutOfBoundsException("CSV column out of bounds");
        }
        if (strings.get(csvColumn) == null) {
            throw new IllegalArgumentException("CSV column is null");
        }
        return strings.get(csvColumn);
    }

    /**
     * Given a {@code BufferedReader} of CSV data and a column index, returns
     * the list of all CSV fields appearing in that column.
     *
     * <p>
     * If a line has no field at the given index, it is skipped.
     *
     * <p>
     * If the line has a field at the given index, it should be returned
     * as an element of the list.
     *
     * @param br        - a BufferedReader that represents tweets
     * @param csvColumn - the index of the column in the CSV data
     * @return a {@code List} of CSV fields (none of which is null)
     */
    static List<String> csvFieldsAtColumn(BufferedReader br, int csvColumn) {
        // TODO: Complete this method.
        if (br == null) {
            throw new IllegalArgumentException("BufferedReader cannot be null");
        }
        List<String> lines = new ArrayList<>();
        String currentLine;
        try {
            while ((currentLine = br.readLine()) != null) {
                try {
                    lines.add(extractColumn(currentLine, csvColumn));
                }
                catch (IndexOutOfBoundsException e) {
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Error reading CSV line", e);
        }
        return lines;
    }

}
