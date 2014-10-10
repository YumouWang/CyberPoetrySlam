package controllerTest;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;

import controllers.Search;
import models.AbstractWord;
import models.GameState;
import views.MainGUI;

public class TestSearch {
	public static void main(String[] args) {
        GameState gameState = new GameState();
        MainGUI mainGUI = new MainGUI(gameState);
        Search search = new Search(mainGUI, gameState);
		search.updateWordTable();
		Collection<AbstractWord> result = search.search("day", "");
		System.out.println(result.size());
        for(AbstractWord word: result){
            System.out.println(word.getValue());
        }
	}
}
