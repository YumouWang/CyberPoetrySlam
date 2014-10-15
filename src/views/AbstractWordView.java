package views;

import common.Constants;
import controllers.AbstractWordViewVisitor;
import models.AbstractWord;
import models.Position;

import java.awt.*;

/**
 * The view class for each word in CyberPoetrySlam
 *
 * Created by Nathan on 10/3/2014.
 */
public class AbstractWordView extends AbstractView {

    AbstractWord word;

    /**
     * Constructor
     * @param word The word that this view represents
     * @param position The position of this word
     */
    public AbstractWordView(AbstractWord word, Position position) {
        super(position);
        this.word = word;
    }

    /**
     * Moves the word to the specified position
     * @param toPosition The position to move to
     * @return Returns whether the move was successful
     */
    public boolean moveTo(Position toPosition) {
        //TODO Make abstract
        return false;
    }

    public AbstractWordView getSelectedElement(ConnectionBox box) {
        //TODO Make abstract
        return null;
    }

    public boolean contains(AbstractWordView otherWord) {
        //TODO Make abstract
        return false;
    }

    /**
     * Checks if this view is adjacent to a given other word.
     * Distance that determines whether a word is adjacent is defined in Constants.CONNECT_DISTANCE
     * @param otherWord The word to check adjacency
     * @return Returns the type of adjacency (NOT_ADJACENT, ABOVE, BELOW, LEFT, or RIGHT)
     */
    public AdjacencyType isAdjacentTo(AbstractWordView otherWord) {
        Position aboveBoxPosition = new Position(position.getX(), position.getY() - Constants.CONNECT_DISTANCE);
        ConnectionBox above = new ConnectionBox(aboveBoxPosition, width, Constants.CONNECT_DISTANCE);
        Position belowBoxPosition = new Position(position.getX(), position.getY() + height);
        ConnectionBox below = new ConnectionBox(belowBoxPosition, width, Constants.CONNECT_DISTANCE);
        Position leftBoxPosition = new Position(position.getX() - Constants.CONNECT_DISTANCE, position.getY());
        ConnectionBox left = new ConnectionBox(leftBoxPosition, Constants.CONNECT_DISTANCE, height);
        Position rightBoxPosition = new Position(position.getX() + width, position.getY());
        ConnectionBox right = new ConnectionBox(rightBoxPosition, Constants.CONNECT_DISTANCE, height);

        AdjacencyType returnType = AdjacencyType.NOT_ADJACENT;
        if(left.isOverlapping(otherWord)) {
            returnType = AdjacencyType.RIGHT;
        } else if(right.isOverlapping(otherWord)) {
            returnType = AdjacencyType.LEFT;
        } else if(above.isOverlapping(otherWord)) {
            returnType = AdjacencyType.BELOW;
        } else if(below.isOverlapping(otherWord)) {
            returnType = AdjacencyType.ABOVE;
        }
        return returnType;
    }

    /**
     * Sets the background color of this word view
     * @param color The color to set the background to
     */
    public void setBackground(Color color) {
        //TODO make abstract
    }

    /**
     * Gets the word this view represents
     * @return Returns the word this view represents
     */
    public AbstractWord getWord() {
        return word;
    }

    /**
     * Sets the size of this word view. Used for testing, should not be necessary for the game
     * @param width The desired width
     * @param height The desired height
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void acceptVisitor(AbstractWordViewVisitor visitor, AbstractWordView otherView) {
        //TODO make abstract
    }

    public void acceptVisitor(AbstractWordViewVisitor visitor, WordView wordView) {
        //TODO make abstract
    }

    public void acceptVisitor(AbstractWordViewVisitor visitor, RowView rowView) {
        //TODO make abstract
    }

    public void acceptVisitor(AbstractWordViewVisitor visitor, PoemView poemView) {
        //TODO make abstract
    }
}
