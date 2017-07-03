package sondow.markov;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;

/**
 * Slurps in lots of JSON files from a Twitter account export, and stores them in memory in a form
 * that's useful for supplying a Markov generator with a corpus of source text.
 *
 * @author @JoeSondow
 */
public class JsonParser {

    /**
     * Go through all the js files in the directory where they're stored. Remove garbage first line
     * of each js file because that's how Twitter rolls. Collect the text of each tweet record that
     * is not a retweet.
     *
     * @param folder the directory where the data-filled js files are stored.
     * @return List < String > the texts of the tweets
     * @throws IOException if there's a problem reading the files
     * @throws JSONException is there's a problem parsing the files
     */
    public List<String> parse(File folder) throws IOException, JSONException {
        File[] files = folder.listFiles();
        List<String> sourcePhrases = new ArrayList<String>();
        for (File file : files) {
            if (file.getName().endsWith(".js")) {
                String contents = new String(Files.readAllBytes(Paths.get(file.getPath())));
                // Remove garbage first line of each js file because that's how
                // Twitter rolls.
                contents = contents.substring(contents.indexOf('\n') + 1);
                JSONArray jsonArray = new JSONArray(contents);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    // If it's a retweet, skip it.
                    if (!jsonObject.has("retweeted_status")) {
                        String text = jsonObject.getString("text");
                        sourcePhrases.add(text);
                    }
                }
            }
        }
        return sourcePhrases;
    }
}
