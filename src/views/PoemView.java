package views;

import controllers.AbstractWordViewVisitor;
import models.Poem;
import models.Position;
import models.Row;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A view to represent the poem model
 *
 * @author Nathan
 * @author YangWang
 * @version 10/10/2014
 */
public class PoemView extends AbstractWordView implements Serializable {

    /**
     *Serialized ID for a PoemVIew
     */
    private static final long serialVersionUID = -4290728328082346968L;
    /**
     * RowViews in a PoemView
     */
    List<RowView> rowViews;
    /**
     * Row offset of each row in a poem
     */
    List<Integer> rowoffset;

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
        rowoffset = new ArrayList<Integer>();

        // Find all wordViews
        for (Row row : rows) {
            // Find the rowView representing each row
            RowView view = (RowView) mainView.getProtectedAbstractWordById(row
                    .getId());
            if (view == null) {
                // If a rowView doesn't exist for this row,
                // then create one at the position of the first word in the row
                long id = row.getWords().get(0).getId();
                Position rowPosition = mainView
                        .getProtectedAbstractWordById(id).getPosition();
                view = new RowView(row, rowPosition, mainView);
            }
            rowViews.add(view);
            rowoffset.add(0);
        }

        calculateDimensions();
        moveTo(position);
    }

    /**
     * Constructor
     *
     * @param poem     The poem that this view represents
     * @param rowViews The rowViews in this poem view
     * @param position The position of this poem
     */
    public PoemView(Poem poem, List<RowView> rowViews, List<Integer> rowOffset, Position position) {
        super(poem, position);
        this.rowViews = new ArrayList<RowView>();
        this.rowViews.addAll(rowViews);
        this.rowoffset = new ArrayList<Integer>();
        this.rowoffset.addAll(rowOffset);

        calculateDimensions();
        moveTo(position);
    }

    /**
     * Moves the word to the specified position
     *
     * @param toPosition The position to move to
     * @return Returns whether the move was successful
     */
    public boolean moveTo(Position toPosition) {

        super.position = toPosition;
        boolean successful = true;
        // Move all words to the appropriate locations
        int currentHeightOffset = 0;
        int currentOffset;
        for (int i = 0; i < rowViews.size(); i++) {
            RowView view = rowViews.get(i);
            currentOffset = rowoffset.get(i);
            successful = successful
                    && view.moveTo(new Position(position.getX() + currentOffset, position
                    .getY() + currentHeightOffset));
            currentHeightOffset += view.height;
        }
        return successful;
    }

    /**
     * Sets the background color of this word view
     *
     * @param color The color to set the background to
     */
    public void setBackground(Color color) {
        for (RowView view : rowViews) {
            view.setBackground(color);
        }
    }
/**
 * Add a RowView to a PoemView
 * @param rowView
 */
    public void addRow(RowView rowView) {
        rowViews.add(rowView);
        rowoffset.add(0);
        moveTo(rowViews.get(0).getPosition());
        calculateDimensions();
    }
/**
 * Add a RowView to the top of a PoemView
 * @param rowView
 */
    public void addRowToTop(RowView rowView) {
        rowViews.add(0, rowView);
        rowoffset.add(0, 0);
        moveTo(rowViews.get(0).getPosition());
        calculateDimensions();
    }
/**
 * Add a PoemView to another PoemView
 * @param poemView
 */
    public void addPoem(PoemView poemView) {
        rowViews.addAll(poemView.getRowViews());
        rowoffset.addAll(poemView.getRowOffset());
        moveTo(rowViews.get(0).getPosition());
        calculateDimensions();
    }
/**
 * Remove a RowView from a PoemView and check whether it succeeds
 * @param otherRow
 * @return boolean
 */
    public boolean removeRowView(RowView otherRow) {
        int index = rowViews.indexOf(otherRow);
        boolean successful = false;
        // If the word is first or last, remove it and we're done
        if (index == 0 || index == rowViews.size() - 1) {
            successful = rowViews.remove(otherRow);
            rowoffset.remove(index);
            if (index == 0) {
                int offset = rowoffset.get(0);
                for (int i = 0; i < rowoffset.size(); i++) {
                    rowoffset.set(i, rowoffset.get(i) - offset);
                }
            }
        }
        calculateDimensions();
        return successful;
    }
