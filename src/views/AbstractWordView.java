package views;

import common.Constants;
import models.AbstractWord;
import models.Position;

import javax.swing.*;
import java.awt.*;

/**
 * The view class for each word in CyberPoetrySlam
 *
 * Created by Nathan on 10/3/2014.
 */
public class AbstractWordView extends AbstractView {

    AbstractWord word;
    JLabel label;

    public AbstractWordView(AbstractWord word, Position position) {
        super(position, 100, 20);
        this.word = word;
        label = new JLabel(word.getValue());
        updateView();
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setBackground(Color.LIGHT_GRAY);
        label.setOpaque(true);
    }

    public boolean moveTo(Position toPosition) {
        super.position = toPosition;
        updateView();
        return true;
    }

    public AdjacencyType isAdjacentTo(AbstractWordView otherWord) {
        Position aboveBoxPosition = new Position(position.getX(), position.getY() - Constants.CONNECT_DISTANCE);
        ConnectionBox above = new ConnectionBox(aboveBoxPosition, width, Constants.CONNECT_DISTANCE);
        Position belowBoxPosition = new Position(position.getX(), position.getY() + height);
        ConnectionBox below = new ConnectionBox(belowBoxPosition, width, Constants.CONNECT_DISTANCE);
        Position leftBoxPosition = new Position(position.getX() - Constants.CONNECT_DISTANCE, position.getY());
        ConnectionBox left = new ConnectionBox(leftBoxPosition, Constants.CONNECT_DISTANCE, height);
        Position rightBoxPosition = new Position(position.getX() + width, position.getY());
        ConnectionBox right = new ConnectionBox(rightBoxPosition, Constants.CONNECT_DISTANCE, height);

        AdjacencyType returnType = AdjacencyType.NOT_ADJACENT;
        if(left.isOverlapping(otherWord)) {
            returnType = AdjacencyType.RIGHT;
        } else if(right.isOverlapping(otherWord)) {
            returnType = AdjacencyType.LEFT;
        } else if(above.isOverlapping(otherWord)) {
//            returnType = AdjacencyType.BELOW;
        } else if(below.isOverlapping(otherWord)) {
//            returnType = AdjacencyType.ABOVE;
        }
        return returnType;
    }

    public void setBackground(Color color) {
        label.setBackground(color);
    }

    private void updateView() {
        label.setBounds(position.getX(), position.getY(), width, height);
    }

    public AbstractWord getWord() {
        return word;
    }
}
