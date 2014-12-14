package controllers;

import models.GameState;
import views.MainView;

/**
 * This is RedoController responsible for redo request
 * @author xujian
 */
public class RedoController {
	private MainView mainView;
	private GameState gameState;
	
	/**
	 * Constructor of RedoController
	 * @param mainView
	 */
	public RedoController(MainView mainView, GameState gameState){
		this.mainView = mainView;
		this.gameState = gameState;
	}
	
	/**
	 * This is process of undoController
	 */
	public void process() {
		UndoMove m = this.mainView.removeLastRedoMove();
		if (m != null) {
			UndoWithMemento undo = new UndoWithMemento(this.mainView, this.gameState);
			m.undo();	
			mainView.recordUndoMove(undo);
			this.mainView.refresh();
		}
		if (this.mainView.getRedoMoves().isEmpty()){
			this.mainView.getRedoButton().setEnabled(false);
		}
	}
}
