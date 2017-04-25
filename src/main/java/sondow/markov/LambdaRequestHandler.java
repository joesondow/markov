package sondow.markov;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * The function that AWS Lambda will invoke.
 *
 * @author @JoeSondow
 */
public class LambdaRequestHandler implements RequestHandler<Object, Object> {

    /*
     * (non-Javadoc)
     *
     * @see com.amazonaws.services.lambda.runtime.RequestHandler#handleRequest(java. lang.Object,
     * com.amazonaws.services.lambda.runtime.Context)
     */
    @Override
    public Object handleRequest(Object input, Context context) {

        ConfigurationBuilder cb = new ConfigurationBuilder();

        // Get the source twitter account and target twitter account from environment variables. Pick a random
        // one. The expected format has pairs as source, then a hyphen, then the target, with pairs separated by
        // commas:
        // joesondow-joeebooks,rikergoogling-rikerebooks,picardtips-picardebooks
        String sourceTargetTwitterAccountPairsString = System.getenv("twitter_account_pairs_csv");
        if (sourceTargetTwitterAccountPairsString == null) {
            throw new RuntimeException("Nothing set in env var twitter_account_pairs_csv");
        }
        List<String> srcTgtAcctPairs = Arrays.asList(sourceTargetTwitterAccountPairsString.split(","));
        int accountCount = srcTgtAcctPairs.size();
        int nowHour = new GregorianCalendar(TimeZone.getTimeZone("UTC")).get(Calendar.HOUR_OF_DAY);
        int remainder = (nowHour + 0) % accountCount;

        // If nowHour is 12, accountCount is 3, remainder is 0.
        // If nowHour is 11, accountCount is 3, remainder is 2.
        // If nowHour is 10, accountCount is 3, remainder is 1.
        // If nowHour is 9, accountCount is 3, remainder is 0.
        // Refactor and unit test this shiz
        String sourceTargetPairString = srcTgtAcctPairs.get(remainder);
        System.out.println("sourceTargetPairString: " + sourceTargetPairString + ", accountCount: " + accountCount
                + ", nowHour: " + nowHour + ", remainder: " + remainder);
        List<String> sourceAndTargetAccount = Arrays.asList(sourceTargetPairString.split("-"));
        String sourceAccount = sourceAndTargetAccount.get(0);
        String targetAccount = sourceAndTargetAccount.get(1);

        // AWS Lambda only allows underscores in environment variables, not dots.
        cb.setOAuthConsumerKey(System.getenv(targetAccount + "_twitter4j_oauth_consumerKey"));
        cb.setOAuthConsumerSecret(System.getenv(targetAccount + "_twitter4j_oauth_consumerSecret"));
        cb.setOAuthAccessToken(System.getenv(targetAccount + "_twitter4j_oauth_accessToken"));
        cb.setOAuthAccessTokenSecret(System.getenv(targetAccount + "_twitter4j_oauth_accessTokenSecret"));
        Configuration config = cb.setTrimUserEnabled(true).build();

        String message = new TweetGenerator().loadAndGenerate(sourceAccount.toLowerCase());
        if (message == null || message.isEmpty()) {
            throw new RuntimeException("What up with the empty message?");
        }
        Tweeter tweeter = new Tweeter(config);
        return tweeter.tweet(message);
    }

    /**
     * Manual testing on my dev machine.
     */
    public static void main(String[] args) {
        LambdaRequestHandler handler = new LambdaRequestHandler();
        handler.handleRequest(null, null);
    }
}
