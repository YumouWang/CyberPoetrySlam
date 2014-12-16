package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;

/**
 * The Area model class
 *
 * @author Nathan
 * @version 10/3/2014
 */
public class Area extends Observable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3373791527038475458L;
	Collection<AbstractWord> abstractWordCollection;

	/**
	 * Constructor
	 * 
	 * @param words
	 *            The collection of words to start in this area
	 */
	public Area(Collection<AbstractWord> words) {
		this.abstractWordCollection = new HashSet<AbstractWord>();
		if (words != null) {
			this.abstractWordCollection.addAll(words);
		}
	}

	/**
	 * Adds a word to this area
	 * 
	 * @param word
	 *            The word to add to the area
	 * @return Returns whether the word was added successfully
	 */
	public boolean addAbstractWord(AbstractWord word) {
		return abstractWordCollection.add(word);
	}

	/**
	 * Removes a word from this area
	 * 
	 * @param word
	 *            The word to remove from the area
	 * @return Returns whether the word was removed successfully
	 */
	public boolean removeAbstractWord(AbstractWord word) {
		return abstractWordCollection.remove(word);
	}

	/**
	 * Gets the collection of words in this area
	 * 
	 * @return Returns the collection of words
	 */
	public Collection<AbstractWord> getAbstractWordCollection() {
		return abstractWordCollection;
	}
}
