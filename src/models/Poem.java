package models;

import java.util.ArrayList;
import java.util.List;

/**
 * The Poem model class
 *
 * Created by Nathan on 10/3/2014.

 */
public class Poem extends AbstractWord {

    List<Row> rows;

    /**
     * Constructor
     * @param row The only row in this poem
     */
    public Poem(Row row) {
        super();
        this.rows = new ArrayList<Row>();
        if(row != null) {
            this.rows.add(row);
        }
    }

    /**
     * Constructor
     * @param rows The rows in this poem
     */
    public Poem(List<Row> rows) {
        super();
        this.rows = new ArrayList<Row>();
        if(rows != null) {
            this.rows.addAll(rows);
        }
    }

    /**
     * Connects a row to the bottom of this poem
     * @param row The row to connect
     */
    public void connect(Row row) {
        rows.add(row);
    }

    /**
     * Connects a row to the top of this poem
     * @param row The row to connect
     */
    public void connectToTop(Row row) {
        rows.add(0, row);
    }

    /**
     * Connects a poem to the bottom of this poem
     * @param poem The poem to connect
     */
    public void connect(Poem poem) {
        rows.addAll(poem.getRows());
    }

    /**
     * Disconnects a row from this poem
     * @param row The row to disconnect
     * @return If the poem was split, returns the second poem. Otherwise, returns null
     */
    public Poem disconnect(Row row) {
        int index = rows.indexOf(row);
        Poem newPoem = null;
        // If the row is first or last, remove it and we're done
        if(index == 0 || index == rows.size() - 1) {
            rows.remove(row);
        } else {
            // Otherwise, we need to split the poem
            // Remove the row
            rows.remove(row);
            // Remove all rows after the specified row
            List<Row> newPoemRows = new ArrayList<Row>();
            while(index < rows.size()) {
                Row removedRow = rows.remove(index);
                newPoemRows.add(removedRow);
            }
            // Return those rows as a new poem
            newPoem = new Poem(newPoemRows);
        }
        return newPoem;
    }

    /**
     * Disconnects a word from one of the rows in this poem if the word is an edge word and is in the poem
     * @param word The word to disconnect
     * @return Returns whether the disconnect was successful. Returns false if the word is not an edge word or is not in the poem
     */
    public boolean disconnectEdgeWord(Word word) {
        boolean successful = false;
        for(Row row: rows) {
            for(Word rowWord: row.getWords()) {
                if(word.equals(rowWord)) {
                    successful = row.disconnect(rowWord);
                    break;
                }
            }
            if(successful) {
                break;
            }
        }
        return successful;
    }

    @Override
    public String getValue() {
        String poemContents = "";
        for(Row row: rows) {
            poemContents += "\n" + row.getValue();
        }
        return poemContents.substring(1);
    }

    /**
     * Gets all the rows in the poem
     * @return Returns a list of rows in the poem
     */
    public List<Row> getRows() {
        return rows;
    }
}
