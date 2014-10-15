package controllers;

import models.*;
import views.*;

import java.awt.*;

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
        boolean successful = fromView.contains(wordView);
        if(successful) {
            AbstractWordView newWordView;

            AbstractWord word = wordView.getWord();
            AbstractWord from = fromView.getWord();
            AbstractWord result = gameState.getProtectedArea().disconnect(word, from);
            successful = (result != null);

            if(from.equals(result)) {
                if(from instanceof Row) {
                    RowView rowView = (RowView) fromView;
                    rowView.removeWordView((WordView)wordView);
                } else {
                    PoemView poemView = (PoemView) fromView;
                    poemView.removeEdgeWordView((WordView)wordView);
                }
                newWordView = fromView;
            } else {
                if(result instanceof Word) {
                    newWordView = new WordView((Word) result, fromView.getPosition());
                } else if(result instanceof Row) {
                    newWordView = new RowView((Row) result, fromView.getPosition(), mainView);
                } else {
                    newWordView = new PoemView((Poem) result, fromView.getPosition(), mainView);
                }
            }

            if (successful) {
                newWordView.setBackground(Color.LIGHT_GRAY);
                mainView.removeAbstractWordView(fromView);
                mainView.addAbstractWordView(wordView);
                mainView.addAbstractWordView(newWordView);
            }
        } else {
            System.out.println("Word is not in poem");
        }
        return successful;
    }
}
