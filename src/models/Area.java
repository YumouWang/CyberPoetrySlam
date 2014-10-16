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
