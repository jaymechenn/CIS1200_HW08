package org.cis1200;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for MarkovChain */
public class MarkovChainTest {

    /*
     * Writing tests for Markov Chain can be a little tricky.
     * We provide a few tests below to help you out, but you still need
     * to write your own.
     */

    /**
     * Helper function to make it easier to create singleton sets of Strings;
     * use this function in your tests as needed.
     *
     * @param s - the String to add to the set
     * @return - a Set containing just s
     */
    private static Set<String> singleton(String s) {
        Set<String> set = new TreeSet<>();
        set.add(s);
        return set;
    }

    /* **** ****** ***** ***** EXAMPLE TWEETS ***** ***** ****** **** */

    /*
     * Test your MarkovChain implementation!
     * Run this test case and check the printed results to see whether
     * your MarkovChain training agrees with the output below.
     *
     * ILLUSTRATIVE EXAMPLE MARKOV CHAIN:
     * startTokens: { "a":2 }
     * bigramFrequencies:
     * "!": { "and":1 }
     * "?": { "<END>":1 }
     * "a": { "banana":2 "chair":1 "table":1 }
     * "and": { "a":2 }
     * "banana": { "!":1 "?":1 }
     * "chair": { "<END>":1 }
     * "table": { "and":1 }
     *
     * We have started this test case for you. Add additional code to the test case
     * to completely characterize the state of the MarkovChain.
     */
    @Test
    public void testIllustrativeExampleMarkovChain() {
        /*
         * Note: we provide the pre-parsed sequence of tokens.
         */
        String[] tweet1 = { "a", "table", "and", "a", "chair" };
        String[] tweet2 = { "a", "banana", "!", "and", "a", "banana", "?" };

        MarkovChain mc = new MarkovChain();
        mc.addSequence(Arrays.stream(tweet1).iterator());
        mc.addSequence(Arrays.stream(tweet2).iterator());

        // Print out the Markov chain
        System.out.println("ILLUSTRATIVE EXAMPLE MARKOV CHAIN:\n" + mc);

        ProbabilityDistribution<String> pdBang = mc.get("!");
        assertEquals(singleton("and"), pdBang.keySet());
        assertEquals(1, pdBang.count("and"));

        ProbabilityDistribution<String> pdQuestion = mc.get("?");
        assertEquals(singleton(MarkovChain.END_TOKEN), pdQuestion.keySet());
        assertEquals(1, pdQuestion.count(MarkovChain.END_TOKEN));

        assertEquals(2, mc.startTokens.getTotal());
        assertEquals(2, mc.startTokens.count("a"));
        ProbabilityDistribution<String> pdA = mc.get("a");
        Set<String> keysA = new TreeSet<>();
        keysA.add("banana");
        keysA.add("chair");
        keysA.add("table");
        assertEquals(keysA, pdA.keySet());
        assertEquals(2, pdA.count("banana"));
        assertEquals(1, pdA.count("chair"));
        assertEquals(1, pdA.count("table"));

        // TODO: Add more assertions to this test case to fully characterize state of mc
    }

    /* **** ****** **** **** ADD BIGRAMS TESTS **** **** ****** **** */

    /* Here's an example test case. Be sure to add your own as well */
    @Test
    public void testAddBigram() {
        MarkovChain mc = new MarkovChain();
        mc.addBigram("1", "2");
        assertTrue(mc.bigramFrequencies.containsKey("1"));
        ProbabilityDistribution<String> pd = mc.bigramFrequencies.get("1");
        assertTrue(pd.getRecords().containsKey("2"));
        assertEquals(1, pd.count("2"));
    }

    /* ***** ****** ***** ***** ADD SEQUENCE TESTS ***** ***** ****** ***** */

    /* Here's an example test case. Be sure to add your own as well */
    @Test
    public void testAddSequence() {
        MarkovChain mc = new MarkovChain();
        String sentence = "1 2 3";
        mc.addSequence(Arrays.stream(sentence.split(" ")).iterator());
        assertEquals(3, mc.bigramFrequencies.size());
        ProbabilityDistribution<String> pd1 = mc.bigramFrequencies.get("1");
        assertTrue(pd1.getRecords().containsKey("2"));
        assertEquals(1, pd1.count("2"));
        ProbabilityDistribution<String> pd2 = mc.bigramFrequencies.get("2");
        assertTrue(pd2.getRecords().containsKey("3"));
        assertEquals(1, pd2.count("3"));
        ProbabilityDistribution<String> pd3 = mc.bigramFrequencies.get("3");
        assertTrue(pd3.getRecords().containsKey(MarkovChain.END_TOKEN));
        assertEquals(1, pd3.count(MarkovChain.END_TOKEN));
    }

    /* **** ****** ****** MARKOV CHAIN CLASS TESTS ***** ****** ***** */

    /*
     * Here's an example test case for walking through the Markov Chain.
     * Be sure to add your own as well
     */
    @Test
    public void testWalk() {
        /*
         * Using the training data "CIS 1200 rocks" and "CIS 1200 beats CIS 1600",
         * we're going to put some bigrams into the Markov Chain.
         *
         * The given sequence of numbers acts as a path through the Markov Model
         * that should be followed by {@code walk}. Note that the sequence
         * includes a choice for {@code END_TOKEN}, so the length is one longer
         * than the {@code expectedTokens}.
         *
         */

        String[] expectedTokens = { "CIS", "1200", "beats", "CIS", "1200", "rocks" };
        MarkovChain mc = new MarkovChain();

        String sentence1 = "CIS 1200 rocks";
        String sentence2 = "CIS 1200 beats CIS 1600";
        mc.addSequence(Arrays.stream(sentence1.split(" ")).iterator());
        mc.addSequence(Arrays.stream(sentence2.split(" ")).iterator());

        // it can be illustrative to print out the state of the Markov Chain at this
        // point
        System.out.println(mc);

        Integer[] seq = { 0, 0, 0, 0, 0, 1, 0 };
        List<Integer> choices = new ArrayList<>(Arrays.asList(seq));
        Iterator<String> walk = mc.getWalk(new ListNumberGenerator(choices));
        for (String token : expectedTokens) {
            assertTrue(walk.hasNext());
            assertEquals(token, walk.next());
        }
        assertFalse(walk.hasNext());

    }

    @Test
    public void testWalk2() {
        /* We can also use the provided method */

        String[] expectedWords = { "CIS", "1600" };
        MarkovChain mc = new MarkovChain();

        String sentence1 = "CIS 1200 rocks";
        String sentence2 = "CIS 1200 beats CIS 1600";
        mc.addSequence(Arrays.stream(sentence1.split(" ")).iterator());
        mc.addSequence(Arrays.stream(sentence2.split(" ")).iterator());

        List<Integer> choices = mc.findWalkChoices(new ArrayList<>(Arrays.asList(expectedWords)));
        Iterator<String> walk = mc.getWalk(new ListNumberGenerator(choices));
        for (String word : expectedWords) {
            assertTrue(walk.hasNext());
            assertEquals(word, walk.next());
        }

    }

}
