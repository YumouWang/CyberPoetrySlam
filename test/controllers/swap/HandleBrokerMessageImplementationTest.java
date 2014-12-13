package controllers.swap;

import models.GameState;
import models.Swap;
import models.Word;
import models.WordType;
import org.junit.Before;
import org.junit.Test;
import views.MainView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HandleBrokerMessageImplementationTest {

    GameState gameState;
    MainView mainView;
    HandleBrokerMessageImplementation handler;
    MockBrokerClient brokerClient;
    Swap swap;

    @Before
    public void setUp() throws Exception, InvalidSwapException {
        gameState = new GameState(null, null);
        gameState.getUnprotectedArea().getAbstractWordCollection().clear();
        gameState.getUnprotectedArea().addAbstractWord(new Word("Moonlight", WordType.NOUN));List<String> inputOfferTypes = new ArrayList<String>();
        inputOfferTypes.add("ANY");
        List<String> inputRequestTypes = new ArrayList<String>();
        inputRequestTypes.add("ANY");
        List<String> inputOfferWords = new ArrayList<String>();
        inputOfferWords.add("Moonlight");
        List<String> inputRequestWords = new ArrayList<String>();
        inputRequestWords.add("");

        swap = new Swap(gameState, inputOfferTypes, inputOfferWords, inputRequestTypes, inputRequestWords, true, "1");
        gameState.getPendingSwaps().add(swap);
        mainView = new MainView(gameState, null, null);
        handler = new HandleBrokerMessageImplementation(mainView, gameState);
        brokerClient = new MockBrokerClient();
    }

    @Test
    public void testProcessDenySwap() throws Exception {
        handler.process(brokerClient, "DENY_SWAP:1");
//        assertTrue(gameState.getPendingSwaps().isEmpty());
    }

    @Test
    public void testBrokerGone() throws Exception {
        handler.brokerGone();
    }
}