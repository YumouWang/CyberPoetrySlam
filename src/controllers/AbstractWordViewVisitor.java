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
    public boolean visit(WordView wordViewOne, WordView wordViewTwo);
    public boolean visit(WordView wordViewOne, RowView rowViewTwo);
    public boolean visit(WordView wordViewOne, PoemView poemViewTwo);
    public boolean visit(RowView rowViewOne, WordView wordViewTwo);
    public boolean visit(RowView rowViewOne, RowView rowViewTwo);
    public boolean visit(RowView rowViewOne, PoemView poemViewTwo);
    public boolean visit(PoemView poemViewOne, WordView wordViewTwo);
    public boolean visit(PoemView poemViewOne, RowView rowViewTwo);
    public boolean visit(PoemView poemViewOne, PoemView poemViewTwo);
}
