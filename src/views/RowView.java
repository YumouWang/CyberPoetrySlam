package views;

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
     * @param row     The word that this view represents
     * @param position The position of this word
     */
    public RowView(Row row, Position position, MainView mainView) {
        super(row, position);
        List<Word> words = row.getWords();
        wordViews = new ArrayList<WordView>();
        // Find all wordViews, and calculate total Width and height
        int totalWidth = 0;
        int tallestHeight = 0;
        for(Word word: words) {
            WordView view = (WordView) mainView.getAbstractWordById(word.getId());

            totalWidth += view.width;
            if(view.height > tallestHeight) {
                tallestHeight = view.height;
            }
            wordViews.add(view);
        }
        setSize(totalWidth, tallestHeight);

        // Move all words to the appropriate locations
        int currentWidthOffset = 0;
        for(WordView view: wordViews) {
            view.moveTo(new Position(position.getX() + currentWidthOffset, position.getY()));
            currentWidthOffset += view.width;
        }
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
}
