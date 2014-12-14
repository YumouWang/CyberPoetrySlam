package controllers;

import models.GameState;
import views.MainView;

/**
 * This is Controller for undo
 * @author xujian Created 11/05/2014
 */
public class UndoController {
	
	private MainView mainView;
	private GameState gameState;
	
	/**
	 * Constructor of UndoController
	 * @param mainView
	 */
	public UndoController(MainView mainView, GameState gameState){
		this.mainView = mainView;
		this.gameState = gameState;
	}
	
	/**
	 * process of undoController
	 */
	public void process() {
		UndoMove m = this.mainView.removeLastUndoMove();
		if (m != null) {
    		UndoWithMemento undo = new UndoWithMemento(this.mainView, this.gameState);
			m.undo();	
			mainView.recordRedoMove(undo);
			this.mainView.refresh();
		}
		if (this.mainView.getUndoMoves().isEmpty()){
			this.mainView.getUndoButton().setEnabled(false);
		}
		
	}
}
