package controllers;

/**
 * This is Abstract Class for all the operation we could undo/redo
 * @author xujian Created on 11/05/2014
 *
 */
public abstract class UndoMove{

	/** Execute given move. For redo purpose */
	public abstract boolean execute();

	/** Request undo. For undo purpose */
	public abstract boolean undo();

}
