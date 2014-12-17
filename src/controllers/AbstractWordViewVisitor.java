package controllers;

import views.PoemView;
import views.RowView;
import views.WordView;

/**
 * An implementation of the visitor pattern for connecting views
 *
 * @author Nathan
 * @version 10/15/2014
 */
public interface AbstractWordViewVisitor {
	/**
     * Handles connecting and disconnecting one WordView to a WordView
     * and return whether it succeeds
     * @param wordViewOne,wordViewTwo
     * @return boolean
     */
    public boolean visit(WordView wordViewOne, WordView wordViewTwo);
    /**
     * Handles connecting and disconnecting one WordView to a RowView
     * and return whether it succeeds
     * @param wordViewOne,rowViewTwo
     * @return boolean
     */
    public boolean visit(WordView wordViewOne, RowView rowViewTwo);
    /**
     * Handles connecting and disconnecting one WordView to a PoemView
     * and return whether it succeeds
     * @param wordViewOne,poemViewTwo
     * @return boolean
     */
    public boolean visit(WordView wordViewOne, PoemView poemViewTwo);
    /**
     * Handles connecting and disconnecting one RowView to a WordView
     * and return whether it succeeds
     * @param rowViewOne,wordViewTwo
     * @return boolean
     */
    public boolean visit(RowView rowViewOne, WordView wordViewTwo);
    /**
     * Handles connecting and disconnecting one RowView to a RowView
     * and return whether it succeeds
     * @param rowViewOne,rowViewTwo
     * @return boolean
     */
    public boolean visit(RowView rowViewOne, RowView rowViewTwo);
    /**
     * Handles connecting and disconnecting one RowView to a PoemView
     * and return whether it succeeds
     * @param rowViewOne,poemViewTwo
     * @return boolean
     */
    public boolean visit(RowView rowViewOne, PoemView poemViewTwo);
    /**
     * Handles connecting and disconnecting one PoemView to a WordView
     * and return whether it succeeds
     * @param poemViewOne,wordViewTwo
     * @return boolean
     */
    public boolean visit(PoemView poemViewOne, WordView wordViewTwo);
    /**
     * Handles connecting and disconnecting one PoemView to a RowView
     * and return whether it succeeds
     * @param poemViewOne,rowViewTwo
     * @return boolean
     */
    public boolean visit(PoemView poemViewOne, RowView rowViewTwo);
    /**
     * Handles connecting and disconnecting one PoemView to a PoemView
     * and return whether it succeeds
     * @param poemViewOne,poemViewTwo
     * @return boolean
     */
    public boolean visit(PoemView poemViewOne, PoemView poemViewTwo);
}
