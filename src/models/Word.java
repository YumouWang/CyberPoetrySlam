package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The model class for a word
 *
 * @author Nathan
 * @version 10/2/2014
 */
public class Word extends AbstractWord implements Serializable, Cloneable {

    /**
     *Serialized ID for a Word
     */
    private static final long serialVersionUID = 1040298162920646098L;
    /**
     * Attributes of a Word
     */
    String value;
    WordType type;

    /**
     * Constructor
     *
     * @param value The value of the word
     * @param type  The type of the word
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

    @Override
    public boolean contains(AbstractWord otherWord) {
        return this.equals(otherWord);
    }

    /**
     * Get the wordtype
     *
     * @return The wordtype of a Word
     */
    public WordType getType() {
        return type;
    }

    /**
     * Connects a word to the right of this word
     *
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
     *
     * @param row The row to connect
     * @return Returns the row with this word connected to the beginning
     */
    public Row connect(Row row) {
        row.connectToFront(this);
        return row;
    }

    @Override
    public Object clone() {
        return new Word(value, type);
    }
}
