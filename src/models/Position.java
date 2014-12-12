package models;

import java.io.Serializable;

/**
 * The Position model class
 *
 * @author Nathan
 * @version 10/3/2014
 */
public class Position implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9049790175731613229L;
	int x;
	int y;

	/**
	 * Constructor
	 * 
	 * @param x
	 *            The x coordinate of the position
	 * @param y
	 *            The y coordinate of the position
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x coordinate
	 * 
	 * @return Returns the x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y coordinate
	 * 
	 * @return Returns the y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Checks if this position is the same as another position
	 * 
	 * @param position
	 *            The position to compare
	 * @return Returns whether the positions are the same
	 */
	public boolean equals(Position position) {
		return x == position.getX() && y == position.getY();
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
