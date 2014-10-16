package views;

import models.Position;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashSet;

/**
 * A class representing the box displayed when dragging to select multiple items
 * Created by Nathan on 10/10/2014.
 */
public class SelectionBox extends JComponent {
    Position endLocation;
    Position startLocation;

    public SelectionBox() {
        endLocation = null;
        startLocation = null;
    }

    public void clearBox() {
        endLocation = null;
        startLocation = null;
        setVisible(false);
    }

    public void startNewSelection(Position start) {
        setVisible(true);
        startLocation = start;
    }

    public void moveSelection(Position move) {
        endLocation = move;
    }

    public Collection<AbstractWordView> getSelectedItems(Collection<AbstractWordView> words) {
        Collection<AbstractWordView> selectedItems = new HashSet<AbstractWordView>();
        if(startLocation != null && endLocation != null) {
            int x = (startLocation.getX() < endLocation.getX()) ? startLocation.getX() : endLocation.getX();
            int y = (startLocation.getY() < endLocation.getY()) ? startLocation.getY() : endLocation.getY();
            int width = Math.abs(startLocation.getX() - endLocation.getX());
            int height = Math.abs(startLocation.getY() - endLocation.getY());
            ConnectionBox collision = new ConnectionBox(new Position(x, y), width, height);

            for (AbstractWordView view : words) {
                if (collision.isOverlapping(view)) {
                    selectedItems.add(view);
                }
            }
        }
        return selectedItems;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(startLocation != null && endLocation != null) {
            int x = (startLocation.getX() < endLocation.getX()) ? startLocation.getX() : endLocation.getX();
            int y = (startLocation.getY() < endLocation.getY()) ? startLocation.getY() : endLocation.getY();
            int width = Math.abs(startLocation.getX() - endLocation.getX());
            int height = Math.abs(startLocation.getY() - endLocation.getY());
            g.drawRect(x, y, width, height);
        }
    }
}