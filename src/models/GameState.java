package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import views.WordView;
import views.AbstractWordView;

/**
 * The main model class that tracks all other models
 *
 * Created by Nathan on 10/3/2014.
 */
public class GameState {
	
	ArrayList<AbstractWordView> protectedWordViews = new ArrayList<AbstractWordView>();
	ArrayList<AbstractWordView> unprotectedWordViews = new ArrayList<AbstractWordView>();
	ArrayList<AbstractWord> protectedWords = new ArrayList<AbstractWord>();
	ArrayList<AbstractWord> unprotectedWords = new ArrayList<AbstractWord>();
	
    Area protectedArea;
    Area unprotectedArea;

    /**
     * Constructor
     */
    public GameState(String s) {
        Collection<AbstractWord> protectedWords = new HashSet<AbstractWord>();
        protectedWords.add(new Word("Cat", WordType.NOUN));
        protectedWords.add(new Word("Dog", WordType.NOUN));
        protectedWords.add(new Word("Mouse", WordType.NOUN));
        protectedWords.add(new Word("Tiger", WordType.NOUN));
        protectedWords.add(new Word("Bear", WordType.NOUN));
        protectedWords.add(new Word("Lion", WordType.NOUN));
        
        Collection<AbstractWord> unprotectedWords = new HashSet<AbstractWord>();
        unprotectedWords.add(new Word("River", WordType.NOUN));
        unprotectedWords.add(new Word("Sun", WordType.NOUN));
        unprotectedWords.add(new Word("Moon", WordType.NOUN));
        unprotectedWords.add(new Word("Tree", WordType.NOUN));
        unprotectedWords.add(new Word("Sea", WordType.NOUN));
        unprotectedWords.add(new Word("Lake", WordType.NOUN));

        protectedArea = new Area(protectedWords);
        unprotectedArea = new Area(unprotectedWords);
    }

    //Empty Constructor
    //Deal with Explorer later
    public GameState(){
    	
    }
    

    /** 
	 * Reset board to state encoded by memento
	 * Go through each item in unprotectedWordMemento's arraylist and put that item in this gameState's attribute
	 * @param protectedWordMemento un
	 */
	public void restoreUnprotectedWords(unprotectedWordMemento un) {
		for (AbstractWordView s : un.unprotectedWordViews) {
			this.unprotectedWordViews.add(new WordView((Word)s.getWord(),s.getPosition()));
		}
		for (AbstractWord s : un.unprotectedWords) {
			this.unprotectedWords.add(s);
		}
		this.unprotectedArea = new Area(unprotectedWords);
	}
	
	/** 
	 * Reset board to state encoded by memento
	 * Go through each item in protectedWordMemento's arraylist and put that item in this gameState's attribute
	 * @param protectedWordMemento p
	 */
	public void restoreProtectedWords(protectedWordMemento p) {
		for (AbstractWordView s : p.protectedWordViews) {
			this.protectedWordViews.add(new WordView((Word)s.getWord(),s.getPosition()));
		}
		for (AbstractWord s : p.protectedWords) {
			this.protectedWords.add(s);
		}
		this.protectedArea = new Area(protectedWords);
	}

	//Create a unprotectedWordMemento using unprotectedWordViews and write this Memento to file
	public unprotectedWordMemento getUnprotectedWordState(){
		return new unprotectedWordMemento(this.unprotectedWordViews);
	}
	
	//Create a protectedWordMemento using protectedWordViews and write this Memento to file
	public protectedWordMemento getProtectedWordState(){
		return new protectedWordMemento(this.protectedWordViews);
	}
	
    
    /**
     * Protects a word
     * @param word The word to protect
     * @return Returns whether the word was successfully protected (false if already protected)
     */
    public boolean protect(AbstractWord word) {
        boolean success = false;
        if(unprotectedArea.removeAbstractWord(word)) {
            success = protectedArea.addAbstractWord(word);
        }
        return success;
    }

    /**
     * Unprotects a word
     * @param word The word to unprotect
     * @return Returns whether the word was successfully unprotected (false if already unprotected)
     */
    public boolean unprotect(AbstractWord word) {
        boolean success = false;
        if(protectedArea.removeAbstractWord(word)) {
            success = unprotectedArea.addAbstractWord(word);
        }
        return success;
    }

    /**
     * Gets the protected area
     * @return The protected area
     */
    public Area getProtectedArea() {
        return protectedArea;
    }

    /**
     * Gets the unprotected area
     * @return The unprotected area
     */
    public Area getUnprotectedArea() {
        return unprotectedArea;
    }
    
    /**
     * Gets the unprotected AbstractWordViews
     * @return The unprotected AbstractWordViews
     */
    public ArrayList<AbstractWordView> getUnprotectedWordView() {
        return unprotectedWordViews;
    }
    
    /**
     * Gets the protected AbstractWordViews
     * @return The protected AbstractWordViews
     */
    public ArrayList<AbstractWordView> getProtectedWordView() {
        return protectedWordViews;
    }

    /**
     * Gets the unprotected AbstractWordViews
     * @return The unprotected AbstractWordViews
     */
    public ArrayList<AbstractWord> getUnprotectedWord() {
        return unprotectedWords;
    }
    
    /**
     * Gets the protected AbstractWordViews
     * @return The protected AbstractWordViews
     */
    public ArrayList<AbstractWord> getProtectedWord() {
        return protectedWords;
    }
    
}
