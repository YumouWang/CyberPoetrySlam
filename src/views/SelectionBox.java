package views;

import models.Position;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashSet;

/**
 * A class representing the box displayed when dragging to select multiple items
 * Not used at the moment, but could be used to select rows so we can disconnect them.
 *
 * @author Nathan
 * @version 10/10/2014
 */
public class SelectionBox extends JComponent {
	/**
	 * StartPosition and EndPosition of a selectionBox
	 */
    Position endLocation;
    Position startLocation;
/**
 * Constructor
 */
    public SelectionBox() {
        endLocation = null;
        startLocation = null;
    }
/**
 * Clear the selectionBox and set it as invisible
 */
    public void clearBox() {
        endLocation = null;
        startLocation = null;
        setVisible(false);
    }
/**
 * Start new selectionBox from one Position
 * @param start
 */
    public void startNewSelection(Position start) {
        setVisible(true);
        startLocation = start;
    }
/**
 * Move the selectionBox to another Position
 * @param move
 */
    public void moveSelection(Position move) {
        endLocation = move;
    }
/**
 * Get all the AbstractWordView elements from a selectionBox created by mouse
 * @param words
 * @return Collection<AbstractWordView>
 */
    public Collection<AbstractWordView> getSelectedItems(Collection<AbstractWordView> words) {
        Collection<AbstractWordView> selectedItems = new HashSet<AbstractWordView>();
        if (startLocation != null && endLocation != null) {
            int x = (startLocation.getX() < endLocation.getX()) ? startLocation.getX() : endLocation.getX();
            int y = (startLocation.getY() < endLocation.getY()) ? startLocation.getY() : endLocation.getY();
            int width = Math.abs(startLocation.getX() - endLocation.getX());
            int height = Math.abs(startLocation.getY() - endLocation.getY());
            ConnectionBox collision = new ConnectionBox(new Position(x, y), width, height);

            for (AbstractWordView view : words) {
                if (collision.isOverlapping(view)) {
                    selectedItems.add(view.getSelectedElement(collision));
                }
            }
        }
        return selectedItems;
    }
/**
 * Get one AbstractWordView from a selectionBox created by mouse
 * @param words
 * @return AbstractWordView
 */
    public AbstractWordView getSelectedItem(Collection<AbstractWordView> words) {
        // Returns one of the selected words. Not guaranteed to select the same one each time
        AbstractWordView selectedItem = null;
        if (startLocation != null && endLocation != null) {
            int x = (startLocation.getX() < endLocation.getX()) ? startLocation.getX() : endLocation.getX();
            int y = (startLocation.getY() < endLocation.getY()) ? startLocation.getY() : endLocation.getY();
            int width = Math.abs(startLocation.getX() - endLocation.getX());
            int height = Math.abs(startLocation.getY() - endLocation.getY());
            ConnectionBox collision = new ConnectionBox(new Position(x, y), width, height);

            for (AbstractWordView view : words) {
                if (collision.isOverlapping(view)) {
                    selectedItem = view.getSelectedElement(collision);
                }
            }
        }
        return selectedItem;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (startLocation != null && endLocation != null) {
            int x = (startLocation.getX() < endLocation.getX()) ? startLocation.getX() : endLocation.getX();
            int y = (startLocation.getY() < endLocation.getY()) ? startLocation.getY() : endLocation.getY();
            int width = Math.abs(startLocation.getX() - endLocation.getX());
            int height = Math.abs(startLocation.getY() - endLocation.getY());
            g.drawRect(x, y, width, height);
        }
    }
}
