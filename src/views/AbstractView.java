package views;

import models.Position;

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
        boolean isOverlapping = false;
        Position otherPosition = otherView.getPosition();
        Position otherBottomRightPosition = new Position(otherPosition.getX() + otherView.width, otherPosition.getY() + otherView.height);
        Position bottomRightPosition = new Position(position.getX() + width, position.getY() + height);

        // Check if the bottom right corner of OtherView is to the bottom right of our top left corner
        if(position.getX() <= otherBottomRightPosition.getX() && position.getY() <= otherBottomRightPosition.getY()) {
            // Check if OtherView's top left corner is to the top left of our bottom right corner
            if(otherPosition.getX() <= bottomRightPosition.getX() && otherPosition.getY() <= bottomRightPosition.getY()) {
                isOverlapping = true;
            }
        }
        return isOverlapping;
    }

    public Position getPosition() {
        return position;
    }
}
