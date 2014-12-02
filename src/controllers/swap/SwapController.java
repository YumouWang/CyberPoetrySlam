package controllers.swap;

import models.GameState;
import views.MainView;

import java.util.ArrayList;

/**
 * Interface for the rest of the program to communicate with the broker
 * @author Nathan
 * @version 11/30/2014
 */
public class SwapController {

    MainView mainView;
    GameState gameState;
    BrokerConnection connection;

    /**
     * Constructs a new swap controller
     * @param mainView The current MainView
     * @param gameState The current GameState
     */
    public SwapController(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.gameState = gameState;
        // Get the broker connection
        try {
            this.connection = BrokerConnectionController.getConnection(mainView, gameState);
        } catch (ConnectionException e) {
            mainView.getSwapAreaView().disable();
        }
    }

    /**
     * Creates a swap request using the information from the MainView and delegates
     * to the BrokerConnection to send it
     */
    public void swap() {
        if(connection == null) { return; }
        mainView.getSwapAreaView().swapPending();
        ArrayList<String> giveTypes = new ArrayList<String>();
        ArrayList<String> giveWords = new ArrayList<String>();
        ArrayList<String> getTypes = new ArrayList<String>();
        ArrayList<String> getWords = new ArrayList<String>();

        giveTypes.add("noun");
        giveWords.add("word");
        getTypes.add("noun");
        getWords.add("myword");

        try {
            connection.sendSwapRequest(giveTypes, giveWords, getTypes, getWords);
        } catch (SwapException e) {
            // If we get here it means we messed up bad
            e.printStackTrace();
        }
    }
}
