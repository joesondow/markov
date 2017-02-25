package sondow.markov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generates semi-random, semi-coherent phrases based on a source corpus of text. Written mostly by @veryphatic
 * https://gist.github.com/veryphatic/3190969 with modifications by @JoeSondow.
 *
 * @author @veryphatic
 * @author @JoeSondow
 */
public class MarkovChain {

    private Map<String, List<String>> wordsToSuffixes = new HashMap<String, List<String>>();
    private Randomizer random;

    private final String START = "__start__";
    private final String END = "__end__";

    public MarkovChain(Randomizer random) {
        this.random = random;

        wordsToSuffixes.put(START, new ArrayList<String>());
        wordsToSuffixes.put(END, new ArrayList<String>());
    }

    // public void load(String corpus) {
    //
    // }
    //
    // public void load(InputStream inputStream) {
    //
    // }

    public void loadCorpus(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            processLine(line);
        }

    }

    /**
     * Process a line of text from the source corpus, to add to the markov data set.
     *
     * @param line the line to process
     */
    private void processLine(String line) {
        String[] words = line.split("\\s+");
        if (words.length >= 1) {

            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                if (i == 0 && words.length > 1) {
                    wordsToSuffixes.get(START).add(word);
                    List<String> optionsForNextWord = wordsToSuffixes.get(words[i]);
                    if (optionsForNextWord == null) {
                        optionsForNextWord = new ArrayList<String>();
                        optionsForNextWord.add(words[i + 1]);
                        wordsToSuffixes.put(word, optionsForNextWord);
                    }
                } else if (i == words.length - 1) {
                    wordsToSuffixes.get(END).add(words[i]);
                } else {
                    List<String> suffix = wordsToSuffixes.get(words[i]);
                    if (suffix == null) {
                        suffix = new ArrayList<String>();
                    }
                    suffix.add(words[i + 1]);
                    wordsToSuffixes.put(words[i], suffix);
                }
            }
        }
    }

    /*
     * Generate a markov phrase.
     */
    public void generatePhrase() {

        List<String> newPhrase = new ArrayList<String>();
        String nextWord = "";

        // Select the first word
        List<String> startWords = wordsToSuffixes.get(START);
        nextWord = random.oneOf(startWords);
        newPhrase.add(nextWord);

        boolean suffixFound = true;
        while (suffixFound) {
            List<String> options = wordsToSuffixes.get(nextWord);
            if (options == null) {
                suffixFound = false;
            } else {
                nextWord = random.oneOf(options);
                newPhrase.add(nextWord);
            }
        }

        System.out.println("New phrase: " + String.join(" ", newPhrase));
    }

    public void readCsvFile() {
        // Sigh. Do this. Or JSON.
    }
}
