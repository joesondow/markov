package sondow.markov;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import twitter4j.JSONException;

/**
 * Tests parsing of JSON files.
 *
 * @author @JoeSondow
 */
public class JsonParserTest {

    @Test
    public void testParse() throws IOException, JSONException {

        ClassLoader classLoader = getClass().getClassLoader();
        String folderPath = classLoader.getResource("testarchive/data/js/tweets/").getFile();
        File folder = new File(folderPath);

        JsonParser parser = new JsonParser();

        List<String> actual = parser.parse(folder);
        List<String> expected = Arrays.asList("Picard management tip: When you stand up, straighten your shirt.",
                "Picard management tip: Say yes whenever possible, except when your tactical officer recommends attacking.",
                "Picard management tip: Enunciate.", "Picard management tip: Ensure chocolate is available.",
                "Picard programming tip: The first version of any software will be buggy. That's why so many holodeck programs try to kill you.",
                "Picard management tip: Hire aliens. It's no big deal.",
                "Picard management tip: Tolerate failure, not incompetence. Learn the difference.",
                "Picard management tip: If you're not sure what to say, take a deep breath and a dramatic pause. Maybe somebody else will talk first.",
                "Picard management tip: If you never fail, you aren't going boldly enough.",
                "Picard management tip: Ignore rules that make no sense.",
                "Picard management tip: If you lose one crew member, mourn appropriately. If the whole ship explodes, don't worry; time travel will fix it.",
                "Picard management tip: For each command, assign it to one person, or ask for a volunteer. Don't ask a crowd to take an action.",
                "Picard management tip: Leave the bridge sometimes. Other people can handle things fine without you.",
                "Picard management tip: If people accuse your organization of endangering people who wear red shirts, put on a damn red shirt every day.",
                "Picard management tip: Give your crew the best technology available and you'll be amazed what they can do for your enterprise.",
                "Picard management tip: Don't assume anything is possible or impossible until you've asked the people who will be doing the work.");
        assertEquals(actual, expected);
    }

}
