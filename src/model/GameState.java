package model;

/**
 * The main model class that tracks all other models
 *
 * Created by Nathan on 10/3/2014.
 */
public class GameState {
    Area protectedArea;
    Area unprotectedArea;

    public GameState() {
        protectedArea = new Area(null);
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
