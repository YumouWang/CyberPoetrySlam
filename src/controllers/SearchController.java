package controllers;

import java.util.Collection;
import java.util.HashSet;

import models.AbstractWord;
import models.GameState;
import models.Word;
import views.MainView;

/**
 * Search Area controller
 * Created by Yumou on 10/3/2014.
 */
public class SearchController {
	private static SearchController search;
	public Collection<AbstractWord> wordtable;
	MainView mainView;
	GameState gameState;
	Collection<AbstractWord> result;
	
	public SearchController(MainView mainView, GameState gameState) {
		this.mainView = mainView;
		this.gameState = gameState;
	}
	
	public void updateWordTable() {
		wordtable = gameState.getUnprotectedArea().getAbstractWordCollection();
	}
	
	public Collection<AbstractWord> search(String word, String wordtype) {
		updateWordTable();
		for (AbstractWord word2 : wordtable) {
			System.out.print(word2.getValue() + ",");
		}
		
		result = new HashSet<AbstractWord>();
		if(word.equals("") && wordtype.equals("")) {
			return wordtable;
		}
		for (AbstractWord word1 : wordtable) {
			String wordValue = word1.getValue();
			String wordType = ((Word)word1).getType().toString();
			if(word.equalsIgnoreCase(wordValue) && wordtype.equalsIgnoreCase(wordType)) {
            	result.add(word1);
            }
            if(word.equalsIgnoreCase(wordValue) && wordtype.equalsIgnoreCase("")) {
            	result.add(word1);
            }
            if(word.equalsIgnoreCase("") && wordtype.equalsIgnoreCase(wordType)) {
            	result.add(word1);
            }   
            //System.out.println(word1.getValue().toString());
		}
		return result;
	}

}

