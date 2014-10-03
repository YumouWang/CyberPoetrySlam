package views;

import model.Position;

import javax.swing.*;

/**
 * The base class for each view component of CyberPoetrySlam
 *
 * Created by Nathan on 10/3/2014.
 */
public abstract class AbstractView extends JComponent {
    Position position;
    int width;
    int height;

    public AbstractView(Position position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public boolean isClicked(Position click) {
        boolean isClicked = false;
        if(position.getX() < click.getX() && click.getX() < position.getX() + width
            && position.getY() < click.getY() && click.getY() < position.getY() + height) {
            isClicked = true;
        }
        return isClicked;
    }

    public Position getPosition() {
        return position;
    }
}
