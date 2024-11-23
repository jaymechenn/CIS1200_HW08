package org.cis1200;

import java.io.IOException;
import java.util.Iterator;
import java.io.BufferedReader;

/**
 * {@code LineIterator} provides a useful wrapper around Java's provided
 * {@code BufferedReader} and provides practice with implementing an
 * {@code Iterator}. Your
 * solution should not read the entire file into memory at once, instead reading
 * a line whenever the {@code next()} method is called.
 * <p>
 * Notes:
 * - Any {@code IOExceptions} thrown be caught and handled properly.
 *
 * - Do not use the {@code ready()} method from {@code BufferedReader}.
 */
public class LineIterator implements Iterator<String> {

    // TODO: Add the fields needed to implement your LineIterator
    private BufferedReader reader;
    private String nextLine;
    private boolean hasNext;

    /**
     * Constructs a {@code LineIterator} for reader. Feel free to create and
     * instantiate any variables that your implementation requires here. See
     * recitation and lecture notes for guidance.
     * <p>
     * If an IOException is thrown by the BufferedReader, then hasNext should
     * return false.
     * <p>
     * The only method that should be called on BufferedReader is readLine() and
     * close(). You cannot call any other methods.
     *
     * @param reader - A reader to be turned to an Iterator
     * @throws IllegalArgumentException if reader is null
     */
    public LineIterator(BufferedReader reader) {
        // TODO: Complete this constructor.
        if (reader == null) {
            throw new IllegalArgumentException("reader is null");
        }
        this.reader = reader;
        try {
            this.nextLine = reader.readLine();
            this.hasNext = (this.nextLine != null);
        }
        catch (IOException e) {
            this.hasNext = false;
            this.nextLine = null;
        }
    }

    /**
     * Creates a LineIterator from a provided filePath by creating a
     * FileReader and BufferedReader for the file.
     * <p>
     * This constructor is implemented for you. DO NOT MODIFY THIS CONSTRUCTOR.
     * 
     * @param filePath - a string representing the file
     * @throws IllegalArgumentException if filePath is null or if the file
     *                                  doesn't exist
     */
    public LineIterator(String filePath) {
        this(FileUtilities.fileToReader(filePath));
    }

    /**
     * Returns true if there are lines left to read in the file, and false
     * otherwise.
     * <p>
     * If there are no more lines left, this method attempts to close the
     * BufferedReader. In case of an IOException during the closing process,
     * an error message is printed to the console indicating the issue.
     *
     * @return a boolean indicating whether the LineIterator can produce
     *         another line from the file
     */
    @Override
    public boolean hasNext() {
        return false; // TODO: Complete this method.
    }

    /**
     * Returns the next line from the file, or throws a NoSuchElementException
     * if there are no more strings left to return (i.e. hasNext() is false).
     * <p>
     * This method also advances the iterator in preparation for another
     * invocation. If an IOException is thrown during a next() call, your
     * iterator should make note of this such that future calls of hasNext()
     * will return false and future calls of next() will throw a
     * NoSuchElementException
     *
     * @return the next line in the file
     * @throws java.util.NoSuchElementException if there is no more data in the
     *                                          file
     */
    @Override
    public String next() {
        return null; // TODO: Complete this method.

    }
}
