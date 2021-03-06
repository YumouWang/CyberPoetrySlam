package controllers;

import common.Constants;
import models.GameState;
import models.Position;
import views.*;

import java.awt.*;
import java.util.Collection;
import java.util.List;

/**
 * A class for handling moving words
 *
 * @author Nathan
 * @author Yumou
 * @version 10/16/2014
 */
public class MoveWordController {
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
     * @param mainView  The MainView to update once a connection has been made
     * @param gameState The GameState to perform connections on
     */
    public MoveWordController(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.gameState = gameState;
    }


    /**
     * @param selectedWord The word being moved
     * @param positionFrom The original position of word before moving
     * @param positionTo   The final position of word after moving
     * @return boolean Returns whether the word is successfully moved
     */
    public boolean moveWord(AbstractWordView selectedWord, Position positionFrom, Position positionTo) {
        boolean isProtectedOrUnprotect = false;

        Position originPosition = selectedWord.getPosition();
        Position positionDiff = new Position(positionTo.getX()
                - positionFrom.getX(), positionTo.getY() - positionFrom.getY());
        Position newPosition = new Position(selectedWord.getPosition().getX()
                + positionDiff.getX(), selectedWord.getPosition().getY()
                + positionDiff.getY());

        selectedWord.moveTo(newPosition);

        boolean isOverlappingOtherWord = false;
        for (AbstractWordView otherView : mainView.getProtectedAreaWords()) {
            if (!selectedWord.equals(otherView)
                    && selectedWord.isOverlapping(otherView)) {
                isOverlappingOtherWord = true;
            }
        }

        if (mainView.isMoveOutOfBounds(selectedWord, newPosition)
                || isOverlappingOtherWord) {
            selectedWord.moveTo(originPosition);
        } else {
            selectedWord.moveTo(newPosition);
        }

        if (mainView.isInProtectedArea(originPosition)
                && mainView.isInProtectedArea(newPosition)) {
            // word move from protect area to protect area
            // if cross the line
            if ((newPosition.getY() > (Constants.PROTECTED_AREA_HEIGHT - 20))
                    && (newPosition.getY() < Constants.PROTECTED_AREA_HEIGHT)) {               
                moveWord(selectedWord, newPosition,
                        new Position(newPosition.getX(),
                                Constants.PROTECTED_AREA_HEIGHT));
            }
            protectAreaWordMove(selectedWord);
        } else if (!mainView.isInProtectedArea(originPosition)
                && mainView.isInProtectedArea(newPosition)) {
            // word move from unprotect area to protect area
            // if cross the line
            if ((newPosition.getY() > (Constants.PROTECTED_AREA_HEIGHT - 20))
                    && (newPosition.getY() < Constants.PROTECTED_AREA_HEIGHT)) {
                selectedWord.moveTo(new Position(newPosition.getX(),
                        Constants.PROTECTED_AREA_HEIGHT - 20));
            }
            protectWord(selectedWord);
        } else if (mainView.isInProtectedArea(originPosition)
                && !mainView.isInProtectedArea(newPosition)) {
            // word move from protect area to unprotect area
            // if cross the line
            if ((newPosition.getY() > (Constants.PROTECTED_AREA_HEIGHT - 20))
                    && (newPosition.getY() < Constants.PROTECTED_AREA_HEIGHT)) {
                selectedWord.moveTo(new Position(newPosition.getX(),
                        Constants.PROTECTED_AREA_HEIGHT));
            }
            if (selectedWord instanceof WordView) {
                unprotectWord(selectedWord);
            }
        }
        // Otherwise, the word started and ended in the unprotected area,
        // So we don't need to do anything special
        return isProtectedOrUnprotect;
    }

