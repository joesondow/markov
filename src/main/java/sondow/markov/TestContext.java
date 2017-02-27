package sondow.markov;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

/**
 * Empty stub implementation of Context, for testing. Useful because aws-lambda-java-core does not contain an
 * implementation, and the handler method Lambda will call expects a Context to be passed in.
 *
 * @author @JoeSondow
 */
public class TestContext implements Context {

    /*
     * (non-Javadoc)
     *
     * @see com.amazonaws.services.lambda.runtime.Context#getAwsRequestId()
     */
    @Override
    public String getAwsRequestId() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.amazonaws.services.lambda.runtime.Context#getLogGroupName()
     */
    @Override
    public String getLogGroupName() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.amazonaws.services.lambda.runtime.Context#getLogStreamName()
     */
    @Override
    public String getLogStreamName() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.amazonaws.services.lambda.runtime.Context#getFunctionName()
     */
    @Override
    public String getFunctionName() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.amazonaws.services.lambda.runtime.Context#getFunctionVersion()
     */
    @Override
    public String getFunctionVersion() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.amazonaws.services.lambda.runtime.Context#getInvokedFunctionArn()
     */
    @Override
    public String getInvokedFunctionArn() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.amazonaws.services.lambda.runtime.Context#getIdentity()
     */
    @Override
    public CognitoIdentity getIdentity() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.amazonaws.services.lambda.runtime.Context#getClientContext()
     */
    @Override
    public ClientContext getClientContext() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.amazonaws.services.lambda.runtime.Context#getRemainingTimeInMillis()
     */
    @Override
    public int getRemainingTimeInMillis() {
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.amazonaws.services.lambda.runtime.Context#getMemoryLimitInMB()
     */
    @Override
    public int getMemoryLimitInMB() {
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.amazonaws.services.lambda.runtime.Context#getLogger()
     */
    @Override
    public LambdaLogger getLogger() {
        return null;
    }

}
