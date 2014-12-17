package controllers;

import models.*;
import org.junit.Test;
import views.MainView;
import views.WordView;

import static org.junit.Assert.assertFalse;

public class DisconnectControllerTest {
    UnprotectedMemento un = null;
    ProtectedMemento p = null;

    @Test
    public void testDisconnect() throws Exception {
        GameState gameState = new GameState(null);
        MainView mainView = new MainView(gameState, null);

        Word wordOne = new Word("MyWord", WordType.ANY);
        Word wordTwo = new Word("MyOtherWord", WordType.ADJECTIVE);
        WordView viewOne = new WordView(wordOne, new Position(0, 0));
        WordView viewTwo = new WordView(wordTwo, new Position(0, 0));
        mainView.addProtectedAbstractWordView(viewOne);
        mainView.addProtectedAbstractWordView(viewTwo);

        DisconnectController disconnectController = new DisconnectController(
                mainView, gameState);
        assertFalse(disconnectController.disconnect(viewOne, viewTwo));
    }
}