    /**
     * Checks for collisions or adjacency and changes colors after moving a word
     * in the protected area
     *
     * @param selectedWord The word being moved
     */
    void protectAreaWordMove(AbstractWordView selectedWord) {
        boolean isOverlapping = false;
        boolean isAdjacent = false;
        Collection<AbstractWordView> words = mainView.getProtectedAreaWords();
        for (AbstractWordView word : words) {
            if (!word.equals(selectedWord)) {
                if (word.isOverlapping(selectedWord)) {
                    isOverlapping = true;
                }
                AdjacencyType adjacencyType = selectedWord.isAdjacentTo(word);
                AdjacencyType adjacencyTypeTwo = word
                        .isAdjacentTo(selectedWord);
                if (adjacencyType != AdjacencyType.NOT_ADJACENT
                        && adjacencyTypeTwo != AdjacencyType.NOT_ADJACENT) {
                    isAdjacent = true;
                    word.setBackground(Color.GREEN);

                    if (selectedWord instanceof PoemView && word instanceof PoemView &&
                            (adjacencyType == AdjacencyType.RIGHT || adjacencyType == AdjacencyType.LEFT)) {

                        isAdjacent = false;
                        word.setBackground(Color.LIGHT_GRAY);
                    }
                } else {
                    word.setBackground(Color.LIGHT_GRAY);
                }
            }
        }
        if (isOverlapping) {
            selectedWord.setBackground(Color.RED);
        } else if (isAdjacent) {
            selectedWord.setBackground(Color.GREEN);
        } else {
            selectedWord.setBackground(Color.LIGHT_GRAY.brighter());
        }
    }

    /**
     * Protects a word that was just moved
     *
     * @param wordView The wordView to protect
     */

    public void protectWord(AbstractWordView wordView) {


        // Add word to protected word list
        gameState.protect(wordView.getWord());
        // Add word view to protected word view list and remove word view from
        // unprotected word view
        mainView.addProtectedAbstractWordView(wordView);
        mainView.removeUnprotectedAbstractWordView(wordView);
        mainView.getExploreArea().updateTable();
    }

    /**
     * Unprotects a word that was just moved
     *
     * @param wordView The wordView to unprotect
     */
    public void unprotectWord(AbstractWordView wordView) {
        if (wordView instanceof RowView || wordView instanceof PoemView) {
            this.mainView.getRedoMoves().clear();
            this.mainView.getUndoMoves().clear();
            this.mainView.getRedoButton().setEnabled(false);
            this.mainView.getUndoButton().setEnabled(false);
        }
        // Add word to unprotected word list
        gameState.unprotect(wordView.getWord());
        // Add word view to unprotected word view list and remove word view from
        // protected word view
        mainView.addUnprotectedAbstractWordView(wordView);
        mainView.removeProtectedAbstractWordView(wordView);
        mainView.getExploreArea().updateTable();
    }

    /**
     * release a row that was just moved
     *
     * @param rowView The row to be released
     */
    public void releaseRow(RowView rowView) {
        List<WordView> words = rowView.getWordViews();
        // remove row view from protected abstractWord view in MainView
        mainView.removeProtectedAbstractWordView(rowView);
        // remove row from protected abstractWord in GameState
        gameState.getProtectedArea().removeAbstractWord(rowView.getWord());
        for (WordView word : words) {
            // for every word add in this row it to the unprotected abstractWord
            // in GameState
            gameState.getUnprotectedArea().addAbstractWord(word.getWord());
            // for every word view in this row view add it to the unprotected
            // abstractWord view in MainView
            mainView.addUnprotectedAbstractWordView(word);
        }
        mainView.getExploreArea().updateTable();
    }

    /**
     * release a poem that was just moved
     *
     * @param poemView The poem to be released
     */
    public void releasePoem(PoemView poemView) {

        List<RowView> rows = poemView.getRowViews();
        // remove poem from protected abstractWord in GameState
        gameState.getProtectedArea().removeAbstractWord(poemView.getWord());
        // remove poem view from protected abstractWord view in MainView
        mainView.removeProtectedAbstractWordView(poemView);
        for (RowView row : rows) {
            List<WordView> words = row.getWordViews();
            for (WordView word : words) {
                // for every word in this poem add it to the unprotected
                // abstractWord in GameState
                gameState.getUnprotectedArea().addAbstractWord(word.getWord());
                // for every word in this poem view add it to the unprotected
                // abstractWord view in MainView
                mainView.addUnprotectedAbstractWordView(word);
            }
        }
        mainView.getExploreArea().updateTable();
    }
}
