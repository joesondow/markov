package sondow.markov;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Interacts with the twitter API to post tweets or do other twitter actions for a given Twitter account.
 *
 * @author @JoeSondow
 */
public class Tweeter {

    private Configuration config;

    /**
     * Creates an instance to interact with the Twitter accounts the is set up as the default for this project.
     */
    public Tweeter() {
        this(new ConfigurationBuilder().build());
    }

    /**
     * Creates an instance to interact with the Twitter account specified by the supplied configuration.
     *
     * @param config the Twitter configuration to use to authenticate
     */
    public Tweeter(Configuration config) {
        this.config = config;
    }

    /**
     * Posts a tweet.
     *
     * @param message the content to tweet
     * @return the full tweet object that was created
     */
    public Status tweet(String message) {
        Twitter twitter = new TwitterFactory(config).getInstance();
        try {
            Status status = twitter.updateStatus(message);
            System.out.println("Successfully tweeted message: " + message + " with status " + status);
            return status;
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
