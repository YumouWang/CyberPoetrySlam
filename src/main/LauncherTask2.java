package main;

import controllers.MouseInputController;
import models.GameState;
import views.MainView;

/**
 * The launcher for creating the UI in task 2
 *
 * Created by Nathan on 10/3/2014.
 */
public class LauncherTask2 {

    public static void main(String[] args) {
        // Initialize the GameState object
        GameState gameState = new GameState();
        // Initialize the MainView pointing at the GameState
        MainView mainView = new MainView(gameState);
        // Add a controller to handle user input
        mainView.addMouseInputController(new MouseInputController(mainView, gameState));
        // Display the views
        mainView.setVisible(true);
    }
}
