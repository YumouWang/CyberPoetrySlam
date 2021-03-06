package controllers;

import models.AbstractWord;
import models.GameState;
import models.Word;
import views.MainView;

import java.util.Collection;
import java.util.HashSet;

/**
 * Search Area controller
 *
 * @author Yumou
 * @version 10/3/2014
 */
public class SearchController {
	/**
	 * The search object handles seatch functions
	 * wordtable is a collection of words in exploreArea
	 */
    private static SearchController search;
    public Collection<AbstractWord> wordtable;
    /**
	 * The MainView of the game
	 */
    MainView mainView;
    /**
     * The GameState of the game
     */
    GameState gameState;
    Collection<AbstractWord> result;

    /**
     * Constructor
     *
     * @param mainView  The MainView of the game
     * @param gameState The GameState of the game
     */
    public SearchController(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.gameState = gameState;
    }

    /**
     * To update the wordtable of the ExploreArea
     */
    public void updateWordTable() {
        wordtable = gameState.getUnprotectedArea().getAbstractWordCollection();
    }

    /**
     * The implementation of searching word of specific types or containing
     * specific word pieces
     *
     * @param word     The word piece to be searched in the ExploreArea
     * @param wordtype The wordtype to be searched in the ExploreArea
     * @return returns a collection of AbstractWord as a searching result
     */
    public Collection<AbstractWord> search(String word, String wordtype) {
        updateWordTable();

        result = new HashSet<AbstractWord>();
        if (word.equals("") && wordtype.equals("")) {
            return wordtable;
        }
        for (AbstractWord word1 : wordtable) {
            String wordValue = word1.getValue().toLowerCase();
            String wordType = ((Word) word1).getType().toString();
            
            if (wordValue.equalsIgnoreCase(word)
                    && wordtype.equalsIgnoreCase(wordType)) {
                result.add(word1);
            }
            if (wordValue.equalsIgnoreCase(word)
                    && wordtype.equalsIgnoreCase("")) {
                result.add(word1);
            }
            if (word.equalsIgnoreCase("")
                    && wordtype.equalsIgnoreCase(wordType)) {
                result.add(word1);
            }
        }
        return result;
    }
}
