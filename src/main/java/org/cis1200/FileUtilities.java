package org.cis1200;

import java.io.*;
import java.util.List;

/**
 * This class organizes some static methods for working with File IO.
 */
public class FileUtilities {

    /**
     * Takes in a filename and creates a BufferedReader.
     * See Java's documentation for BufferedReader to learn how to construct one
     * given a path to a file.
     *
     * @param filePath the path to the CSV file to be turned to a
     *                 BufferedReader
     * @return a BufferedReader of the provided file contents
     * @throws IllegalArgumentException if filePath is null or if the file
     *                                  doesn't exist
     */
    public static BufferedReader fileToReader(String filePath) {
        // TODO: Complete this method.
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null");
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            return reader;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("File not found: " + filePath);
        }
    }

    /**
     * Given a {@code List} of {@code String}s, writes them to a file (one
     * {@code String} per
     * line in the file). This method uses {@code BufferedWriter}, the flip side to
     * {@code BufferedReader}. It may be useful to look at the JavaDocs for
     * {@code FileWriter}.
     * <p>
     * You may assume none of the arguments or strings passed in will be null.
     * <p>
     * If the process of opening the file or writing the data triggers an
     * {@code IOException}, you
     * should catch it and stop writing. (You can also print an error message to the
     * terminal, but we will not test that behavior.)
     *
     * @param stringsToWrite A List of Strings to write to the file
     * @param filePath       the string containing the path to the file where
     *                       the tweets should be written
     * @param append         a boolean indicating whether the new tweets
     *                       should be appended to the current file or should
     *                       overwrite its previous contents
     */
    public static void writeStringsToFile(
            List<String> stringsToWrite, String filePath,
            boolean append) {
        BufferedWriter bw;
        // TODO: Complete this method.
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null");
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append));
            for (String s : stringsToWrite) {
                writer.write(s);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to file: " + filePath);
        }
    }
}
