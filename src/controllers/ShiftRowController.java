package controllers;

import models.GameState;
import models.Position;
import views.AbstractWordView;
import views.MainView;
import views.PoemView;
import views.RowView;

/**
 * This class handles shifting rows in a poem
 *
 * @author Yang
 * @version 12/2/2014
 */

public class ShiftRowController {
	/**
	 * The MainView of the game
	 */
    MainView mainView;
    /**
     * The GameState of the game
     */
    GameState gameState;

    /**
     * Constructor
     *
     * @param mainView  The Mainview to update once some row is shifted
     * @param gameState The Gamestate to perform shifting rows on
     */

    public ShiftRowController(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.gameState = gameState;
    }

    /**
     * Implementation of shifting a row under various conditions
     *
     * @param selectedPoem The poem which includes the shifted row
     * @param selectedRow  The row to shift
     * @param positionFrom The original position of shifted row
     * @param positionTo   The final position of shifted row
     */
    public void shiftRow(PoemView selectedPoem, AbstractWordView selectedRow,
                         Position positionFrom, Position positionTo) {
        if (selectedRow == null || selectedRow instanceof PoemView) {
            return;
        }

        int shiftAmount = positionFrom.getX() - positionTo.getX();
        RowView theRow = null;

        for (RowView row : selectedPoem.getRowViews()) {
            if (row.contains(selectedRow)) {

                shiftRowTemp(selectedPoem, row, shiftAmount);
                theRow = row;
            }
        }

        boolean isPoemDisConnected = false;
        int length = selectedPoem.getRowViews().size();
        int index = selectedPoem.getRowViews().indexOf(theRow);
        if (index == 0) {
            if (!theRow.isOverlapping(selectedPoem.getRowViews().get(1)))
                isPoemDisConnected = true;
            else
                isPoemDisConnected = false;
        } else if (index == selectedPoem.getRowViews().size() - 1) {
            if (!theRow.isOverlapping(selectedPoem.getRowViews()
                    .get(length - 2)))
                isPoemDisConnected = true;
            else
                isPoemDisConnected = false;
        } else {
            if (!theRow
                    .isOverlapping(selectedPoem.getRowViews().get(index - 1))
                    || !theRow.isOverlapping(selectedPoem.getRowViews().get(
                    index + 1)))
                isPoemDisConnected = true;
            else
                isPoemDisConnected = false;
        }

        if (isPoemDisConnected) {
            shiftRowTemp(selectedPoem, theRow, -shiftAmount);
        }

        boolean isOverlappingOtherWord = false;
        for (AbstractWordView otherView : mainView.getProtectedAreaWords()) {
            if (!theRow.equals(otherView) && !selectedPoem.contains(otherView)
                    && theRow.isOverlapping(otherView)) {
                isOverlappingOtherWord = true;
            }
        }

        if (isOverlappingOtherWord) {
            shiftRowTemp(selectedPoem, theRow, -shiftAmount);
        }

        boolean isOutOfBounds = false;
        if (mainView.isMoveOutOfBounds(theRow, theRow.getPosition())) {
            isOutOfBounds = true;
        }

        if (isOutOfBounds) {
            shiftRowTemp(selectedPoem, theRow, -shiftAmount);
        }
    }

    /**
     * Private helper for ShiftRowController. Shifts the row by some amount
     *
     * @param selectedPoem The poem which includes the shifted row
     * @param selectedRow  The row to shift
     * @param shiftAmount  The shifting amount of target row
     */
    void shiftRowTemp(PoemView selectedPoem, AbstractWordView selectedRow,
                      int shiftAmount) {
        if (selectedRow == null || selectedRow instanceof PoemView) {
            return;
        }
        selectedPoem.shiftRow(selectedRow, shiftAmount);
    }

}
