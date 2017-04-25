package sondow.markov;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import twitter4j.JSONException;

/**
 * Processes all the source data for a specific account, and creates a new markov phrase from that data.
 *
 * @author @JoeSondow
 */
public class TweetGenerator {

    Random random;

    public TweetGenerator(Random random) {
        this.random = random;
    }

    /**
     * Manual test.
     */
    public static void main(String[] args) {
        System.out.println(new TweetGenerator(new Random()).loadAndGenerate("picardtips"));
    }

    /**
     * Processes all the js files under data/js/tweets under the specified folder on the classpath, to collect
     * tweets and then generates a single new markov phrase from those tweets.
     *
     * @param folderName name of the folder on the classpath under which data/js/tweets contains all the js files
     * @return the new randomly generated markov phrase
     */
    public String loadAndGenerate(String folderName) {
        JsonParser parser = new JsonParser();

        ClassLoader classLoader = getClass().getClassLoader();
        String folderPath = classLoader.getResource(folderName + "/data/js/tweets/").getFile();
        File folder = new File(folderPath);
        String phrase = null;
        try {
            List<String> corpus = parser.parse(folder);
            MarkovChain markovChain = new MarkovChain(random).loadCorpus(corpus);

            // Make phrases until one of them comes out short enough to tweet.
            boolean shortPhraseMade = false;
            while (!shortPhraseMade) {
                phrase = markovChain.generatePhrase();
                if (phrase.length() <= 140) {
                    shortPhraseMade = true;
                }
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

        return phrase;
    }
}
