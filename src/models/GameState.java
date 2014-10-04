package models;

import java.util.Collection;
import java.util.HashSet;

/**
 * The main model class that tracks all other models
 *
 * Created by Nathan on 10/3/2014.
 */
public class GameState {
    static GameState instance;

    Area protectedArea;
    Area unprotectedArea;

    public static GameState getInstance() {
        if(instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    private GameState() {
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

    public boolean protect(AbstractWord word) {
        boolean success = false;
        if(unprotectedArea.removeAbstractWord(word)) {
            success = protectedArea.addAbstractWord(word);
        }
        return success;
    }

    public boolean unprotect(AbstractWord word) {
        boolean success = false;
        if(protectedArea.removeAbstractWord(word)) {
            success = unprotectedArea.addAbstractWord(word);
        }
        return success;
    }

    public Area getProtectedArea() {
        return protectedArea;
    }

    public Area getUnprotectedArea() {
        return unprotectedArea;
    }
}
