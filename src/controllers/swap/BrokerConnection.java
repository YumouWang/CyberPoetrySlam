package controllers.swap;

import broker.BrokerClient;
import broker.handler.ReaderThread;
import broker.util.IProtocol;
import models.GameState;
import models.Swap;
import views.MainView;

/**
 * A wrapper around BrokerClient to make communicating with the broker simpler
 * This class only handles outgoing communication
 * When this class is initialized, it sets up a handler to handle incoming communication
 *
 * @author Nathan
 * @version 11/30/2014
 */
public class BrokerConnection {
/**
 * Objects associated with brokerConnection
 */
    BrokerClient broker;
    ReaderThread brokerCommunicationThread;
    HandleBrokerMessageImplementation handler;

    /**
     * Constructor
     * @param broker
     * @param mainView
     * @param gameState
     */
    public BrokerConnection(BrokerClient broker, MainView mainView, GameState gameState) {
        this.broker = broker;
        // Set up the thread to handle incoming communication
        handler = new HandleBrokerMessageImplementation(mainView, gameState);
        brokerCommunicationThread = new ReaderThread(broker, handler);
        brokerCommunicationThread.start();
    }
/**
 * Send a swap request to broker
 * @param swap
 */
    public void sendSwapRequest(Swap swap) {
        String requestToSend = IProtocol.requestSwapMsg + IProtocol.separator +
                broker.getID() + IProtocol.separator + "*" + IProtocol.separator +
                swap.toString();

        System.out.println("Sending: " + requestToSend);
        brokerCommunicationThread.sendMessage(requestToSend);
    }
/**
 * Get the unique ID from broker to identify the players
 * @return
 */
    public String getSessionID() {
        return broker.getID();
    }

}
