package main;

import controllers.MouseInputController;
import models.GameState;
import views.MainView;

/**
 * The main launcher for starting the program
 *
 * Created by Yumou on 10/3/2014.
 */
public class MainLauncher {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
        // Initialize the GameState object
        GameState gameState = new GameState();
        // Initialize the MainView pointing at the GameState
        MainView mainView = new MainView(gameState);
        // Add a controller to handle user input
        mainView.addMouseInputController(new MouseInputController(mainView, gameState));
        // Display the view
        mainView.setVisible(true);
	}
}
