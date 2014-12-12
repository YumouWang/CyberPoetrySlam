package models;

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

	public GameState(UnprotectedMemento un, ProtectedMemento p) {
		pendingSwaps = new HashSet<Swap>();
		if (un == null && p == null) {
			Collection<AbstractWord> protectedWords = new HashSet<AbstractWord>();
			

			Collection<AbstractWord> unprotectedWords = new HashSet<AbstractWord>();
			WordInitialize wordInitialize = new WordInitialize();
			Collection<Word> wordList = wordInitialize
					.getInitialWordFromFile("Dictionary/WordDictionary.csv");
			for (Word word : wordList) {
				unprotectedWords.add(word);
			}
			

			protectedArea = new Area(protectedWords);
			unprotectedArea = new Area(unprotectedWords);
		} else {
			unprotectedWordViews = un.getUnprotectedView();
			protectedWordViews = p.getProtectedView();
			Collection<AbstractWord> unprotectedWords = new HashSet<AbstractWord>();
			Collection<AbstractWord> protectedWords = new HashSet<AbstractWord>();
			for (AbstractWordView abs : unprotectedWordViews) {
				WordView wordView = (WordView) abs;
				Word w = wordView.getWord();
				unprotectedWords.add(w);
			}
			for (AbstractWordView abs : protectedWordViews) {
				if (abs instanceof WordView) {
					WordView wordView = (WordView) abs;
					Word w = wordView.getWord();
					protectedWords.add(w);
				} else if (abs instanceof RowView) {
					Row r = (Row) abs.getWord();
					protectedWords.add(r);
					// List<Word> word = r.getWords();
					// for(Word w: word){
					// protectedWords.add(w);
					// }
				} else {
					// This is for poems
					Poem poem = (Poem) abs.getWord();
					protectedWords.add(poem);
				}
			}
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
}