/**
 * Return a row with no WordView inside
 * @return RowView
 */
    public RowView getEmptyRowView() {
        RowView result = null;
        for (RowView rowView : rowViews) {
            if (rowView.getWordViews().size() == 0) {
                result = rowView;
            }
        }
        return result;
    }

    /**
     * Disconnects a word from one of the rows in this poem if the word is an
     * edge word and is in the poem
     *
     * @param wordView The word to disconnect
     * @return Returns whether the disconnect was successful. Returns false if
     * the word is not an edge word or is not in the poem
     */
    public boolean removeEdgeWordView(WordView wordView) {
        boolean successful = false;
        for (RowView rowView : rowViews) {
            for (WordView rowWordView : rowView.getWordViews()) {
                if (wordView.equals(rowWordView)) {
                    boolean isFirst = (rowView.getWordViews().indexOf(rowWordView) == 0);
                    successful = rowView.removeWordView(rowWordView);
                    if (successful && isFirst && rowView.getWordViews().size() > 0) {
                        shiftRow(rowView, rowWordView.getWidth());
                    }
                    break;
                }
            }
            if (successful) {
                break;
            }
        }
        calculateDimensions();
        return successful;
    }
/**
 * Get a collection of all RowViews from a PoemVIew
 * @return List<RowView>
 */
    public List<RowView> getRowViews() {
        return rowViews;
    }
/**
 * Get a collection of all RowOffset of each RowView in a PoemView
 * @return List<Integer>
 */
    public List<Integer> getRowOffset() {
        return rowoffset;
    }
/**
 * Shift a RowView inside a PoemView by its offset
 * @param row
 * @param shiftoffset
 */
    public void shiftRow(AbstractWordView row, int shiftoffset) {
        int index = rowViews.indexOf(row);

        if (index != 0) {
            int offset = rowoffset.get(index);
            rowoffset.set(index, offset + shiftoffset);
            moveTo(position);
        } else {
            for (int i = 1; i < rowoffset.size(); i++) {
                rowoffset.set(i, rowoffset.get(i) - shiftoffset);
            }
            moveTo(new Position(position.getX() + shiftoffset, position.getY()));
        }

        calculateDimensions();
    }
/**
 * Calculate the dimensions of a PoemView
 */
    private void calculateDimensions() {
        // Find all wordViews, and calculate total Width and height
        if (rowViews.size() < 1) {
            setSize(0, 0);
            return;
        }
        position = rowViews.get(0).getPosition();
        int totalHeight = 0;
        int widest;
        int min = getPosition().getX();
        int max = getPosition().getX() + rowViews.get(0).getWidth();
        for (RowView row : rowViews) {
            if (row.getPosition().getX() < min) {
                min = row.getPosition().getX();
            }
            if (row.getPosition().getX() + row.getWidth() > max) {
                max = row.getPosition().getX() + row.getWidth();
            }
        }
        widest = max - min;
        for (RowView row : rowViews) {
            totalHeight += row.height;
        }

        setSize(widest, totalHeight);

        int left = 0;
        int right = 0;
        for (int i = 0; i < rowViews.size(); i++) {
            int leftOffset = rowoffset.get(i);
            int rightOffset = rowoffset.get(i) + rowViews.get(i).getWidth();
            if (leftOffset < left) {
                left = leftOffset;
            }
            if (rightOffset > right) {
                right = rightOffset;
            }
        }
        furthestRight = right;
        furthestLeft = left;
    }
/**
 * Get the element from a ConnectionBox created by mouse
 * @param box
 * @return AbstractWordView
 */
    public AbstractWordView getSelectedElement(ConnectionBox box) {
        AbstractWordView selected = null;
        for (RowView row : rowViews) {
            AbstractWordView selectedElement = row.getSelectedElement(box);
            if (selectedElement != null) {
                // If we have not yet selected a word or row in this poem,
                // The selected item is the currently selected element
                if (selected == null) {
                    selected = selectedElement;
                } else {
                    // If we have already selected a word or row from this poem,
                    // Select the entire poem (Cannot select more than one word
                    // without selecting the whole poem)
                    selected = this;
                    break;
                }
            }
        }
        return selected;
    }
