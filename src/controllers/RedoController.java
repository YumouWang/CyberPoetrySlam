package controllers;

import views.MainView;

/**
 * Created by Jian
 * This is RedoController responsible for redo request
 * @author xujian
 *
 */
public class RedoController {
	private MainView mainView;
	
	public RedoController(MainView mainView){
		this.mainView = mainView;
	}
	
	public void process() {
		UndoMove m = this.mainView.removeLastRedoMove();
		if (m != null) {
			m.execute();	
			mainView.recordUndoMove(m);
			this.mainView.refresh();
		}
	}
}
