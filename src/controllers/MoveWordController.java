package controllers;

import java.awt.Color;
import java.util.Collection;
import java.util.List;

import models.AbstractWord;
import models.GameState;
import models.Position;
import views.AbstractWordView;
import views.AdjacencyType;
import views.MainView;
import views.PoemView;
import views.RowView;
import views.WordView;

/**
 * A class for handling moving words
 *
 * Created by Nathan on 10/16/2014.
 */
public class MoveWordController {

    MainView mainView;
    GameState gameState;

    /**
     * Constructor
     * @param mainView The MainView to update once a connection has been made
     * @param gameState The GameState to perform connections on
     */
    public MoveWordController(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.gameState = gameState;
    }

    public void moveWord(AbstractWordView selectedWord, Position positionFrom, Position positionTo) {
        Position originPosition = selectedWord.getPosition();
        Position positionDiff = new Position(positionTo.getX() - positionFrom.getX(),
                positionTo.getY() - positionFrom.getY());
        Position newPosition = new Position(selectedWord.getPosition().getX() + positionDiff.getX(),
                selectedWord.getPosition().getY() + positionDiff.getY());
        selectedWord.moveTo(newPosition);
        
        if (mainView.isInProtectedArea(originPosition) && mainView.isInProtectedArea(newPosition)) {
            // word move from protect area to protect area
            protectAreaWordMove(selectedWord);
        }
        else if (!mainView.isInProtectedArea(originPosition) && mainView.isInProtectedArea(newPosition)) {
            // word move from unprotect area to protect area
            System.out.println("you are protecting word:" + selectedWord.getWord().getValue());
            protectWord(selectedWord);
        }
        else if (mainView.isInProtectedArea(originPosition) && !mainView.isInProtectedArea(newPosition)) {
            // word move from protect area to unprotect area;
            System.out.println("you are unprotecting word:" + selectedWord.getWord().getValue());
            if(selectedWord instanceof WordView){
            	unprotectWord(selectedWord);
            }
            if(selectedWord instanceof RowView) {
            	System.out.println("release row");
            	relaseRow((RowView)selectedWord);
            }
            if(selectedWord instanceof PoemView) {
            	System.out.println("release poem");
            	relasePoem((PoemView)selectedWord);
            }
            
        }
        // Otherwise, the word started and ended in the unprotected area,
        // So we don't need to do anything special
    }

    /**
     * Checks for collisions or adjacency and changes colors after moving a word in the protected area
     * @param selectedWord The word being moved
     */
    void protectAreaWordMove(AbstractWordView selectedWord) {
        boolean isOverlapping = false;
        boolean isAdjacent = false;
        Collection<AbstractWordView> words = mainView.getProtectedAreaWords();
        for (AbstractWordView word : words) {
            if(!word.equals(selectedWord)) {
                if (word.isOverlapping(selectedWord)) {
                    isOverlapping = true;
                }
                AdjacencyType adjacencyType = selectedWord.isAdjacentTo(word);
                if(adjacencyType != AdjacencyType.NOT_ADJACENT) {
                    isAdjacent = true;
                    word.setBackground(Color.GREEN);
                } else {
                    word.setBackground(Color.LIGHT_GRAY);
                }
            }
        }
        if(isOverlapping) {
            selectedWord.setBackground(Color.RED);
        } else if (isAdjacent) {
            selectedWord.setBackground(Color.GREEN);
        } else {
            selectedWord.setBackground(Color.LIGHT_GRAY.brighter());
        }
    }

    /**
     * Protects a word that was just moved
     * @param wordView The wordView to protect
     */
    void protectWord(AbstractWordView wordView) {
        // Add word to protected word list
        gameState.protect(wordView.getWord());
        // Add word view to protected word view list
        mainView.addProtectedAbstractWordView(wordView);

        // Print out the results
        Collection<AbstractWord> protectedWords = gameState.getProtectedArea()
                .getAbstractWordCollection();
        System.out.print("protectWord list: ");
        for (AbstractWord word : protectedWords) {
            System.out.print(word.getValue() + ",");
        }
        System.out.println();

        Collection<AbstractWord> unprotectedWords = gameState.getUnprotectedArea()
                .getAbstractWordCollection();
        System.out.print("unprotectWord list: ");
        for (AbstractWord word : unprotectedWords) {
            System.out.print(word.getValue() + ",");
        }
        System.out.println();
    }

    /**
     * Unprotects a word that was just moved
     * @param wordView The wordView to unprotect
     */
    void unprotectWord(AbstractWordView wordView) {
        // Add word to unprotected word list
        gameState.unprotect(wordView.getWord());
        // Add word view to unprotected word view list
        mainView.addUnprotectedAbstractWordView(wordView);

        // Print out the results
        System.out.print("unprotectWord list: ");
        Collection<AbstractWord> unprotectedWords = gameState.getUnprotectedArea()
                .getAbstractWordCollection();
        for (AbstractWord word : unprotectedWords) {
            System.out.print(word.getValue() + ",");
        }
        System.out.println();

        Collection<AbstractWord> protectedWords = gameState.getProtectedArea()
                .getAbstractWordCollection();
        System.out.print("protectWord list: ");
        for (AbstractWord word : protectedWords) {
            System.out.print(word.getValue() + ",");
        }
        System.out.println();
    }
    
	/**
	 * release a row that was just moved
	 */
	void relaseRow(RowView rowView) {

		List<WordView> words = rowView.getWordViews();

		for (WordView word : words) {
			gameState.getProtectedArea().removeAbstractWord(rowView.getWord());
			gameState.getUnprotectedArea().addAbstractWord(word.getWord());
			
			unprotectWord(word);
			
			Collection<AbstractWord> protectedWords = gameState
					.getProtectedArea().getAbstractWordCollection();
			System.out.print("protectWord list: ");
			for (AbstractWord word1 : protectedWords) {
				System.out.print(word1.getValue() + ",");
			}
			System.out.println();

			Collection<AbstractWord> unprotectedWords = gameState
					.getUnprotectedArea().getAbstractWordCollection();
			System.out.print("unprotectWord list: ");
			for (AbstractWord word2 : unprotectedWords) {
				System.out.print(word2.getValue() + ",");
			}
			System.out.println();
		}
	}
    
	/**
	 * release a poem that was just moved
	 */
    void relasePoem(PoemView poemView) {
    	List<RowView> rows = poemView.getRowViews();
    	gameState.getProtectedArea().removeAbstractWord(poemView.getWord());
    	for (RowView row : rows) {
    		gameState.getProtectedArea().removeAbstractWord(row.getWord());
    		relaseRow(row);
    	}
    }
}