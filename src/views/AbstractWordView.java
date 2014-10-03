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
        super(position, 80, 20);
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
        return AdjacencyType.NOT_ADJACENT;
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
