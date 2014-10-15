package models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Observable;

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
    public Row connectHorizontal(AbstractWord wordOne, AbstractWord wordTwo) {
        Row result = null;

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
    public Poem connectVertical(AbstractWord wordOne, AbstractWord wordTwo) {
        Poem result = null;

        // Check that the move is valid before we do it
        if(abstractWordCollection.contains(wordOne) && abstractWordCollection.contains(wordTwo)) {
            // Identify the types of the words so we can call the appropriate connect method
            Poem topPoem;
            if(wordOne instanceof Poem) {
                // The first word is a poem, so cast it to a poem and then call the appropriate connect
                topPoem = (Poem) wordOne;
                // Connect the words
                if(wordTwo instanceof Word) {
                    Row row = new Row((Word) wordTwo);
                    topPoem.connect(row);
                } else if(wordTwo instanceof Row) {
                    topPoem.connect((Row) wordTwo);
                } else {
                    topPoem.connect((Poem) wordTwo);
                }
            } else if(wordTwo instanceof Poem) {
                topPoem = (Poem) wordTwo;
                // The second word is a poem, so convert the first word to a row and then call the appropriate connect
                if (wordOne instanceof Word) {
                    Row row = new Row((Word) wordOne);
                    topPoem.connectToTop(row);
                } else {
                    topPoem.connectToTop((Row) wordOne);
                }
            } else {
                // Neither input is a poem, so convert the first input to a poem and call the appropriate connect
                if (wordOne instanceof Word) {
                    Row row = new Row((Word) wordOne);
                    topPoem = new Poem(row);
                } else {
                    topPoem = new Poem((Row) wordOne);
                }
                // Connect the words
                if(wordTwo instanceof Word) {
                    Row row = new Row((Word) wordTwo);
                    topPoem.connect(row);
                } else {
                    topPoem.connect((Row) wordTwo);
                }
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

    public AbstractWord disconnect(AbstractWord wordToDisconnect, AbstractWord from) {
        boolean successful = true;

        Word word = (Word) wordToDisconnect;
        AbstractWord result = from;
        if(from instanceof Poem) {
            Poem fromPoem = (Poem) from;
            successful = fromPoem.disconnectEdgeWord(word);
            // If the poem is now one row, downsize it to a row
            if(fromPoem.getRows().size() == 1) {
                Row resultRow = fromPoem.getRows().get(0);
                // If the resultRow is one word, downsize it to a regular word
                if(resultRow.getWords().size() == 1) {
                    result = resultRow.getWords().get(0);
                } else {
                    result = resultRow;
                }
            }
        } else if(from instanceof Row) {
            Row fromRow = (Row) from;
            successful = fromRow.disconnect(word);
            // If the row is now one word, downsize it to a regular word
            if(fromRow.getWords().size() == 1) {
                result = fromRow.getWords().get(0);
            }
        }
        // Otherwise, from is the word, so we don't need to disconnect it.

        if(successful) {
            // Update the model to reflect the disconnect
            addAbstractWord(wordToDisconnect);
            System.out.println("Disconnected " + wordToDisconnect.getValue() + " from " + from.getValue() + " and got " + result.getValue());
            // Remove the old Poem
            removeAbstractWord(from);
            // Add the resulting poem to the area
            // This may be the same as from, but it might also not be the same
            addAbstractWord(result);
        } else {
            result = null;
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
