package controllers;

import models.*;
import views.*;

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
    public boolean visit(WordView wordViewToDisconnectFrom, WordView wordView) {
        // Cannot disconnect a word from another word, so do nothing
        return false;
    }

    @Override
    public boolean visit(WordView wordViewToDisconnectFrom, RowView rowView) {
        // Cannot disconnect a row from a word, so do nothing
        return false;
    }

    @Override
    public boolean visit(WordView wordViewToDisconnectFrom, PoemView poemView) {
        // Cannot disconnect a poem from a word, so do nothing
        return false;
    }

    @Override
    public boolean visit(RowView rowViewToDisconnectFrom, WordView wordView) {
        Row rowToDisconnectFrom = rowViewToDisconnectFrom.getWord();
        Word word = wordView.getWord();
        boolean successful = true;

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
            // To add functionality where we can remove a word from the middle of a row, put it here
            // Use the row.splitRowAt() function
            System.out.println("Error disconnecting " + word.getValue() + " from " + rowToDisconnectFrom.getValue());
            successful = false;
        }

        // Disconnect the wordView so the view reflects the entity change
        if(rowViewToDisconnectFrom.removeWordView(wordView)) {

            // Update the main view object to reflect the change
            mainView.addProtectedAbstractWordView(wordView);

            // If the row is now just one word, convert it to a word
            if(rowViewToDisconnectFrom.getWordViews().size() == 1) {
                WordView wordViewFromRow = rowViewToDisconnectFrom.getWordViews().get(0);
                // And then make the appropriate change in the protected area
                mainView.removeProtectedAbstractWordView(rowViewToDisconnectFrom);
                mainView.addProtectedAbstractWordView(wordViewFromRow);
            }
        } else {
            System.out.println("Error disconnecting view for " + word.getValue() + " from view for " + rowToDisconnectFrom.getValue());
            successful = false;
        }
        // Move the row to the appropriate position
        // This also updates the positions of all the words in the row
        rowViewToDisconnectFrom.moveTo(rowViewToDisconnectFrom.getWordViews().get(0).getPosition());
        return successful;
    }

    @Override
    public boolean visit(RowView rowViewToDisconnectFrom, RowView rowView) {
        // Cannot disconnect a row from a row, so do nothing
        return false;
    }

    @Override
    public boolean visit(RowView rowViewToDisconnectFrom, PoemView poemView) {
        // Cannot disconnect a poem from a row, so do nothing
        return false;
    }

    @Override
    public boolean visit(PoemView poemViewToDisconnectFrom, WordView wordView) {
        System.out.println("Disconnect " + wordView.getWord().getValue() + " from " + poemViewToDisconnectFrom.getWord().getValue());
//        Poem poemToDisconnectFrom = poemViewToDisconnectFrom.getWord();
//        Word word = wordView.getWord();
//        boolean successful = true;
//        AbstractWord splitResult = null;
//
//        // Disconnect the two entities
//        if(poemToDisconnectFrom.disconnectEdgeWord(word)) {
//
//            // Update the area to reflect the updated words
//            protectedArea.addAbstractWord(word);
//
//            // Check if we removed the last word in a row
//            splitResult = poemToDisconnectFrom.revalidate();
//            // If we did, we might have also split the poem. If so,
//            // Add the result to the protectedArea
//            if(splitResult != null) {
//                protectedArea.addAbstractWord(splitResult);
//            }
//
//            // If the poem is now just one row, convert it to a row
//            if(poemToDisconnectFrom.getRows().size() == 1) {
//                Row rowFromPoem = poemToDisconnectFrom.getRows().get(0);
//                // And then make the appropriate change in the protected area
//                protectedArea.removeAbstractWord(poemToDisconnectFrom);
//                protectedArea.addAbstractWord(rowFromPoem);
//            }
//        } else {
//            // To add functionality where we can remove a word from the middle of a poem, put it here
//            // Use the poem.splitPoemAt() and the row.splitRowAt() functions
//            System.out.println("Error disconnecting " + word.getValue() + " from " + poemToDisconnectFrom.getValue());
//            successful = false;
//        }

//        // Disconnect the two entities
//        if(poemViewToDisconnectFrom.removeEdgeWordView(wordView)) {
//
//            // Update the main view object to reflect the change
//            mainView.addProtectedAbstractWordView(wordView);
//
//            // If we split the poem, create two new poems based on the poemToDisconnectFrom and the splitResult
//            if(splitResult != null) {
//                System.out.println("We split the poem. This isn't implemented yet");
////                mainView.addProtectedAbstractWordView(splitResult);
//            } else if(poemViewToDisconnectFrom.getRowViews().size() == 1) {
//                // If the poem is now just one row, convert it to a row
//                RowView rowViewFromPoem = poemViewToDisconnectFrom.getRowViews().get(0);
//                // And then make the appropriate change in the protected area
//                mainView.removeProtectedAbstractWordView(poemViewToDisconnectFrom);
//                mainView.addProtectedAbstractWordView(rowViewFromPoem);
//            }
//        } else {
//            // To add functionality where we can remove a wordView from the middle of a poem, put it here
//            // Use the poem.splitPoemAt() and the row.splitRowAt() functions
//            System.out.println("Error disconnecting " + word.getValue() + " from " + poemToDisconnectFrom.getValue());
//            successful = false;
//        }

        return false;
    }

    @Override
    public boolean visit(PoemView poemViewToDisconnectFrom, RowView rowView) {
        Poem poemToDisconnectFrom = poemViewToDisconnectFrom.getWord();
        Row row = rowView.getWord();
        boolean successful = true;

        // Disconnect the two entities
        if(poemToDisconnectFrom.disconnect(row)) {

            // Update the area to reflect the updated words
            protectedArea.addAbstractWord(row);

            // If the row is now just one word, convert it to a word
            if(poemToDisconnectFrom.getRows().size() == 1) {
                Row rowFromPoem = poemToDisconnectFrom.getRows().get(0);
                // And then make the appropriate change in the protected area
                protectedArea.removeAbstractWord(poemToDisconnectFrom);
                protectedArea.addAbstractWord(rowFromPoem);
            }
        } else {
            // To add functionality where we can remove a row from the middle of a poem, put it here
            // Use the poem.splitPoemAt() function
            System.out.println("Error disconnecting " + row.getValue() + " from " + poemToDisconnectFrom.getValue());
            successful = false;
        }

        // Disconnect the wordView so the view reflects the entity change
        if(poemViewToDisconnectFrom.removeRowView(rowView)) {

            // Update the main view object to reflect the change
            mainView.addProtectedAbstractWordView(rowView);

            // If the row is now just one word, convert it to a word
            if(poemViewToDisconnectFrom.getRowViews().size() == 1) {
                RowView rowViewFromPoem = poemViewToDisconnectFrom.getRowViews().get(0);
                // And then make the appropriate change in the protected area
                mainView.removeProtectedAbstractWordView(poemViewToDisconnectFrom);
                mainView.addProtectedAbstractWordView(rowViewFromPoem);
            }
        } else {
            System.out.println("Error disconnecting view for " + row.getValue() + " from view for " + poemToDisconnectFrom.getValue());
            successful = false;
        }
        // Move the row to the appropriate position
        // This also updates the positions of all the words in the row
        poemViewToDisconnectFrom.moveTo(poemViewToDisconnectFrom.getRowViews().get(0).getPosition());
        return successful;
    }

    @Override
    public boolean visit(PoemView poemViewToDisconnectFrom, PoemView poemView) {
        // Cannot disconnect a poem from a poem, so do nothing
        return false;
    }
}
