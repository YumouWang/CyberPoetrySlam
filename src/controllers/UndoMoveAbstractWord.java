package controllers;


import models.GameState;
import models.Position;
import views.AbstractWordView;
import views.MainView;

/**
 * This is Real implementation of Move class
 * This one is only for moving absractword around
 * @author xujian Created on 11/05/2014
 *
 */
public class UndoMoveAbstractWord extends UndoMove{
	
	final AbstractWordView wordview;
	final MainView mainView;
	final GameState gameState;
	final int newx;
	final int newy;
	final int oldx;
	final int oldy;
	
	public UndoMoveAbstractWord(AbstractWordView wordview, int oldx, int oldy, int newx, int newy, MainView mainView, GameState gameState) {
		this.wordview = wordview;
		this.oldx = oldx;
		this.oldy = oldy;
		this.newx = newx;
		this.newy = newy;
		this.mainView = mainView;
		this.gameState = gameState;
	}
	
	@Override
	public boolean execute() {
		//System.out.println("Debugging with Redo " + oldx + "   " + oldy);
		//System.out.println("Debugging with Redo " + newx + "   " + newy);
		//System.out.println(wordview.getPosition().getX() + "  "+ wordview.getPosition().getY());
		MoveWordController moveController = new MoveWordController(this.mainView, this.gameState);
        moveController.moveWord(wordview, new Position(oldx,oldy),new Position(newx,newy));
        //System.out.println(wordview.getPosition().getX() + "  "+ wordview.getPosition().getY());
	    return true;
	}

	@Override
	public boolean undo() {	
		//System.out.println("Debugging with Undo " + oldx + "   " + oldy);
		//System.out.println("Debugging with Undo " + newx + "   " + newy);
		//System.out.println(wordview.getPosition().getX() + "  "+ wordview.getPosition().getY());
		MoveWordController moveWordController = new MoveWordController(this.mainView, this.gameState);
		moveWordController.moveWord(wordview, new Position(newx, newy), new Position(oldx, oldy));
		//System.out.println(wordview.getPosition().getX() + "  "+ wordview.getPosition().getY());
		return true;
	}
}
