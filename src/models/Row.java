package models;

import java.util.ArrayList;
import java.util.List;

/**
 * The model row class
 *
 * Created by Nathan on 10/2/2014.
 */
public class Row extends AbstractWord {

    List<Word> words;

    /**
     * Constructor
     * @param words A list of words in the row
     */
    public Row(List<Word> words) {
        super();
        this.words = new ArrayList<Word>();
        if(words != null) {
            this.words.addAll(words);
        }
    }

    /**
     * Connects a word to the end of the row
     * @param word The word to connect
     */
    public void connect(Word word) {
        words.add(word);
    }

    /**
     * Connects a row to the end of the row
     * @param row The row to connect
     */
    public void connect(Row row) {
        words.addAll(row.getWords());
    }

    /**
     * Connects a word to the start of the row
     * @param word The word to connect
     */
    public void connectToFront(Word word) {
        words.add(0, word);
    }

    /**
     * Disconnects a word from the row if the word is in the row and an edge word
     * @param word The word to disconnect
     * @return Returns whether the disconnect was successful
     */
    public boolean disconnect(Word word) {
        int index = words.indexOf(word);
        boolean successful = false;
        // If the word is first or last, remove it and we're done
        if(index == 0 || index == words.size() - 1) {
            successful = words.remove(word);
        }
        return successful;
    }

    @Override
    public String getValue() {
        String rowContents = "";
        for(Word word: words) {
            rowContents += " " + word.getValue();
        }
        return rowContents.substring(1);
    }

    /**
     * Gets all the words in the row
     * @return Returns a list of words in the row
     */
    public List<Word> getWords() {
        return words;
    }

	@Override
	public WordType getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
