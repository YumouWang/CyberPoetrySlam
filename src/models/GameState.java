package models;

import common.Constants;
import controllers.UndoWithMemento;
import controllers.WordInitialize;
import views.AbstractWordView;
import views.RowView;
import views.WordView;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 * The main model class that tracks all other models
 * 
 * @author Nathan
 * @author Jian
 * @version 10/3/2014
 */
public class GameState implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8711483643686783088L;

	// These two arrayLists are used to obtain wordviews from existed Mementoes
	Collection<AbstractWordView> unprotectedWordViews;
	Collection<AbstractWordView> protectedWordViews;

	Collection<Swap> pendingSwaps;

	Area protectedArea;
	Area unprotectedArea;

	/**
	 * Constructor
	 */

	public GameState(UndoWithMemento memento) {
		pendingSwaps = new HashSet<Swap>();
		if (memento == null) {
			Collection<AbstractWord> protectedWords = new HashSet<AbstractWord>();
			Collection<AbstractWord> unprotectedWords = new HashSet<AbstractWord>();
			WordInitialize wordInitialize = new WordInitialize();
			Collection<Word> wordList = wordInitialize.getInitialWordFromFile(Constants.WORDS_AND_TYPES_FILENAME);
			for (Word word : wordList) {
				unprotectedWords.add(word);
			}

			protectedArea = new Area(protectedWords);
			unprotectedArea = new Area(unprotectedWords);
		} else {
			// Otherwise loading the memento state will be handled in the mainView
			Collection<AbstractWord> protectedWords = new HashSet<AbstractWord>();
			Collection<AbstractWord> unprotectedWords = new HashSet<AbstractWord>();
			protectedArea = new Area(protectedWords);
			unprotectedArea = new Area(unprotectedWords);
		}
	}

	/**
	 * Protects a word
	 * 
	 * @param word
	 *            The word to protect
	 * @return Returns whether the word was successfully protected (false if
	 *         already protected)
	 */
	public boolean protect(AbstractWord word) {
		boolean success = false;
		if (unprotectedArea.removeAbstractWord(word)) {
			success = protectedArea.addAbstractWord(word);
		}
		return success;
	}

	/**
	 * Unprotects a word
	 * 
	 * @param word
	 *            The word to unprotect
	 * @return Returns whether the word was successfully unprotected (false if
	 *         already unprotected)
	 */
	public boolean unprotect(AbstractWord word) {
		boolean success = false;
		if (protectedArea.removeAbstractWord(word)) {
			success = unprotectedArea.addAbstractWord(word);
		}
		return success;
	}

	/**
	 * Gets the protected area
	 * 
	 * @return The protected area
	 */
	public Area getProtectedArea() {
		return protectedArea;
	}

	/**
	 * Gets the unprotected area
	 * 
	 * @return The unprotected area
	 */
	public Area getUnprotectedArea() {
		return unprotectedArea;
	}

	public Collection<Swap> getPendingSwaps() { return pendingSwaps; }
	
	public void resetUnprotectedArea(Collection<AbstractWord> collection) {
		this.unprotectedArea = new Area(collection);
		return;
	}
	
	public void resetProtectedArea(Collection<AbstractWord> collection) {
		this.protectedArea = new Area(collection);
		return;
	}
}
