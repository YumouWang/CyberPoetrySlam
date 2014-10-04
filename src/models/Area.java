package models;

import java.util.*;

/**
 * The Area model class
 *
 * Created by Nathan on 10/3/2014.
 */
public class Area extends Observable {

    Collection<AbstractWord> abstractWordCollection;

    public Area(Collection<AbstractWord> words) {
        this.abstractWordCollection = new HashSet<AbstractWord>();
        if(words != null) {
            this.abstractWordCollection.addAll(words);
        }
    }

    public AbstractWord connectHorizontal(AbstractWord wordOne, AbstractWord wordTwo) throws Exception {
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
        // This if block is just checking that the state is consistent. It should
        // never actually throw an exception
        if(result != null) {
            // Update the model to reflect the new connection
            // Adding and removing words from the areas should always work. If it doesn't,
            // That means that the model does not match what is being displayed on the screen
            // So they system is in an invalid state.
            boolean isInvalidState = false;
            // Remove the old words from the area
            if (!removeAbstractWord(wordOne))
                isInvalidState = true;
            if (!removeAbstractWord(wordTwo))
                isInvalidState = true;
            // Add the new word to the area, so the area views the row as one entity
            if (!addAbstractWord(result))
                isInvalidState = true;

            if (isInvalidState)
                throw new Exception("Invalid state detected! Model and view are inconsistent.");
        }
        return result;
    }

    public AbstractWord connectVertical(AbstractWord wordOne, AbstractWord wordTwo) throws Exception {
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
        // Result should only be null if either wordOne or wordTwo was a poem
        // This if block is just checking that the state is consistent. It should
        // never actually throw an exception
        if(result != null) {
            // Update the model to reflect the new connection
            // Adding and removing words from the areas should always work. If it doesn't,
            // That means that the model does not match what is being displayed on the screen
            // So they system is in an invalid state.
            boolean isInvalidState = false;
            // Remove the old words from the area
            if (!removeAbstractWord(wordOne))
                isInvalidState = true;
            if (!removeAbstractWord(wordTwo))
                isInvalidState = true;
            // Add the new word to the area, so the area views the row as one entity
            if (!addAbstractWord(result))
                isInvalidState = true;

            if (isInvalidState)
                throw new Exception("Invalid state detected! Model and view are inconsistent.");
        }
        return result;
    }

    public boolean addAbstractWord(AbstractWord word) {
        return abstractWordCollection.add(word);
    }

    public boolean removeAbstractWord(AbstractWord word) {
        return abstractWordCollection.remove(word);
    }

    public Collection<AbstractWord> getAbstractWordCollection() {
        return abstractWordCollection;
    }
}
