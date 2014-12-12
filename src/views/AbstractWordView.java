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
 * Created by Nathan on 10/3/2014.
 */
public abstract class AbstractWordView extends AbstractView implements
		Serializable,Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8621254877402775008L;
	AbstractWord word;

	/**
	 * Constructor
	 * 
	 * @param word
	 *            The word that this view represents
	 * @param position
	 *            The position of this word
	 */
	public AbstractWordView(AbstractWord word, Position position) {
		super(position);
		this.word = word;
	}

	/**
	 * Checks if this view is adjacent to a given other word. Distance that
	 * determines whether a word is adjacent is defined in
	 * Constants.CONNECT_DISTANCE
	 * 
	 * @param otherWord
	 *            The word to check adjacency
	 * @return Returns the type of adjacency (NOT_ADJACENT, ABOVE, BELOW, LEFT,
	 *         or RIGHT)
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
	 * @param width
	 *            The desired width
	 * @param height
	 *            The desired height
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
	 * @param toPosition
	 *            The position to move to
	 * @return Returns whether the move was successful
	 */
	public abstract boolean moveTo(Position toPosition);

	public abstract AbstractWordView getSelectedElement(ConnectionBox box);

	public abstract boolean contains(AbstractWordView otherWord);

	/**
	 * Sets the background color of this word view
	 * 
	 * @param color
	 *            The color to set the background to
	 */
	public abstract void setBackground(Color color);

	public abstract boolean acceptVisitor(AbstractWordViewVisitor visitor,
			AbstractWordView otherView);

	public abstract boolean acceptVisitor(AbstractWordViewVisitor visitor,
			WordView wordView);

	public abstract boolean acceptVisitor(AbstractWordViewVisitor visitor,
			RowView rowView);

	public abstract boolean acceptVisitor(AbstractWordViewVisitor visitor,
			PoemView poemView);
	
	@Override
	public Object clone() {
		AbstractWordView abstractWordView = null;
		try{
			abstractWordView = (AbstractWordView) super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return abstractWordView;
	}
}
