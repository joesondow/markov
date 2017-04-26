package sondow.markov;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import twitter4j.JSONException;

public class MarkovChainTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testParseAndLoadCorpusAndOutputToJson() throws IOException, JSONException {

        ClassLoader classLoader = getClass().getClassLoader();
        String folderPath = classLoader.getResource("testarchive/data/js/tweets/").getFile();
        File folder = new File(folderPath);

        JsonParser parser = new JsonParser();

        List<String> corpus = parser.parse(folder);
        MarkovChain markov = new MarkovChain(new Random()).loadCorpus(corpus);
        String actualMarkovJson = markov.toJson();
        System.out.println(actualMarkovJson);
        String markovFilePath = classLoader.getResource("picardtipsmarkov.json").getFile();
        String expectedMarkovJson = new String(Files.readAllBytes(Paths.get(markovFilePath)));

        assertEquals(expectedMarkovJson, actualMarkovJson);

        String prettyFilePath = classLoader.getResource("pretty.json").getFile();
        String expectedPretty = new String(Files.readAllBytes(Paths.get(prettyFilePath)));
        String actualPretty = markov.toJsonPrettyPrinted();

        assertEquals(expectedPretty, actualPretty);
    }

    @Test
    public void testFromJson() throws IOException, JSONException {
        ClassLoader classLoader = getClass().getClassLoader();
        String markovFilePath = classLoader.getResource("picardtipsmarkov.json").getFile();
        String markovJsonFromFile = new String(Files.readAllBytes(Paths.get(markovFilePath)));
        MarkovChain markov = MarkovChain.fromJson(markovJsonFromFile, new Random());
        assertEquals(markovJsonFromFile, markov.toJson());
    }

    @Test
    public void testGeneratePhrase() throws IOException, JSONException {
        ClassLoader classLoader = getClass().getClassLoader();
        String markovFilePath = classLoader.getResource("picardtipsmarkov.json").getFile();
        String markovJsonFromFile = new String(Files.readAllBytes(Paths.get(markovFilePath)));
        MarkovChain markov = MarkovChain.fromJson(markovJsonFromFile, new Random(1L));
        String phrase1 = markov.generatePhrase();
        String phrase2 = markov.generatePhrase();
        String phrase3 = markov.generatePhrase();
        String phrase4 = markov.generatePhrase();

        assertEquals("Picard management tip: Tolerate failure, not incompetence. Learn the people can handle "
                + "things fine without you.", phrase1);
        assertEquals("Picard management tip: If you lose one crew member, mourn appropriately. If you aren't "
                + "going boldly enough.", phrase2);
        assertEquals("Picard management tip: Tolerate failure, not sure what to take a volunteer. Don't assume "
                + "anything is possible or ask for a dramatic pause. Maybe somebody else will fix it.", phrase3);
        assertEquals("Picard management tip: If the people can handle things fine without you.", phrase4);
    }

}
