package controllers;

import models.AbstractWord;
import models.GameState;
import models.Word;
import views.MainGUI;

import java.util.Collection;
import java.util.HashSet;

public class Search {
	private static Search search;
	public Collection<AbstractWord> wordtable;
	MainGUI mainGUI;
	GameState gameState;
	Collection<AbstractWord> result;
	
	public Search(MainGUI mainGUI, GameState gameState) {
		this.mainGUI = mainGUI;
		this.gameState = gameState;
	}
	
	public void updateWordTable() {
		wordtable = gameState.getUnprotectedArea().getAbstractWordCollection();
	}
	
	public static Search getInstance(MainGUI mainGUI, GameState gameState) {
		if(search == null) {
			search = new Search(mainGUI, gameState);
		}
		return search;
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

