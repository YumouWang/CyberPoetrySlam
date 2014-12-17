package main;

import controllers.LoadStateController;
import controllers.UndoWithMemento;
import controllers.swap.BrokerConnectionController;
import controllers.swap.ConnectionException;
import controllers.swap.SwapController;
import models.GameState;
import views.MainView;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The main launcher for starting the program
 *
 * @author Yumou
 * @author Jian
 * @author Nathan
 * @version 10/3/2014
 */
public class MainLauncher {

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        LoadStateController loadStateController = new LoadStateController();

        UndoWithMemento memento = loadStateController.loadMemento();

        // Initialize the GameState object
        final GameState gameState = new GameState(memento);
        // Initialize the MainView pointing at the GameState
        final MainView mainView = new MainView(gameState, memento);
        // Add a controller to handle user input
        // mainView.addMouseInputController(new MouseInputController(mainView,
        // gameState));
        mainView.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                SwapController swapController = new SwapController(mainView, gameState);
                swapController.cancelPendingSwaps();
                LoadStateController loadStateController = new LoadStateController();
                loadStateController.storeState(mainView, gameState);
                System.exit(0);
            }
        });
        // Display the view
        mainView.setVisible(true);

        // Attempt to connect to the broker immediately on startup
        // If we can't then disable the swap area
        try {
            BrokerConnectionController.getConnection(mainView, gameState);
            mainView.getSwapAreaView().enable();
        } catch (ConnectionException e) {
            mainView.getSwapAreaView().disable();
        }
    }
}
