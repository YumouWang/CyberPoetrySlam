package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Poem model class
 *
 * @author Nathan
 * @version 10/3/2014
 */
public class Poem extends AbstractWord implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6796779767893699320L;
    List<Row> rows;

    /**
     * Constructor
     *
     * @param row The only row in this poem
     */
    public Poem(Row row) {
        super();
        this.rows = new ArrayList<Row>();
        if (row != null) {
            this.rows.add(row);
        }
    }

    /**
     * Constructor
     *
     * @param rows The rows in this poem
     */
    public Poem(List<Row> rows) {
        super();
        this.rows = new ArrayList<Row>();
        if (rows != null) {
            this.rows.addAll(rows);
        }
    }

    /**
     * Connects a row to the bottom of this poem
     *
     * @param row The row to connect
     */
    public void connect(Row row) {
        rows.add(row);
    }

    /**
     * Connects a row to the top of this poem
     *
     * @param row The row to connect
     */
    public void connectToTop(Row row) {
        rows.add(0, row);
    }

    /**
     * Connects a poem to the bottom of this poem
     *
     * @param poem The poem to connect
     */
    public void connect(Poem poem) {
        rows.addAll(poem.getRows());
    }

    /**
     * Disconnects a row from this poem
     *
     * @param row The row to disconnect
     * @return Returns whether disconnect was successful
     */
    public boolean disconnect(Row row) {
        int index = rows.indexOf(row);
        boolean successful = false;
        // If the row is first or last, remove it and we're done
        if (index == 0 || index == rows.size() - 1) {
            successful = rows.remove(row);
        }
        return successful;
    }

    /**
     * Splits a poem at specific row if the row is in the poem
     *
     * @param row The row to split at
     * @return The new poem after split
     */
    public Poem splitPoemAt(Row row) {
        int index = rows.indexOf(row);
        Poem result;

        List<Row> secondPoemRows = new ArrayList<Row>(rows.subList(index + 1,
                rows.size()));
        rows.removeAll(secondPoemRows);

        result = new Poem(secondPoemRows);
        return result;
    }

    /**
     * Disconnects a word from one of the rows in this poem if the word is an
     * edge word and is in the poem
     *
     * @param word The word to disconnect
     * @return Returns whether the disconnect was successful. Returns false if
     * the word is not an edge word or is not in the poem
     */
    public boolean disconnectEdgeWord(Word word) {
        boolean successful = false;
        for (Row row : rows) {
            for (Word rowWord : row.getWords()) {
                if (word.equals(rowWord)) {
                    successful = row.disconnect(rowWord);
                    break;
                }
            }
            if (successful) {
                break;
            }
        }
        return successful;
    }

    /**
     * Checks the poem for any invalid rows (length 0) And splits based on the
     * rows if necessary. Should be called after every call to
     * disconnectEdgeWord
     *
     * @return The split poem, row, or word, if any. Null otherwise
     */
    public Poem revalidate() {
        Poem result = null;
        for (Row row : rows) {
            // Check if this row is invalid
            if (row.getWords().size() == 0) {
                int index = rows.indexOf(row);
                // If the empty row is in the middle of the poem, split it
                if (index != 0 && index != rows.size() - 1) {
                    result = splitPoemAt(row);
                }
                // Disconnect the invalid row
                disconnect(row);
                break;
            }
        }
        return result;
    }

    @Override
    public String getValue() {
        String poemContents = "";
        for (Row row : rows) {
            poemContents += "\n" + row.getValue();
        }
        return poemContents.substring(1);
    }

    @Override
    public boolean contains(AbstractWord otherWord) {
        boolean isFound = this.equals(otherWord);
        for (Row row : rows) {
            isFound = isFound || row.contains(otherWord);
        }
        return isFound;
    }

    /**
     * Gets all the rows in the poem
     *
     * @return Returns a list of rows in the poem
     */
    public List<Row> getRows() {
        return rows;
    }

    public Object clone() {
        List<Row> cloneRows = new ArrayList<Row>();
        for (Row row : rows) {
            cloneRows.add((Row) row.clone());
        }
        return new Poem(cloneRows);
    }
}
