package main;

import controllers.MouseController;
import models.GameState;
import views.MainView;

public class MainLauncher {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GameState gameState = new GameState();
		MainView view = new MainView(gameState);
		view.addMouseInputController(new MouseController(view, gameState));
		view.setVisible(true);
	}

}
