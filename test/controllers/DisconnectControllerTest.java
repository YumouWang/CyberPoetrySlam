package controllers;

import models.GameState;
import models.Position;
import models.Word;
import models.WordType;
import org.junit.Test;
import views.MainView;
import views.WordView;

import static org.junit.Assert.*;

public class DisconnectControllerTest {

    @Test
    public void testDisconnect() throws Exception {
        GameState gameState = new GameState();
        MainView mainView = new MainView(gameState);

        Word wordOne = new Word("MyWord", WordType.ANY);
        Word wordTwo = new Word("MyOtherWord", WordType.ADJECTIVE);
        WordView viewOne = new WordView(wordOne, new Position(0, 0));
        WordView viewTwo = new WordView(wordTwo, new Position(0, 0));
        mainView.addProtectedAbstractWordView(viewOne);
        mainView.addProtectedAbstractWordView(viewTwo);

        DisconnectController disconnectController = new DisconnectController(mainView, gameState);
        assertFalse(disconnectController.disconnect(viewOne, viewTwo));
    }
}