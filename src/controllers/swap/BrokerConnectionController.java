package controllers.swap;

import broker.BrokerClient;
import common.Constants;
import models.GameState;
import views.MainView;

/**
 * A class that handles setting up the connection with the broker
 * @author Nathan
 * @version 11/30/2014
 */
public class BrokerConnectionController {

    /**
     * The singleton connection to the broker
     */
    static BrokerConnection connection;

    /**
     * Implementation of the singleton pattern for maintaining a single connection with the broker
     * @param mainView the mainView associated with this connection
     * @param gameState the gameState associated with this connection
     * @return Returns the current broker connection
     * @throws ConnectionException Throws an exception if there is an error connecting to the broker
     */
    public static BrokerConnection getConnection(MainView mainView, GameState gameState) throws ConnectionException {
        // If we already have a connection object, return it
        if(connection != null) {
            return connection;
        }
        // We don't have a valid connection object, so try to connect to the broker
        BrokerClient broker = new BrokerClient(Constants.BROKER_HOSTNAME, Constants.BROKER_PORT);
        // If the connection failed, shut down and throw an exception
        if (!broker.connect()) {
            broker.shutdown();
            throw new ConnectionException("Unable to connect to broker");
        }
        // Otherwise, wrap the client object in a simpler connection object and return it
        connection = new BrokerConnection(broker, mainView, gameState);
        return connection;
    }

    /**
     * Resets the connection. Used when brokerGone is called
     */
    public static void resetConnection() {
        connection = null;
    }
}
