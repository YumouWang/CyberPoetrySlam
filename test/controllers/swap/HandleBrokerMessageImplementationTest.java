package controllers.swap;

import models.*;
import org.junit.Before;
import org.junit.Test;
import views.MainView;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class HandleBrokerMessageImplementationTest {

    GameState gameState;
    MainView mainView;
    HandleBrokerMessageImplementation handler;
    MockBrokerClient brokerClient;
    Swap swap;
    Word word;

    @Before
    public void setUp() throws Exception, InvalidSwapException {
        gameState = new GameState(null, null);
        gameState.getUnprotectedArea().getAbstractWordCollection().clear();
        word = new Word("Moonlight", WordType.NOUN);
        gameState.getUnprotectedArea().addAbstractWord(word);List<String> inputOfferTypes = new ArrayList<String>();
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
        assertTrue(gameState.getPendingSwaps().isEmpty());
    }

    @Test(expected = NoSuchElementException.class)
    public void testBadMessage() throws Exception {
        handler.process(brokerClient, "BadMessage");
    }

    @Test
    public void testBadMessage2() throws Exception {
        handler.process(brokerClient, "Bad:Message");
        assertTrue(true);
    }

    @Test
    public void testProcessConfirmSwap() throws Exception {
        handler.process(brokerClient, "CONFIRM_SWAP:1:3:1:noun:moonlight:noun:yak");
        assertTrue(gameState.getPendingSwaps().isEmpty());
        assertFalse(gameState.getUnprotectedArea().getAbstractWordCollection().contains(word));
        assertEquals(1, gameState.getUnprotectedArea().getAbstractWordCollection().size());
        assertEquals("yak", ((AbstractWord)gameState.getUnprotectedArea().getAbstractWordCollection().toArray()[0]).getValue());
    }

    @Test
    public void testBrokerGone() throws Exception {
        handler.brokerGone();
    }
}