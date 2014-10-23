package models;

import java.io.Serializable;
import java.util.ArrayList;

import views.WordView;
import views.AbstractWordView;

public class unprotectedWordMemento implements Serializable{
	
	private static final long serialVersionUID = 6866467097920091719L;
	ArrayList<AbstractWordView> unprotectedWordViews = new ArrayList<AbstractWordView>();
	ArrayList<AbstractWord> unprotectedWords = new ArrayList<AbstractWord>();
	
	/**
	 * Has special permissions to be able to inspect all attributes of Shape objects
	 * and make copy as needed.
	 * 
	 * @param shapes
	 */
	public unprotectedWordMemento(ArrayList<AbstractWordView> words) {
		for (AbstractWordView s : words) {
			//long id = s.getWord().id;
			unprotectedWordViews.add(new WordView((Word)s.getWord(),s.getPosition()));
			unprotectedWords.add((Word)s.getWord());
			//unprotectedWords.add(new Word((Word)s.getWord().getValue(),s.getWord().getType()));
		}
	}

	/**
	 * Unique tag for memento objects on disk
	 * 
	 * @see java.io.Serializable
	 */
	//private static final long serialVersionUID = 1L;
}
