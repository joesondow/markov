package sondow.markov;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import twitter4j.JSONException;

/**
 * When you get an archive of data from Twitter, it's very large and filled with extraneous data. This class
 * converts a set of raw Twitter archive json files to a single json file that simply contains the fully
 * pre-processed Markov Chain map.
 *
 * @author @JoeSondow
 */
public class DataConverter {

    /**
     * Converts raw Twitter archive data to a pre-processed, condensed Markov CHain data form.
     *
     * The input folder is assumed to be in this project under src/main/resources/ and it is assumed to contain
     * nested folders as Twitter delivers them, in the form ./data/js/tweets/ which then contain one or more *.js
     * files containing full tweet data.
     *
     * @param inputFolderName the name of the source folder under src/main/resources/ and containing nested
     *            folders data/js/tweets/
     * @param outputFileName the name of the fully pre-processed json file to be saved, including file extension
     * @return the newly created pre-processed data file that should get saved and committed
     * @throws JSONException
     * @throws IOException
     */
    public File convertTwitterArchiveToMarkovJson(String inputFolderName, String outputFileName)
            throws IOException, JSONException {
        MarkovChain markov = MarkovChain.loadFromArchive(inputFolderName);
        String preProcessedData = markov.toJson();
        Path newFilePath = Paths.get("src/main/resources/" + outputFileName);
        newFilePath.toFile().createNewFile();
        Path filePath = Files.write(newFilePath, preProcessedData.getBytes());
        return filePath.toFile();
    }

    public static void main(String[] args) throws IOException, JSONException {
        DataConverter converter = new DataConverter();
        converter.convertTwitterArchiveToMarkovJson("picardtips", "picardtipsmarkov.json");
        converter.convertTwitterArchiveToMarkovJson("rikergoogling", "rikergooglingmarkov.json");
        converter.convertTwitterArchiveToMarkovJson("joesondow", "joesondowmarkov.json");
    }
}
