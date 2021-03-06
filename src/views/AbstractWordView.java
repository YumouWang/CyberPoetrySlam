package views;

import common.Constants;
import controllers.AbstractWordViewVisitor;
import models.AbstractWord;
import models.Position;

import java.awt.*;
import java.io.Serializable;

/**
 * The view class for each word in CyberPoetrySlam
 *
 * @author Nathan
 * @version 10/3/2014
 */
public abstract class AbstractWordView extends AbstractView implements
        Serializable, Cloneable {

    /**
     *Serialized ID for an AbstractWordView
     */

    private static final long serialVersionUID = -8621254877402775008L;
    /**
     * Attributes of an AbstractWordView
     * furthestRight records the right boundary of a AbstractWordView
     * furthestLeft  records the left boundary of a AbstractWordView
     */
    AbstractWord word;
    int furthestRight;
    int furthestLeft;

    /**
     * Constructor
     *
     * @param word     The word that this view represents
     * @param position The position of this word
     */
    public AbstractWordView(AbstractWord word, Position position) {
        super(position);
        this.word = word;
        furthestLeft = 0;
        furthestRight = width;
    }

    /**
     * Checks if this view is adjacent to a given other word. Distance that
     * determines whether a word is adjacent is defined in
     * Constants.CONNECT_DISTANCE
     *
     * @param otherWord The word to check adjacency
     * @return Returns the type of adjacency (NOT_ADJACENT, ABOVE, BELOW, LEFT,
     * or RIGHT)
     */
    public AdjacencyType isAdjacentTo(AbstractWordView otherWord) {
        Position aboveBoxPosition = new Position(position.getX(),
                position.getY() - Constants.CONNECT_DISTANCE);
        ConnectionBox above = new ConnectionBox(aboveBoxPosition, width,
                Constants.CONNECT_DISTANCE);
        Position belowBoxPosition = new Position(position.getX(),
                position.getY() + height);
        ConnectionBox below = new ConnectionBox(belowBoxPosition, width,
                Constants.CONNECT_DISTANCE);
        Position leftBoxPosition = new Position(position.getX()
                - Constants.CONNECT_DISTANCE, position.getY());
        ConnectionBox left = new ConnectionBox(leftBoxPosition,
                Constants.CONNECT_DISTANCE, height);
        Position rightBoxPosition = new Position(position.getX() + width,
                position.getY());
        ConnectionBox right = new ConnectionBox(rightBoxPosition,
                Constants.CONNECT_DISTANCE, height);

        AdjacencyType returnType = AdjacencyType.NOT_ADJACENT;
        if (left.isOverlapping(otherWord)) {
            returnType = AdjacencyType.RIGHT;
        } else if (right.isOverlapping(otherWord)) {
            returnType = AdjacencyType.LEFT;
        } else if (above.isOverlapping(otherWord)) {
            returnType = AdjacencyType.BELOW;
        } else if (below.isOverlapping(otherWord)) {
            returnType = AdjacencyType.ABOVE;
        }
        return returnType;
    }

    /**
     * Sets the size of this word view. Used for testing, should not be
     * necessary for the game
     *
     * @param width  The desired width
     * @param height The desired height
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the word this view represents
     *
     * @return Returns the word this view represents
     */
    public AbstractWord getWord() {
        return word;
    }

    /**
     * Moves the word to the specified position
     *
     * @param toPosition The position to move to
     * @return Returns whether the move was successful
     */
    public abstract boolean moveTo(Position toPosition);
/**
 * Get the elements from connection box created by mouse
 * @param box
 * @return AbstractWordView
 */
    public abstract AbstractWordView getSelectedElement(ConnectionBox box);
/**
 * Check whether one AbstractWordView contains another AbstractWordView
 * @param otherWord
 * @return boolean
 */
    public abstract boolean contains(AbstractWordView otherWord);

    /**
     * Sets the background color of this word view
     *
     * @param color The color to set the background to
     */
    public abstract void setBackground(Color color);
/**
 * Connect or disconnect an AbstractWordView to another AbstractWordView
 * @param visitor
 * @param otherView
 * @return boolean
 */
    public abstract boolean acceptVisitor(AbstractWordViewVisitor visitor,
                                          AbstractWordView otherView);
/**
 * Connect or disconnect an WordView to another AbstractWordView
 * @param visitor
 * @param wordView
 * @return boolean
 */
    public abstract boolean acceptVisitor(AbstractWordViewVisitor visitor,
                                          WordView wordView);
/**
 * Connect or disconnect an RowView to another AbstractWordView
 * @param visitor
 * @param rowView
 * @return boolean
 */
    public abstract boolean acceptVisitor(AbstractWordViewVisitor visitor,
                                          RowView rowView);
/**
 * Connect or disconnect an PoemView to another AbstractWordView
 * @param visitor
 * @param poemView
 * @return boolean
 */
    public abstract boolean acceptVisitor(AbstractWordViewVisitor visitor,
                                          PoemView poemView);

    @Override
    public Object clone() {
        AbstractWordView abstractWordView = null;
        try {
            abstractWordView = (AbstractWordView) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return abstractWordView;
    }

    /**
     * Get the right boundary of the AbstractWordView
     *
     * @return int
     */
    public int getFurthestRight() {
        return furthestRight;
    }

    /**
     * Get the left boundary of the AbstractWordView
     *
     * @return int
     */
    public int getFurthestLeft() {
        return furthestLeft;
    }
}
