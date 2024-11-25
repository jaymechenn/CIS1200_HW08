package org.cis1200;

import org.junit.jupiter.api.Test;
import java.io.StringReader;
import java.io.BufferedReader;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for LineIterator */
public class LineIteratorTest {

    /*
     * Here's a test to help you out, but you still need to write your own.
     */

    @Test
    public void testHasNextAndNext() {

        // Note we don't need to create a new file here in order to
        // test out our LineIterator if we do not want to. We can just
        // create a StringReader to make testing easy!
        String words = "0, The end should come here.\n"
                + "1, This comes from data with no duplicate words!";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        LineIterator li = new LineIterator(br);
        assertTrue(li.hasNext());
        assertEquals("0, The end should come here.", li.next());
        assertTrue(li.hasNext());
        assertEquals("1, This comes from data with no duplicate words!", li.next());
        assertFalse(li.hasNext());
    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */

    @Test
    public void testEmptyInput() {
        String empty = "";
        StringReader sr = new StringReader(empty);
        BufferedReader br = new BufferedReader(sr);
        LineIterator li = new LineIterator(br);
        assertFalse(li.hasNext());
        assertThrows(NoSuchElementException.class, li::next);
    }

    @Test
    public void testSingleLineInput() {
        String singleLine = "This is the only line.";
        StringReader sr = new StringReader(singleLine);
        BufferedReader br = new BufferedReader(sr);
        LineIterator li = new LineIterator(br);
        assertTrue(li.hasNext());
        assertEquals("This is the only line.", li.next());
        assertFalse(li.hasNext());
        assertThrows(NoSuchElementException.class, li::next);
    }

    @Test
    public void testMultipleLinesInput() {
        String multipleLines = "First line.\nSecond line.\nThird line.";
        StringReader sr = new StringReader(multipleLines);
        BufferedReader br = new BufferedReader(sr);
        LineIterator li = new LineIterator(br);

        assertTrue(li.hasNext());
        assertEquals("First line.", li.next());
        assertTrue(li.hasNext());
        assertEquals("Second line.", li.next());
        assertTrue(li.hasNext());
        assertEquals("Third line.", li.next());
        assertFalse(li.hasNext());
    }

    @Test
    public void testIteratorThrowsExceptionAfterEnd() {
        String words = "Line 1.\nLine 2.";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        LineIterator li = new LineIterator(br);

        assertTrue(li.hasNext());
        assertEquals("Line 1.", li.next());
        assertTrue(li.hasNext());
        assertEquals("Line 2.", li.next());
        assertFalse(li.hasNext());
        assertThrows(NoSuchElementException.class, li::next);
    }

    @Test
    public void testIOExceptionHandling() {
        BufferedReader br = null; // Simulate an invalid reader
        assertThrows(IllegalArgumentException.class, () -> new LineIterator(br));
    }

    @Test
    public void testHasNextDoesNotAdvance() {
        String lines = "Line 1.\nLine 2.";
        StringReader sr = new StringReader(lines);
        BufferedReader br = new BufferedReader(sr);
        LineIterator li = new LineIterator(br);

        assertTrue(li.hasNext());
        assertTrue(li.hasNext()); // hasNext() called multiple times
        assertEquals("Line 1.", li.next());
        assertTrue(li.hasNext());
        assertEquals("Line 2.", li.next());
        assertFalse(li.hasNext());
    }

}
