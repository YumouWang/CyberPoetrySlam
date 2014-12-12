package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The model row class
 *
 * @author Nathan
 * @version 10/2/2014
 */
public class Row extends AbstractWord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5101545467090613847L;
	List<Word> words;

	/**
	 * Constructor
	 * 
	 * @param word
	 *            The only word in the row
	 */
	public Row(Word word) {
		super();
		this.words = new ArrayList<Word>();
		if (word != null) {
			this.words.add(word);
		}
	}

	/**
	 * Constructor
	 * 
	 * @param words
	 *            A list of words in the row
	 */
	public Row(List<Word> words) {
		super();
		this.words = new ArrayList<Word>();
		if (words != null) {
			this.words.addAll(words);
		}
	}

	/**
	 * Connects a word to the end of the row
	 * 
	 * @param word
	 *            The word to connect
	 */
	public void connect(Word word) {
		words.add(word);
	}

	/**
	 * Connects a row to the end of the row
	 * 
	 * @param row
	 *            The row to connect
	 */
	public void connect(Row row) {
		words.addAll(row.getWords());
	}

	/**
	 * Connects a word to the start of the row
	 * 
	 * @param word
	 *            The word to connect
	 */
	public void connectToFront(Word word) {
		words.add(0, word);
	}

	/**
	 * Disconnects a word from the row if the word is in the row and an edge
	 * word
	 * 
	 * @param word
	 *            The word to disconnect
	 * @return Returns whether the disconnect was successful
	 */
	public boolean disconnect(Word word) {
		int index = words.indexOf(word);
		boolean successful = false;
		// If the word is first or last, remove it and we're done
		if (index == 0 || index == words.size() - 1) {
			successful = words.remove(word);
		}
		// Otherwise, disconnect failed so return false
		return successful;
	}

	/**
	 * Splits the row after the desired word and returns the resulting row or
	 * word Intended to be called if disconnect fails, then call disconnect
	 * again. The word to split at will remain part of this row
	 * 
	 * @param word
	 *            The word to split after
	 * @return Return the resulting Row or Word
	 */
	public Row splitRowAt(Word word) {
		int index = words.indexOf(word);
		List<Word> secondRowWords = new ArrayList<Word>(words.subList(
				index + 1, words.size()));
		words.removeAll(secondRowWords);
		Row result = new Row(secondRowWords);
		return result;
	}

	@Override
	public String getValue() {
		String rowContents = "";
		for (Word word : words) {
			rowContents += " " + word.getValue();
		}
		return rowContents.substring(1);
	}

	@Override
	public boolean contains(AbstractWord otherWord) {
		boolean isFound = this.equals(otherWord);
		for (Word word : words) {
			isFound = isFound || word.contains(otherWord);
		}
		return isFound;
	}

	/**
	 * Gets all the words in the row
	 * 
	 * @return Returns a list of words in the row
	 */
	public List<Word> getWords() {
		return words;
	}
}
