package models;

/**
 * The Position model class
 *
 * Created by Nathan on 10/3/2014.
 */
public class Position {
    int x;
    int y;

    /**
     * Constructor
     * @param x The x coordinate of the position
     * @param y The y coordinate of the position
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x coordinate
     * @return Returns the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate
     * @return Returns the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Checks if this position is the same as another position
     * @param position The position to compare
     * @return Returns whether the positions are the same
     */
    public boolean equals(Position position) {
        return x == position.getX() && y == position.getY();
    }
}
