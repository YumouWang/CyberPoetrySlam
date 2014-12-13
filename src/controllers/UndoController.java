package controllers;

import views.MainView;

/**
 * Created by Jian
 * This is Controller for undo
 * @author xujian Created 11/05/2014
 *
 */
public class UndoController {
	
	private MainView mainView;
	
	public UndoController(MainView mainView){
		this.mainView = mainView;
	}
	
	public void process() {
		UndoMove m = this.mainView.removeLastUndoMove();
		if (m != null) {
			m.undo();	
			mainView.recordRedoMove(m);
			this.mainView.refresh();
		}
		if (this.mainView.getUndoMoves().isEmpty()){
			this.mainView.getUndoButton().setEnabled(false);
		}
		
	}
}
