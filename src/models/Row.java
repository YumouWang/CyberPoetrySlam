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

    public Row(List<Word> words) {
        super();
        this.words = new ArrayList<Word>();
        if(words != null) {
            this.words.addAll(words);
        }
    }

    public void connect(Word word) {
        words.add(word);
    }

    public void connect(Row row) {
        words.addAll(row.getWords());
    }

    public void connectToFront(Word word) {
        words.add(0, word);
    }

    public void disconnect(Word word) throws Exception {
        int index = words.indexOf(word);
        // If the word is first or last, remove it and we're done
        if(index == 0 || index == words.size() - 1) {
            words.remove(word);
        } else {
            throw new Exception("Invalid disconnect");
        }
    }

    @Override
    public String getValue() {
        String rowContents = "";
        for(Word word: words) {
            rowContents += " " + word.getValue();
        }
        return rowContents.substring(1);
    }

    public List<Word> getWords() {
        return words;
    }
}
