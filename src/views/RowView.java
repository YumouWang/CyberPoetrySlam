package views;

import controllers.AbstractWordViewVisitor;
import models.Position;
import models.Row;
import models.Word;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A view class representing a row
 *
 * @author Nathan
 * @version 10/9/2014
 */
public class RowView extends AbstractWordView implements Serializable, Cloneable {

    /**
     *
     */
    private static final long serialVersionUID = -2974299359963260887L;
    List<WordView> wordViews;

    /**
     * Constructor
     *
     * @param row      The row that this view represents
     * @param position The position of this row
     */
    public RowView(Row row, Position position, MainView mainView) {
        super(row, position);
        wordViews = new ArrayList<WordView>();
        List<Word> words = row.getWords();

        // Find all wordViews
        for (Word word : words) {
            WordView view = (WordView) mainView.getProtectedAbstractWordById(word.getId());
            wordViews.add(view);
        }

        calculateDimensions();
        moveTo(position);
    }

    /**
     * Constructor
     *
     * @param row       The row that this view represents
     * @param wordViews The wordViews in this row view
     * @param position  The position of this row
     */
    public RowView(Row row, List<WordView> wordViews, Position position) {
        super(row, position);
        this.wordViews = new ArrayList<WordView>();
        this.wordViews.addAll(wordViews);

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
        int currentWidthOffset = 0;
        for (WordView view : wordViews) {
            successful = successful
                    && view.moveTo(new Position(position.getX()
                    + currentWidthOffset, position.getY()));
            currentWidthOffset += view.width;
        }
        return successful;
    }

    /**
     * Sets the background color of this word view
     *
     * @param color The color to set the background to
     */
    public void setBackground(Color color) {
        for (WordView view : wordViews) {
            view.setBackground(color);
        }
    }

    public void addWord(WordView wordView) {
        wordViews.add(wordView);
        moveTo(wordViews.get(0).getPosition());
        calculateDimensions();
    }

    public void addWordToFront(WordView wordView) {
        wordViews.add(0, wordView);
        moveTo(wordViews.get(0).getPosition());
        calculateDimensions();
    }

    public void addRow(RowView rowView) {
        wordViews.addAll(rowView.getWordViews());
        moveTo(wordViews.get(0).getPosition());
        calculateDimensions();
    }

    public List<WordView> getWordViews() {
        return wordViews;
    }

    void calculateDimensions() {
        // Find all wordViews, and calculate total Width and height
        int totalWidth = 0;
        int tallestHeight = 0;
        for (WordView word : wordViews) {
            totalWidth += word.width;
            if (word.height > tallestHeight) {
                tallestHeight = word.height;
            }
        }
        if (0 < wordViews.size()) {
            position = wordViews.get(0).getPosition();
        }
        furthestRight = totalWidth;
        setSize(totalWidth, tallestHeight);
    }

    public AbstractWordView getSelectedElement(ConnectionBox box) {
        AbstractWordView selected = null;
        for (WordView word : wordViews) {
            AbstractWordView selectedElement = word.getSelectedElement(box);
            // If the connectionBox overlaps one of the words in the system
            if (selectedElement != null) {
                // If we have not yet selected a word in this row,
                // The selected item is the currently selected element
                if (selected == null) {
                    selected = selectedElement;
                } else {
                    // If we have already selected a word in this row,
                    // Select the entire row (Cannot select more than one word
                    // without selecting the whole row)
                    selected = this;
                    break;
                }
            }
        }
        return selected;
    }

    public boolean contains(AbstractWordView otherWord) {
        boolean containsWord = this.equals(otherWord);
        if (!containsWord) {
            for (WordView word : wordViews) {
                if (word.contains(otherWord)) {
                    containsWord = true;
                    break;
                }
            }
        }
        return containsWord;
    }

    public boolean removeWordView(WordView otherWord) {
        int index = wordViews.indexOf(otherWord);
        boolean successful = false;
        // If the word is first or last, remove it and we're done
        if (index == 0 || index == wordViews.size() - 1) {
            successful = wordViews.remove(otherWord);
        }
        calculateDimensions();
        return successful;
    }

    public boolean acceptVisitor(AbstractWordViewVisitor visitor,
                                 AbstractWordView otherView) {
        return otherView.acceptVisitor(visitor, this);
    }

    public boolean acceptVisitor(AbstractWordViewVisitor visitor,
                                 WordView wordView) {
        return visitor.visit(wordView, this);
    }

    public boolean acceptVisitor(AbstractWordViewVisitor visitor,
                                 RowView rowView) {
        return visitor.visit(rowView, this);
    }

    public boolean acceptVisitor(AbstractWordViewVisitor visitor,
                                 PoemView poemView) {
        return visitor.visit(poemView, this);
    }

    public Row getWord() {
        return (Row) word;
    }

    @Override
    public Object clone() {
        Row cloneRow = (Row) word.clone();
        List<WordView> cloneWordViews = new ArrayList<WordView>();
        for (int i = 0; i < wordViews.size(); i++) {
            WordView cloneWordView = new WordView(cloneRow.getWords().get(i), wordViews.get(i).getPosition());
            cloneWordViews.add(cloneWordView);
        }
        return new RowView(cloneRow, cloneWordViews, position);
    }
}