/**
 * Check whether one PoemView contains another AbstractWordView and return its value
 * @param otherWord
 * @return boolean
 */
    public boolean contains(AbstractWordView otherWord) {
        boolean containsWord = this.equals(otherWord);
        if (!containsWord) {
            for (RowView row : rowViews) {
                if (row.contains(otherWord)) {
                    containsWord = true;
                    break;
                }
            }
        }
        return containsWord;
    }
/**
 * Handles connecting and disconnecting one AbstractWordView to a PoemView
 * @param visitor,otherView
 * @return boolean
 */
    public boolean acceptVisitor(AbstractWordViewVisitor visitor,
                                 AbstractWordView otherView) {
        return otherView.acceptVisitor(visitor, this);
    }
    /**
     * Handles connecting and disconnecting one WordView to a PoemView
     * @param visitor,wordView
     * @return boolean
     */
    public boolean acceptVisitor(AbstractWordViewVisitor visitor,
                                 WordView wordView) {
        return visitor.visit(wordView, this);
    }
    /**
     * Handles connecting and disconnecting one RowView to a PoemView
     * @param visitor,rowView
     * @return boolean
     */
    public boolean acceptVisitor(AbstractWordViewVisitor visitor,
                                 RowView rowView) {
        return visitor.visit(rowView, this);
    }
    /**
     * Handles connecting and disconnecting one PoemView to a PoemView
     * @param visitor,poemView
     * @return boolean
     */
    public boolean acceptVisitor(AbstractWordViewVisitor visitor,
                                 PoemView poemView) {
        return visitor.visit(poemView, this);
    }
/**
 * Get the Poem from the PoemView
 * @return Poem
 */
    public Poem getWord() {
        return (Poem) word;
    }

    @Override
    public Object clone() {
        Poem clonePoem = (Poem) word.clone();
        List<RowView> cloneRowViews = new ArrayList<RowView>();
        for (int j = 0; j < rowViews.size(); j++) {
            List<WordView> cloneWordViews = new ArrayList<WordView>();
            for (int i = 0; i < rowViews.get(j).getWordViews().size(); i++) {
                WordView cloneWordView = new WordView(clonePoem.getRows().get(j).getWords().get(i), rowViews.get(j).getWordViews().get(i).getPosition());
                cloneWordViews.add(cloneWordView);
            }
            cloneRowViews.add(new RowView(clonePoem.getRows().get(j), cloneWordViews, rowViews.get(j).getPosition()));
        }
        return new PoemView(clonePoem, cloneRowViews, rowoffset, position);
    }


    /**
     * Determines whether this view object is overlapping a given view
     *
     * @param otherView The other view
     * @return Returns whether this view overlaps with the other view
     */
    @Override
    public boolean isOverlapping(AbstractView otherView) {
        boolean isOverlapping = false;

        for (RowView rowView : rowViews) {
            isOverlapping = isOverlapping || rowView.isOverlapping(otherView);
        }
        return isOverlapping;
    }

    /**
     * Checks if this view is adjacent to a given other word. Distance that
     * determines whether a word is adjacent is defined in
     * Constants.CONNECT_DISTANCE
     *
     * @param otherWord The word to check adjacency
     * @return Returns the type of adjacency (NOT_ADJACENT, ABOVE, BELOW, LEFT,
     * or RIGHT)
     */
    public AdjacencyType isAdjacentTo(AbstractWordView otherWord) {
        AdjacencyType returnType = AdjacencyType.NOT_ADJACENT;

        for (int i = 0; i < rowViews.size(); i++) {
            AdjacencyType rowAdjacency = rowViews.get(i).isAdjacentTo(otherWord);
            if (rowAdjacency == AdjacencyType.LEFT || rowAdjacency == AdjacencyType.RIGHT ||
                    (rowAdjacency == AdjacencyType.ABOVE && i == rowViews.size() - 1) ||
                    (rowAdjacency == AdjacencyType.BELOW && i == 0)) {
                returnType = rowAdjacency;
                break;
            }
        }
        return returnType;
    }

    /**
     * Determines whether a position is inside of this view object
     *
     * @param click The position of the click
     * @return Returns whether this view object was clicked
     */
    @Override
    public boolean isClicked(Position click) {
        boolean isClicked = false;

        for (RowView rowView : rowViews) {
            isClicked = isClicked || rowView.isClicked(click);
        }
        return isClicked;

    }
}
