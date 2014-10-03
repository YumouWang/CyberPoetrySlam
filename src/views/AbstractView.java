package views;

import model.Position;

/**
 * The base class for each view component of CyberPoetrySlam
 *
 * Created by Nathan on 10/3/2014.
 */
public abstract class AbstractView {
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

    public boolean isOverlapping(AbstractView otherView) {
        return false;
    }

    public Position getPosition() {
        return position;
    }
}
