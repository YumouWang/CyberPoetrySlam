package views;

import models.Position;

/**
 * Class for detecting if two words are close enough to be connected.
 * Used when checking if the user wanted to make a connection, one is put on each edge of the word
 * to check if it intersects with the word that was just moved.
 *
 * Created by Nathan on 10/3/2014.
 */
public class ConnectionBox extends AbstractView {

    /**
     * Constructor
     * @param position The position of the ConnectionBox
     * @param width The width of the ConnectionBox
     * @param height The height of the ConnectionBox
     */
    public ConnectionBox(Position position, int width, int height) {
        super(position, width, height);
    }
}
