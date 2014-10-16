package views;

import controllers.AbstractWordViewVisitor;
import models.Position;
import models.Row;
import models.Word;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A view class representing a row
 * Created by Nathan on 10/9/2014.
 */
public class RowView extends AbstractWordView {

    List<WordView> wordViews;

    /**
     * Constructor
     *
     * @param row     The row that this view represents
     * @param position The position of this row
     */
    public RowView(Row row, Position position, MainView mainView) {
        super(row, position);
        List<Word> words = row.getWords();
        wordViews = new ArrayList<WordView>();

        // Find all wordViews
        for(Word word: words) {
            WordView view = (WordView) mainView.getAbstractWordById(word.getId());
            wordViews.add(view);
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
        int currentWidthOffset = 0;
        for(WordView view: wordViews) {
            successful = successful && view.moveTo(new Position(position.getX() + currentWidthOffset, position.getY()));
            currentWidthOffset += view.width;
        }
        return successful;
    }

    /**
     * Sets the background color of this word view
     * @param color The color to set the background to
     */
    public void setBackground(Color color) {
        for(WordView view : wordViews) {
            view.setBackground(color);
        }
    }

    public void addWord(WordView wordView) {
        wordViews.add(wordView);
        calculateDimensions();
    }

    public void addWordToFront(WordView wordView) {
        wordViews.add(0, wordView);
        calculateDimensions();
    }

    public void addRow(RowView rowView) {
        wordViews.addAll(rowView.getWordViews());
        calculateDimensions();
    }

    public List<WordView> getWordViews() {
        return wordViews;
    }

    private void calculateDimensions() {
        // Find all wordViews, and calculate total Width and height
        int totalWidth = 0;
        int tallestHeight = 0;
        for(WordView word: wordViews) {
            totalWidth += word.width;
            if(word.height > tallestHeight) {
                tallestHeight = word.height;
            }
        }
        setSize(totalWidth, tallestHeight);
    }

    public AbstractWordView getSelectedElement(ConnectionBox box) {
        AbstractWordView selected = null;
        for(WordView word : wordViews) {
            AbstractWordView selectedElement = word.getSelectedElement(box);
            if(selectedElement != null) {
                selected = selectedElement;
            }
        }
        return selected;
    }

    public boolean contains(AbstractWordView otherWord) {
        boolean containsWord = this.equals(otherWord);
        if(!containsWord) {
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
        if(index == 0 || index == wordViews.size() - 1) {
            successful = wordViews.remove(otherWord);
        }
        calculateDimensions();
        return successful;
    }

    public void acceptVisitor(AbstractWordViewVisitor visitor, AbstractWordView otherView) {
        otherView.acceptVisitor(visitor, this);
    }

    public void acceptVisitor(AbstractWordViewVisitor visitor, WordView wordView) {
        visitor.visit(wordView, this);
    }

    public void acceptVisitor(AbstractWordViewVisitor visitor, RowView rowView) {
        visitor.visit(rowView, this);
    }

    public void acceptVisitor(AbstractWordViewVisitor visitor, PoemView poemView) {
        visitor.visit(poemView, this);
    }

    public Row getWord() {
        return (Row) word;
    }
}
