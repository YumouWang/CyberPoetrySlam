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

    public Word(String value, WordType type) {
        super();
        this.value = value;
        this.type = type;
    }

    @Override
    public String getValue() {
        return value;
    }

    public Row connect(Word word) {
        List<Word> words = new ArrayList<Word>();
        words.add(this);
        words.add(word);
        return new Row(words);
    }

    public Row connect(Row row) {
        row.connectToFront(this);
        return row;
    }
}
