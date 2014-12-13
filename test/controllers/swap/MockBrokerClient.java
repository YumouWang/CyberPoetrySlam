package controllers.swap;

import broker.BrokerClient;

import java.io.PrintWriter;

/**
 * @author Nathan
 * @version 12/12/2014
 */
public class MockBrokerClient extends BrokerClient {

    MockPrintWriter writer;

    public MockBrokerClient() {
        super("", 0);
    }

    @Override
    public String getID() {
        return "2";
    }

    @Override
    public PrintWriter getBrokerOutput() {
        return writer;
    }
}
