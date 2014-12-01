package controllers.swap;

import broker.BrokerClient;
import broker.handler.IHandleBrokerMessage;
import models.GameState;
import views.MainView;

/**
 * Class for handling receiving messages from the broker
 * @author Nathan
 * @version 11/30/2014
 */
public class HandleBrokerMessageImplementation implements IHandleBrokerMessage {

    private MainView mainView;
    private GameState gameState;

    public HandleBrokerMessageImplementation(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.gameState = gameState;
    }

    @Override
    public void process(BrokerClient brokerClient, String s) {
        //TODO Handle requests
        System.out.println(s);
    }

    @Override
    public void brokerGone() {
        BrokerConnectionController.resetConnection();
        mainView.getSwapAreaView().disable();
    }
}
