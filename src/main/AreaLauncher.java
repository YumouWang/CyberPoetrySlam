package main;

import models.GameState;
import views.AreaView;

public class AreaLauncher {
	public static void main(String[] args) {
		GameState gameState = new GameState();
		AreaView frame = new AreaView(gameState);
		frame.setVisible(true);
	}
}
