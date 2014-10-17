package controllers;

import models.GameState;
import views.AbstractWordView;
import views.MainView;

/**
 * A controller for handling disconnecting words
 *
 * Created by Nathan on 10/12/2014.
 */
public class DisconnectController {

    MainView mainView;
    GameState gameState;

    /**
     * Constructor
     * @param mainView The MainView to update once a connection has been made
     * @param gameState The GameState to perform connections on
     */
    public DisconnectController(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.gameState = gameState;
    }

    /**
     * Disconnects two words. Returns whether the disconnect was successful
     * @param wordView The word to disconnect
     * @param fromView The AbstractWordView to disconnect from
     * @return Returns whether the disconnect was successful or not
     */
    public boolean disconnect(AbstractWordView wordView, AbstractWordView fromView) {
        DisconnectVisitor disconnector = new DisconnectVisitor(mainView, gameState);
        return fromView.acceptVisitor(disconnector, wordView);
    }
}
