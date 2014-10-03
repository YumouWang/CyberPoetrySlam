package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Poem model class
 *
 * Created by Nathan on 10/3/2014.

 */
public class Poem extends AbstractWord {

    List<Row> rows;

    public Poem(List<Row> rows) {
        super();
        this.rows = new ArrayList<Row>();
        if(rows != null) {
            this.rows.addAll(rows);
        }
    }

    public void connect(Row row) {
        rows.add(row);
    }

    public void connect(Poem poem) {
        rows.addAll(poem.getRows());
    }

    public Poem disconnect(Row row) {
        int index = rows.indexOf(row);
        // If the row is first or last, remove it and we're done
        if(index == 0 || index == rows.size() - 1) {
            rows.remove(row);
            return null;
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
            return new Poem(newPoemRows);
        }
    }

    public boolean disconnectEdgeWord(Word word) throws Exception {
        boolean successful = false;
        for(Row row: rows) {
            for(Word rowWord: row.getWords()) {
                if(word.equals(rowWord)) {
                    row.disconnect(rowWord);
                    successful = true;
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

    public List<Row> getRows() {
        return rows;
    }
}
