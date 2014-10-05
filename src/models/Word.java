package models;

import java.util.ArrayList;
import java.util.List;

/**
 * The model class for a word
 *
 * Created by Nathan on 10/2/2014.
 */
public class Word extends AbstractWord {

    String value;
    WordType type;

    /**
     * Constructor
     * @param value The value of the word
     * @param type The type of the word
     */
    public Word(String value, WordType type) {
        super();
        this.value = value;
        this.type = type;
    }

    @Override
    public String getValue() {
        return value;
    }

    /**
     * Connects a word to the right of this word
     * @param word The word to connect
     * @return Returns a row containing both words
     */
    public Row connect(Word word) {
        List<Word> words = new ArrayList<Word>();
        words.add(this);
        words.add(word);
        return new Row(words);
    }

    /**
     * Connects a row to the right of this word
     * @param row The row to connect
     * @return Returns the row with this word connected to the beginning
     */
    public Row connect(Row row) {
        row.connectToFront(this);
        return row;
    }
}
