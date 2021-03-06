package controllers;

import models.GameState;
import views.MainView;

/**
 * This is RedoController responsible for redo request
 *
 * @author xujian
 */
public class RedoController {
	/**
	 * The MainView of the game
	 */
    private MainView mainView;
    /**
     * The GameState of the game
     */
    private GameState gameState;

    /**
     * Constructor of RedoController
     *
     * @param mainView
     */
    public RedoController(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.gameState = gameState;
    }

    /**
     * This is process of undoController
     */
    public void process() {
        UndoWithMemento m = this.mainView.removeLastRedoMove();
        if (m != null) {
            UndoWithMemento undo = new UndoWithMemento(this.mainView);
            m.loadState(mainView, gameState);
            mainView.getExploreArea().updateTable();
            mainView.recordUndoMove(undo);
            this.mainView.refresh();
        }
        if (this.mainView.getRedoMoves().isEmpty()) {
            this.mainView.getRedoButton().setEnabled(false);
        }
    }
}
