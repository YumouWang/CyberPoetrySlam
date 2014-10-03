package views;

import model.AbstractWord;
import model.Position;

import javax.swing.*;
import java.awt.*;

/**
 * The view class for each word in CyberPoetrySlam
 * Created by Nathan on 10/3/2014.
 */
public class AbstractWordView extends AbstractView {

    AbstractWord word;
    JLabel label;

    public AbstractWordView(AbstractWord word, Position position) {
        super(position, 30, 20);
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

    public boolean isOverlapping(AbstractWordView otherWord) {
        return false;
    }

    private void updateView() {
        label.setBounds(position.getX(), position.getY(), width, height);
    }

    public JLabel getLabel() {
        return label;
    }

    public AbstractWord getWord() {
        return word;
    }
}
