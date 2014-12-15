package controllers.swap;

import broker.ipc.Broker;
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

public class BrokerConnectionTest {

    BrokerConnection brokerConnection;
    String id;
    GameState gameState;
    MainView mainView;
    MockReaderThread reader;

    @Before
     public void setUp() throws Exception {
        gameState = new GameState(null);
        gameState.getUnprotectedArea().getAbstractWordCollection().clear();
        gameState.getUnprotectedArea().addAbstractWord(new Word("Moonlight", WordType.NOUN));
        mainView = new MainView(gameState, null);
        try {
            brokerConnection = BrokerConnectionController.getConnection(mainView, gameState);
            assertNotNull(BrokerConnectionController.connection);
            reader = new MockReaderThread(brokerConnection.broker, brokerConnection.handler);
            brokerConnection.brokerCommunicationThread = reader;
            id = brokerConnection.broker.getID();
        } catch (ConnectionException e) {
            assertNull(BrokerConnectionController.connection);
        }
    }

    @Test
    public void testSendSwapRequest() throws Exception, InvalidSwapException {
        if(brokerConnection != null) {
            List<String> inputOfferTypes = new ArrayList<String>();
            inputOfferTypes.add("ANY");
            List<String> inputRequestTypes = new ArrayList<String>();
            inputRequestTypes.add("ANY");
            List<String> inputOfferWords = new ArrayList<String>();
            inputOfferWords.add("Moonlight");
            List<String> inputRequestWords = new ArrayList<String>();
            inputRequestWords.add("");

            Swap s = new Swap(gameState, inputOfferTypes, inputOfferWords, inputRequestTypes, inputRequestWords, true, "1");
            brokerConnection.sendSwapRequest(s);
            assertEquals("REQUEST_SWAP:" + id + ":*:1:noun:Moonlight:*:*", reader.msg);
        }
    }

    @Test
    public void testGetSessionID() throws Exception {
        if(brokerConnection != null) {
            assertEquals(id, brokerConnection.getSessionID());
        }
    }
}