package org.cis1200;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/** Tests for TwitterBot class */
public class TwitterBotTest {
    // A helper function for creating lists of strings
    private static List<String> listOfArray(String[] words) {
        List<String> l = new LinkedList<>();
        Collections.addAll(l, words);
        return l;
    }

    /*
     * The below test case test whether your TwitterBot class itself is written
     * correctly. To generate a tweet of specific content, make a walk
     * containing the indices of the words you want to appear in the tweet and
     * use that as the {@code NumberGenerator}
     */

    private static final String[] TWEET_1 = { "a", "table", "and", "a", "chair" };
    private static final String[] TWEET_2 = { "a", "banana", "!", "and", "a", "banana", "?" };

    private static List<List<String>> getTestTrainingDataExample() {
        List<List<String>> trainingData = new LinkedList<>();
        trainingData.add(listOfArray(TWEET_1));
        trainingData.add(listOfArray(TWEET_2));
        return trainingData;
    }

    @Test
    public void generatorWorks() {
        TwitterBot tb = new TwitterBot(getTestTrainingDataExample());

        String expected = "a banana?";
        int[] walk = { 0, 0, 1, 0 };
        NumberGenerator ng = new ListNumberGenerator(walk);
        assertEquals(expected, tb.generateTweet(ng));
    }

    /**
     * When the training data is empty, your TwitterBot should create an empty
     * MarkovChain.
     * An empty tweet should be generated by your bot.
     * No exceptions should be thrown and your program should not go into an
     * infinite loop!
     */
    @Test
    public void emptyTrainingDataCreatesEmptyTweet() {
        // Checks that your program does not go into an infinite loop
        assertTimeoutPreemptively(
                Duration.ofSeconds(10), () -> {
                    // No exceptions are thrown if training data is empty
                    TwitterBot tb = new TwitterBot(new LinkedList<>());
                    // Checks that the bot creates an empty tweet
                    assertEquals(0, tb.generateTweet().length());
                }
        );
    }

    /*
     * Add additional test cases here, modeled after the generatorWorks test shown
     * above.
     */

    @Test
    public void testGeneratorWithSimpleData() {
        String[] tweet = { "hello", "world" };
        List<List<String>> trainingData = new LinkedList<>();
        trainingData.add(listOfArray(tweet));

        TwitterBot tb = new TwitterBot(trainingData);

        String expected = "hello world";
        int[] walk = { 0, 0 };
        NumberGenerator ng = new ListNumberGenerator(walk);
        assertEquals(expected, tb.generateTweet(ng));
    }

    @Test
    public void testGeneratorWithPunctuation() {
        String[] tweet = { "hello", "world", "!" };
        List<List<String>> trainingData = new LinkedList<>();
        trainingData.add(listOfArray(tweet));

        TwitterBot tb = new TwitterBot(trainingData);

        String expected = "hello world!";
        int[] walk = { 0, 0, 0 };
        NumberGenerator ng = new ListNumberGenerator(walk);
        assertEquals(expected, tb.generateTweet(ng));
    }

    @Test
    public void testGeneratorHandlesRepeats() {
        String[] tweet = { "repeat", "this", "repeat", "this" };
        List<List<String>> trainingData = new LinkedList<>();
        trainingData.add(listOfArray(tweet));

        TwitterBot tb = new TwitterBot(trainingData);

        String expected = "repeat this repeat this";
        int[] walk = { 0, 0, 1, 0 };
        NumberGenerator ng = new ListNumberGenerator(walk);
        assertEquals(expected, tb.generateTweet(ng));
    }

    @Test
    public void testGeneratorHandlesMultipleTweets() {
        String[] tweet1 = { "good", "morning" };
        String[] tweet2 = { "good", "night" };
        List<List<String>> trainingData = new LinkedList<>();
        trainingData.add(listOfArray(tweet1));
        trainingData.add(listOfArray(tweet2));

        TwitterBot tb = new TwitterBot(trainingData);

        String expected = "good morning";
        int[] walk = { 0, 0 };
        NumberGenerator ng = new ListNumberGenerator(walk);
        assertEquals(expected, tb.generateTweet(ng));

        expected = "good night";
        walk = new int[] { 0, 1 };
        ng = new ListNumberGenerator(walk);
        assertEquals(expected, tb.generateTweet(ng));
    }

    @Test
    public void testGeneratorLongTweet() {
        String[] tweet = { "a", "very", "long", "tweet", "to", "generate", "properly" };
        List<List<String>> trainingData = new LinkedList<>();
        trainingData.add(listOfArray(tweet));

        TwitterBot tb = new TwitterBot(trainingData);

        String expected = "a very long tweet to generate properly";
        int[] walk = { 0, 0, 0, 0, 0, 0, 0 };
        NumberGenerator ng = new ListNumberGenerator(walk);
        assertEquals(expected, tb.generateTweet(ng));
    }


}
