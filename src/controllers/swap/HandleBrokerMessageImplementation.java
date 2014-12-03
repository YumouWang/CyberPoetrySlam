package controllers.swap;

import broker.BrokerClient;
import broker.handler.IHandleBrokerMessage;
import broker.util.IProtocol;
import models.GameState;
import models.Swap;
import views.MainView;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Class for handling receiving messages from the broker
 * @author Nathan
 * @version 11/30/2014
 */
public class HandleBrokerMessageImplementation implements IHandleBrokerMessage {

    private MainView mainView;
    private GameState gameState;

    public HandleBrokerMessageImplementation(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.gameState = gameState;
    }

    @Override
    public void process(BrokerClient brokerClient, String msg) {
        System.out.println("Received: " + msg);

        // We assume that all messages are well formed. Receiving a poorly formed message would cause unexpected behavior
        StringTokenizer st = new StringTokenizer(msg, ":");
        String msgType = st.nextToken();
        String requestorID = st.nextToken();
        String acceptorID = st.nextToken();
        if(msgType.equals(IProtocol.denySwapMsg)) {
            mainView.getSwapAreaView().swapFailed();
        } else if(msgType.equals(IProtocol.matchSwapMsg)) {
            try {
                // Try to create the swap, if it's successful then it means we can fulfill the swap
                Swap swap = Swap.getSwap(gameState, msg, false);
                String responseString = IProtocol.confirmSwapMsg + IProtocol.separator;
                responseString += requestorID + IProtocol.separator;
                responseString += acceptorID + IProtocol.separator;
                // The swap we can fulfill
                responseString += swap.toString();
                brokerClient.getBrokerOutput().println(responseString);
            } catch (InvalidSwapException e) {
                // Swap request cannot be fulfilled, respond with denySwap
                String responseString = IProtocol.denySwapMsg + IProtocol.separator;
                // The id of the user requesting the swap
                responseString += requestorID + IProtocol.separator;
                brokerClient.getBrokerOutput().println(responseString);
            }
        } else if(msgType.equals(IProtocol.confirmSwapMsg)) {
            try {
                // Try to fulfill the swap, it should be successful
                Swap swap = Swap.getSwap(gameState, msg, requestorID.equals(brokerClient.getID()));
                SwapController swapController = new SwapController(mainView, gameState);
                swapController.executeSwap(swap);
                mainView.getSwapAreaView().swapSuccessful();
            } catch (InvalidSwapException e) {
                // Swap request cannot be fulfilled. This is bad,
                // we said we could do a swap but now we can't
                e.printStackTrace();
            }
        } else {
            try {
                throw new ConnectionException("Bad message from broker");
            } catch (ConnectionException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void brokerGone() {
        BrokerConnectionController.resetConnection();
        mainView.getSwapAreaView().disable();
    }

}
