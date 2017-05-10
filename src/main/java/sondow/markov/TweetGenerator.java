package sondow.markov;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        System.out.println(new TweetGenerator(new Random()).loadAndGenerate("rikergooglingmarkov.json"));
    }

    /**
     * Processes all the js files under data/js/tweets under the specified folder on the classpath, to collect
     * tweets and then generates a single new markov phrase from those tweets.
     *
     * @param fileName name of the file containing the pre-processed json map
     * @return the new randomly generated markov phrase
     */
    public String loadAndGenerate(String fileName) {

        String phrase = null;
        try {

            ClassLoader classLoader = getClass().getClassLoader();
            String filePath = classLoader.getResource(fileName).getFile();
            File file = new File(filePath);
            String jsonMap = new String(Files.readAllBytes(Paths.get(file.getPath())));
            MarkovChain markovChain = MarkovChain.fromJson(jsonMap, random);

            // Make phrases until one of them comes out short enough to tweet.
            boolean shortPhraseMade = false;
            while (!shortPhraseMade) {
                phrase = markovChain.generatePhrase();
                if (phrase.length() <= 140) {
                    shortPhraseMade = true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return phrase;
    }
}
