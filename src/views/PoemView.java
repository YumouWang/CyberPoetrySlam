package views;

import models.Poem;
import models.Position;
import models.Row;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A view to represent the poem model
 * Created by Nathan on 10/10/2014.
 */
public class PoemView extends AbstractWordView {

    List<RowView> rowViews;

    /**
     * Constructor
     *
     * @param poem     The poem that this view represents
     * @param position The position of this poem
     */
    public PoemView(Poem poem, Position position, MainView mainView) {
        super(poem, position);
        List<Row> rows = poem.getRows();
        rowViews = new ArrayList<RowView>();

        // Find all wordViews
        for(Row row: rows) {
            // Find the rowView representing each row
            RowView view = (RowView) mainView.getAbstractWordById(row.getId());
            if(view == null) {
                // If a rowView doesn't exist for this row,
                // then create one at the position of the first word in the row
                long id = row.getWords().get(0).getId();
                Position rowPosition = mainView.getAbstractWordById(id).getPosition();
                view = new RowView(row, rowPosition, mainView);
            }
            rowViews.add(view);
        }

        calculateDimensions();
        moveTo(position);
    }

    /**
     * Moves the word to the specified position
     * @param toPosition The position to move to
     * @return Returns whether the move was successful
     */
    public boolean moveTo(Position toPosition) {
        super.position = toPosition;
        boolean successful = true;
        // Move all words to the appropriate locations
        int currentHeightOffset = 0;
        for(RowView view: rowViews) {
            successful = successful && view.moveTo(new Position(position.getX(), position.getY() + currentHeightOffset));
            currentHeightOffset += view.height;
        }
        return successful;
    }

    /**
     * Sets the background color of this word view
     * @param color The color to set the background to
     */
    public void setBackground(Color color) {
        for(RowView view : rowViews) {
            view.setBackground(color);
        }
    }

    public void addRow(RowView rowView) {
        rowViews.add(rowView);
        calculateDimensions();
    }

    public void addRowToTop(RowView rowView) {
        rowViews.add(0, rowView);
        calculateDimensions();
    }

    public List<RowView> getRowViews() {
        return rowViews;
    }

    private void calculateDimensions() {
        // Find all wordViews, and calculate total Width and height
        int totalHeight = 0;
        int widest = 0;
        for(RowView row: rowViews) {
            totalHeight += row.height;
            if(row.width > widest) {
                widest = row.width;
            }
        }
        setSize(widest, totalHeight);
    }
}
