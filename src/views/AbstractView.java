package views;

import java.io.Serializable;

import models.Position;

import javax.swing.*;

/**
 * The base class for each view component of CyberPoetrySlam
 *
 * @author Nathan
 * @version 10/3/2014
 */
public abstract class AbstractView implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3874209890887625448L;
	Position position;
	int width;
	int height;

	/**
	 * Constructor
	 * 
	 * @param position The position of the view object
	 */
	public AbstractView(Position position) {
		this.position = position;
		this.width = 0;
		this.height = 0;
	}

	/**
	 * Constructor
	 * 
	 * @param position The position of the view object
	 * @param width The width of the view object
	 * @param height The height of the view object
	 */
	public AbstractView(Position position, int width, int height) {
		this.position = position;
		this.width = width;
		this.height = height;
	}

	/**
	 * Determines whether a position is inside of this view object
	 * 
	 * @param click The position of the click
	 * @return Returns whether this view object was clicked
	 */
	public boolean isClicked(Position click) {
		boolean isClicked = false;
		if (position.getX() < click.getX()
				&& click.getX() < position.getX() + width
				&& position.getY() < click.getY()
				&& click.getY() < position.getY() + height) {
			isClicked = true;
		}
		return isClicked;
	}

	/**
	 * Determines whether this view object is overlapping a given view
	 * 
	 * @param otherView The other view
	 * @return Returns whether this view overlaps with the other view
	 */
	public boolean isOverlapping(AbstractView otherView) {
		boolean isOverlapping = false;
		Position otherPosition = otherView.getPosition();
		Position otherBottomRightPosition = new Position(otherPosition.getX()
				+ otherView.width, otherPosition.getY() + otherView.height);
		Position bottomRightPosition = new Position(position.getX() + width,
				position.getY() + height);

		// Check if the bottom right corner of OtherView is to the bottom right
		// of our top left corner
		if (position.getX() <= otherBottomRightPosition.getX()
				&& position.getY() <= otherBottomRightPosition.getY()) {
			// Check if OtherView's top left corner is to the top left of our
			// bottom right corner
			if (otherPosition.getX() <= bottomRightPosition.getX()
					&& otherPosition.getY() <= bottomRightPosition.getY()) {
				isOverlapping = true;
			}
		}
		return isOverlapping;
	}

	/**
	 * Gets the position of this view
	 * 
	 * @return Returns the position of this view
	 */
	public Position getPosition() {
		return position;
	}

    public int getWidth() { return width; }

    public int getHeight() { return height; }

}
