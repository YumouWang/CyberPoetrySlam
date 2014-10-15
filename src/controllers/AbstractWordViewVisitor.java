package controllers;

import views.PoemView;
import views.RowView;
import views.WordView;

/**
 * An implementation of the visitor pattern for connecting views
 *
 * Created by Nathan on 10/15/2014.
 */
public interface AbstractWordViewVisitor {
    public void visit(WordView wordViewOne, WordView wordViewTwo);
    public void visit(WordView wordViewOne, RowView rowViewTwo);
    public void visit(WordView wordViewOne, PoemView poemViewTwo);
    public void visit(RowView rowViewOne, WordView wordViewTwo);
    public void visit(RowView rowViewOne, RowView rowViewTwo);
    public void visit(RowView rowViewOne, PoemView poemViewTwo);
    public void visit(PoemView poemViewOne, WordView wordViewTwo);
    public void visit(PoemView poemViewOne, RowView rowViewTwo);
    public void visit(PoemView poemViewOne, PoemView poemViewTwo);
}
