package controllers;

import views.MainView;

/**
 * This is RedoController responsible for redo request
 * @author xujian
 */
public class RedoController {
	private MainView mainView;
	
	/**
	 * Constructor of RedoController
	 * @param mainView
	 */
	public RedoController(MainView mainView){
		this.mainView = mainView;
	}
	
	/**
	 * This is process of undoController
	 */
	public void process() {
		UndoMove m = this.mainView.removeLastRedoMove();
		if (m != null) {
			m.execute();	
			mainView.recordUndoMove(m);
			this.mainView.refresh();
		}
		if (this.mainView.getRedoMoves().isEmpty()){
			this.mainView.getRedoButton().setEnabled(false);
		}
	}
}
