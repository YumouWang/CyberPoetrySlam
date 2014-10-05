package models;

import java.util.*;

/**
 * The Area model class
 *
 * Created by Nathan on 10/3/2014.
 */
public class Area extends Observable {

    Collection<AbstractWord> abstractWordCollection;

    /**
     * Constructor
     * @param words The collection of words to start in this area
     */
    public Area(Collection<AbstractWord> words) {
        this.abstractWordCollection = new HashSet<AbstractWord>();
        if(words != null) {
            this.abstractWordCollection.addAll(words);
        }
    }

    /**
     * Connects a word in this area with another word in this area horizontally
     * @param wordOne The word on the left
     * @param wordTwo The word on the right
     * @return Returns the connected word
     */
    public AbstractWord connectHorizontal(AbstractWord wordOne, AbstractWord wordTwo) {
        AbstractWord result = null;

        // Check that the move is valid before we do it
        if(abstractWordCollection.contains(wordOne) && abstractWordCollection.contains(wordTwo)) {
            // Identify the types of the words so we can call the appropriate connect method
            // Method currently does not handle connecting poems
            if (wordOne instanceof Word) {
                Word leftWord = (Word) wordOne;
                if (wordTwo instanceof Word) {
                    result = leftWord.connect((Word) wordTwo);
                } else if (wordTwo instanceof Row) {
                    result = leftWord.connect((Row) wordTwo);
                }
            } else if (wordOne instanceof Row) {
                Row leftRow = (Row) wordOne;
                if (wordTwo instanceof Word) {
                    leftRow.connect((Word) wordTwo);
                    result = leftRow;
                } else if (wordTwo instanceof Row) {
                    leftRow.connect((Row) wordTwo);
                    result = leftRow;
                }
            }
        }
        // Result should only be null if either wordOne or wordTwo was a poem
        if(result != null) {
            // Remove the old words from the area
            removeAbstractWord(wordOne);
            removeAbstractWord(wordTwo);
            // Add the new word to the area, so the area views the row as one entity
            addAbstractWord(result);
        }
        return result;
    }

    /**
     * Connects a word in this area with another word in this area vertically
     * @param wordOne The word on the top
     * @param wordTwo The word on the bottom
     * @return Returns the connected word
     */
    public AbstractWord connectVertical(AbstractWord wordOne, AbstractWord wordTwo) {
        AbstractWord result = null;

        // Check that the move is valid before we do it
        if(abstractWordCollection.contains(wordOne) && abstractWordCollection.contains(wordTwo)) {
            // Identify the types of the words so we can call the appropriate connect method
            Poem topPoem;
            // Convert the first word into a poem
            if (wordOne instanceof Word) {
                List<Word> wordList = new ArrayList<Word>();
                wordList.add((Word) wordOne);
                Row row = new Row(wordList);
                List<Row> rowList = new ArrayList<Row>();
                rowList.add(row);
                topPoem = new Poem(rowList);
            } else if (wordOne instanceof Row) {
                List<Row> rowList = new ArrayList<Row>();
                rowList.add((Row) wordOne);
                topPoem = new Poem(rowList);
            } else {
                topPoem = (Poem) wordOne;
            }
            // Connect the words
            if(wordTwo instanceof Word) {
                List<Word> wordList = new ArrayList<Word>();
                wordList.add((Word) wordTwo);
                Row row = new Row(wordList);
                topPoem.connect(row);
            } else if(wordTwo instanceof Row) {
                topPoem.connect((Row) wordTwo);
            } else {
                topPoem.connect((Poem) wordTwo);
            }
            result = topPoem;
        }
        // Result should only be null if the words don't exist in this area
        if(result != null) {
            // Update the model to reflect the new connection
            removeAbstractWord(wordOne);
            removeAbstractWord(wordTwo);
            // Add the new word to the area, so the area views the poem as one entity
            addAbstractWord(result);
        }
        return result;
    }

    /**
     * Adds a word to this area
     * @param word The word to add to the area
     * @return Returns whether the word was added successfully
     */
    public boolean addAbstractWord(AbstractWord word) {
        return abstractWordCollection.add(word);
    }

    /**
     * Removes a word from this area
     * @param word The word to remove from the area
     * @return Returns whether the word was removed successfully
     */
    public boolean removeAbstractWord(AbstractWord word) {
        return abstractWordCollection.remove(word);
    }

    /**
     * Gets the collection of words in this area
     * @return Returns the collection of words
     */
    public Collection<AbstractWord> getAbstractWordCollection() {
        return abstractWordCollection;
    }
}
