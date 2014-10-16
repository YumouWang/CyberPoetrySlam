package controllers;

import models.Area;
import models.GameState;
import models.Row;
import models.Word;
import views.MainView;
import views.PoemView;
import views.RowView;
import views.WordView;

/**
 * A realization of the AbstractWordViewVisitor interface for disconnecting two words
 *
 * Created by Nathan on 10/15/2014.
 */
public class DisconnectVisitor implements AbstractWordViewVisitor {

    MainView mainView;
    Area protectedArea;

    /**
     * Constructor
     * @param mainView The MainView to update once a connection has been made
     * @param gameState The GameState to perform connections on
     */
    public DisconnectVisitor(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.protectedArea = gameState.getProtectedArea();
    }

    @Override
    public void visit(WordView wordViewToDisconnectFrom, WordView wordView) {
        // Cannot disconnect a word from another word, so do nothing
    }

    @Override
    public void visit(WordView wordViewToDisconnectFrom, RowView rowView) {
        // Cannot disconnect a row from a word, so do nothing
    }

    @Override
    public void visit(WordView wordViewToDisconnectFrom, PoemView poemView) {
        // Cannot disconnect a poem from a word, so do nothing
    }

    @Override
    public void visit(RowView rowViewToDisconnectFrom, WordView wordView) {
        Row rowToDisconnectFrom = rowViewToDisconnectFrom.getWord();
        Word word = wordView.getWord();

        // Disconnect the two entities
        if(rowToDisconnectFrom.disconnect(word)) {

            // Update the area to reflect the updated words
            protectedArea.addAbstractWord(word);

            // If the row is now just one word, convert it to a word
            if(rowToDisconnectFrom.getWords().size() == 1) {
                Word wordFromRow = rowToDisconnectFrom.getWords().get(0);
                // And then make the appropriate change in the protected area
                protectedArea.removeAbstractWord(rowToDisconnectFrom);
                protectedArea.addAbstractWord(wordFromRow);
            }
        } else {
            System.out.println("Error disconnecting " + word + " from " + rowToDisconnectFrom.getValue());
        }

        // Disconnect the wordView so the view reflects the entity change
        if(rowViewToDisconnectFrom.removeWordView(wordView)) {

            // Update the main view object to reflect the change
            mainView.addProtectedAbstractWordView(wordView);

            // If the row is now just one word, convert it to a word
            if(rowToDisconnectFrom.getWords().size() == 1) {
                WordView wordViewFromRow = rowViewToDisconnectFrom.getWordViews().get(0);
                // And then make the appropriate change in the protected area
                mainView.removeProtectedAbstractWordView(rowViewToDisconnectFrom);
                mainView.addProtectedAbstractWordView(wordViewFromRow);
            }
        } else {
            System.out.println("Error disconnecting view for " + word + " from view for " + rowToDisconnectFrom.getValue());
        }
//        // Move the poem to the appropriate position
//        // This also updates the positions of all the words in the poem
//        resultPoemView.moveTo(wordViewOne.getPosition());
    }

    @Override
    public void visit(RowView rowViewToDisconnectFrom, RowView rowView) {
        // Cannot disconnect a row from a row, so do nothing
    }

    @Override
    public void visit(RowView rowViewToDisconnectFrom, PoemView poemView) {
        // Cannot disconnect a poem from a row, so do nothing
    }

    @Override
    public void visit(PoemView poemViewToDisconnectFrom, WordView wordView) {
        System.out.println("Disconnect " + wordView.getWord().getValue() + " from " + poemViewToDisconnectFrom.getWord().getValue());
    }

    @Override
    public void visit(PoemView poemViewToDisconnectFrom, RowView rowView) {
        System.out.println("Disconnect " + rowView.getWord().getValue() + " from " + poemViewToDisconnectFrom.getWord().getValue());
    }

    @Override
    public void visit(PoemView poemViewToDisconnectFrom, PoemView poemView) {
        // Cannot disconnect a poem from a poem, so do nothing
    }
}
