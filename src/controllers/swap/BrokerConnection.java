package controllers.swap;

import broker.BrokerClient;
import broker.handler.ReaderThread;
import broker.util.IProtocol;
import models.GameState;
import views.MainView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A wrapper around BrokerClient to make communicating with the broker simpler
 * This class only handles outgoing communication
 * When this class is initialized, it sets up a handler to handle incoming communication
 * @author Nathan
 * @version 11/30/2014
 */
public class BrokerConnection {

    private BrokerClient broker;
    private ReaderThread brokerCommunicationThread;
    private HandleBrokerMessageImplementation handler;

    public BrokerConnection(BrokerClient broker, MainView mainView, GameState gameState) {
        this.broker = broker;
        // Set up the thread to handle incoming communication
        handler = new HandleBrokerMessageImplementation(mainView, gameState);
        brokerCommunicationThread = new ReaderThread(broker, handler);
        brokerCommunicationThread.start();
    }

    public void sendSwapRequest(ArrayList<String> giveTypes, ArrayList<String> giveWords,
                                ArrayList<String> getTypes, ArrayList<String> getWords) throws SwapException {
        int size = giveTypes.size();
        if(size != giveWords.size() || size != getTypes.size() || size != getWords.size()) {
            throw new SwapException("Invalid swap input");
        }
        String swapString = "" + size;
        for(String s : giveTypes) {
            swapString += IProtocol.separator + s;
        }
        for(String s : giveWords) {
            swapString += IProtocol.separator + s;
        }
        for(String s : getTypes) {
            swapString += IProtocol.separator + s;
        }
        for(String s : getWords) {
            swapString += IProtocol.separator + s;
        }

        System.out.println(constructMessage(swapString));
        brokerCommunicationThread.sendMessage(constructMessage(swapString));
    }

    private String constructMessage(String message) {
        return IProtocol.requestSwapMsg + IProtocol.separator +
                broker.getID() + IProtocol.separator + "*" + IProtocol.separator +
                message;
    }
}
