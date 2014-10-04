package views;

import model.Position;

/**
 * Class for detecting if two words are close enough to be connected.
 * Used when checking if the user wanted to make a connection, one is put on each edge of the word
 * to check if it intersects with the word that was just moved.
 *
 * Created by Nathan on 10/3/2014.
 */
public class ConnectionBox extends AbstractView {

    public ConnectionBox(Position position, int width, int height) {
        super(position, width, height);
    }
}
