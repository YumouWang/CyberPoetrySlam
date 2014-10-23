package models;

import java.io.Serializable;
import java.util.ArrayList;

import views.WordView;
import views.AbstractWordView;

public class protectedWordMemento implements Serializable{
	
	private static final long serialVersionUID = 2809303306073188302L;
	
	ArrayList<AbstractWordView> protectedWordViews = new ArrayList<AbstractWordView>();
	ArrayList<AbstractWord> protectedWords = new ArrayList<AbstractWord>();
	
	/**
	 * Has special permissions to be able to inspect all attributes of Shape objects
	 * and make copy as needed.
	 * 
	 * @param shapes
	 */
	public protectedWordMemento(ArrayList<AbstractWordView> words) {
		for (AbstractWordView s : words) {
			//long id = s.getWord().id;
			protectedWordViews.add(new WordView((Word)s.getWord(),s.getPosition()));
			protectedWords.add((Word)s.getWord());
			//protectedWords.add(new Word(s.getWord().getValue(),s.getWord().getType()));
		}
	}

	/**
	 * Unique tag for memento objects on disk
	 * 
	 * @see java.io.Serializable
	 */
	//private static final long serialVersionUID = 2L;
}
