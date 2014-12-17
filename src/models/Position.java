package models;

import java.io.Serializable;

/**
 * The Position model class
 *
 * @author Nathan Jian
 * @version 10/3/2014
 */
public class Position implements Serializable {
    /**
     *Serialized ID for a Position
     */
    private static final long serialVersionUID = 9049790175731613229L;
    /**
     * Coordinate x and y of the Position
     */
    int x;
    int y;

    /**
     * Constructor
     *
     * @param x The x coordinate of the position
     * @param y The y coordinate of the position
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
     * changes the position of x values
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
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
     * changes the position of y values
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Checks if this position is the same as another position
     *
     * @param position The position to compare
     * @return Returns whether the positions are the same
     */
    public boolean equals(Position position) {
        return x == position.getX() && y == position.getY();
    }
}
