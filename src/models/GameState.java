package models;

import java.util.Collection;
import java.util.HashSet;

/**
 * The main model class that tracks all other models
 *
 * Created by Nathan on 10/3/2014.
 */
public class GameState {

    Area protectedArea;
    Area unprotectedArea;

    /**
     * Constructor
     */
    public GameState() {
        Collection<AbstractWord> protectedWords = new HashSet<AbstractWord>();
        protectedWords.add(new Word("Cat", WordType.NOUN));
        protectedWords.add(new Word("Dog", WordType.NOUN));
        protectedWords.add(new Word("Mouse", WordType.NOUN));
        protectedWords.add(new Word("Tiger", WordType.NOUN));
        protectedWords.add(new Word("Bear", WordType.NOUN));
        protectedWords.add(new Word("Lion", WordType.NOUN));

        protectedArea = new Area(protectedWords);
        unprotectedArea = new Area(null);
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
}
