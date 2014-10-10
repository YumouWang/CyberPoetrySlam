package main;

import controllers.MouseController;
import views.MainGUI;
import models.GameState;

public class MainGUILauncher {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GameState gameState = new GameState();
		MainGUI view = new MainGUI(gameState);
		view.addMouseInputController(new MouseController(view, gameState));
		view.setVisible(true);
	}

}
