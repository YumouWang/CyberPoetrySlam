package controllers.swap;

import broker.BrokerClient;
import broker.handler.IHandleBrokerMessage;
import broker.handler.ReaderThread;

/**
 * @author Nathan
 * @version 12/12/2014
 */
public class MockReaderThread extends ReaderThread {

    String msg;

    public MockReaderThread(BrokerClient broker, IHandleBrokerMessage handler) {
        super(broker, handler);
    }

    @Override
    public void sendMessage(String msg) {
        this.msg = msg;
    }
}
