package views;

import models.AbstractWord;
import models.Position;

import javax.swing.*;
import java.awt.*;

/**
 * A view for representing words on the screen
 * Created by Nathan on 10/9/2014.
 */
public class WordView extends AbstractWordView {

    JLabel label;

    /**
     * Constructor
     *
     * @param word     The word that this view represents
     * @param position The position of this word
     */
    public WordView(AbstractWord word, Position position) {
        super(word, position);
        label = new JLabel(word.getValue());
        updateView();
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setBackground(Color.LIGHT_GRAY);
        label.setOpaque(true);
    }

    /**
     * Sets the background color of this word view
     * @param color The color to set the background to
     */
    public void setBackground(Color color) {
        label.setBackground(color);
    }

    /**
     * Updates the view
     */
    private void updateView() {
        label.setBounds(position.getX(), position.getY(), width, height);
    }

    /**
     * Moves the word to the specified position
     * @param toPosition The position to move to
     * @return Returns whether the move was successful
     */
    public boolean moveTo(Position toPosition) {
        super.position = toPosition;
        updateView();
        return true;
    }
}
