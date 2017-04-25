package sondow.markov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Generates semi-random, semi-coherent phrases based on a source corpus of text. Written by @veryphatic
 * https://gist.github.com/veryphatic/3190969 with modifications by @JoeSondow.
 *
 * @author @veryphatic
 * @author @JoeSondow
 */
public class MarkovChain {

    private Map<String, List<String>> wordsToSuffixes = new HashMap<String, List<String>>();
    private Random random;

    private final String START = "__start__";
    private final String END = "__end__";

    public MarkovChain(Random random) {
        this.random = random;

        wordsToSuffixes.put(START, new ArrayList<String>());
        wordsToSuffixes.put(END, new ArrayList<String>());
    }

    /**
     * A way to view the contents of the markov data structure for debugging purposes.
     */
    public void dump() {
        for (String key : wordsToSuffixes.keySet()) {
            List<String> values = wordsToSuffixes.get(key);
            System.out.println(key + ": " + values);
        }
    }

    /**
     * Fills in the markov data structure from a corpus of text input in the form of a list of strings.
     *
     * @param corpus the source text
     * @return this MarkovChain, for method chaining
     */
    public MarkovChain loadCorpus(List<String> corpus) {
        for (String phrase : corpus) {
            processPhrase(phrase);
        }
        return this;
    }

    /**
     * Process a unit of text from the source corpus, to add to the markov data set.
     *
     * @param phrase the phrase to process
     */
    private void processPhrase(String phrase) {
        String[] words = phrase.split("\\s+");
        if (words.length >= 2) {

            for (int i = 0; i < words.length; i++) {
                String thisWord = words[i];
                int finalIndex = words.length - 1;
                String nextWord = null;
                if (i < finalIndex) {
                    nextWord = words[i + 1];
                }

                // Omit twitter usernames. I don't want my bots talking to every person I've ever talked to.
                // I also don't want links to things, and I don't want to spam any hashtags I've used before.
                List<String> prefixBlacklist = Arrays.asList("@", "\"@", ".@", "http", "#", "\"#");

                boolean skipThisWord = false;
                for (String unwantedPrefix : prefixBlacklist) {
                    if (thisWord.startsWith(unwantedPrefix)) {
                        skipThisWord = true;
                        break;
                    }
                }
                boolean skipNextWord = (nextWord == null);
                if (nextWord != null) {
                    for (String unwantedPrefix : prefixBlacklist) {
                        if (nextWord.startsWith(unwantedPrefix)) {
                            skipNextWord = true;
                            break;
                        }
                    }
                }

                if (!skipThisWord) {
                    if (i == finalIndex) {
                        wordsToSuffixes.get(END).add(thisWord);
                    } else {
                        if (i == 0) {
                            wordsToSuffixes.get(START).add(thisWord);
                        }
                        if (!skipNextWord) {
                            List<String> optionsForNextWord = wordsToSuffixes.get(thisWord);
                            if (optionsForNextWord == null) {
                                optionsForNextWord = new ArrayList<String>();
                            }
                            optionsForNextWord.add(nextWord);
                            wordsToSuffixes.put(thisWord, optionsForNextWord);
                        }
                    }
                }
            }
        }
    }

    /**
     * Generates a markov phrase.
     */
    public String generatePhrase() {

        List<String> phrase = new ArrayList<String>();
        List<String> startWords = wordsToSuffixes.get(START);
        String nextWord = startWords.get(random.nextInt(startWords.size()));
        phrase.add(nextWord);

        boolean suffixFound = true;
        while (suffixFound) {
            List<String> options = wordsToSuffixes.get(nextWord);
            if (options == null) {
                suffixFound = false;
            } else {
                nextWord = options.get(random.nextInt(options.size()));
                phrase.add(nextWord);
            }
        }

        return String.join(" ", phrase);
    }
}
