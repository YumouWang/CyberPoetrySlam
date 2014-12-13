package controllers.swap;

import models.GameState;
import org.junit.Before;
import org.junit.Test;
import views.MainView;

import static org.junit.Assert.*;

public class BrokerConnectionControllerTest {

    @Test
    public void testGetConnection() throws Exception {
        GameState gameState = new GameState(null, null);
        MainView mainView = new MainView(gameState, null, null);
        try {
            BrokerConnection brokerConnection = BrokerConnectionController.getConnection(mainView, gameState);
            assertNotNull(BrokerConnectionController.connection);
            try {
                brokerConnection = BrokerConnectionController.getConnection(mainView, gameState);
                assertNotNull(BrokerConnectionController.connection);
                assertNotNull(brokerConnection);
            } catch (ConnectionException e) {
                assertNull(BrokerConnectionController.connection);
            }
        } catch (ConnectionException e) {
            assertNull(BrokerConnectionController.connection);
        }
    }

    @Test
    public void testResetConnection() throws Exception {
        GameState gameState = new GameState(null, null);
        MainView mainView = new MainView(gameState, null, null);
        try {
            BrokerConnection brokerConnection = BrokerConnectionController.getConnection(mainView, gameState);
            assertNotNull(BrokerConnectionController.connection);
        } catch (ConnectionException e) {
            assertNull(BrokerConnectionController.connection);
        }
        BrokerConnectionController.resetConnection();
        assertNull(BrokerConnectionController.connection);
    }
}