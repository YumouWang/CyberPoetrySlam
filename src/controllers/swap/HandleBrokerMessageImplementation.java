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
    private boolean cancelSwap;

    public HandleBrokerMessageImplementation(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.gameState = gameState;
        cancelSwap = false;
    }

    @Override
    public void process(BrokerClient brokerClient, String s) {
        //TODO Handle requests
        System.out.println(s);
        mainView.getSwapAreaView().swapSuccessful();
    }

    @Override
    public void brokerGone() {
        BrokerConnectionController.resetConnection();
        mainView.getSwapAreaView().disable();
    }

}
