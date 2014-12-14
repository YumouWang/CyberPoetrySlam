package controllers.swap;

import common.Constants;
import models.*;
import views.MainView;
import views.WordView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Interface for the rest of the program to communicate with the broker
 * @author Nathan
 * @version 11/30/2014
 */
public class SwapController {

    MainView mainView;
    GameState gameState;
    BrokerConnection connection;

    /**
     * Constructs a new swap controller
     * @param mainView The current MainView
     * @param gameState The current GameState
     */
    public SwapController(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.gameState = gameState;
        // Get the broker connection
        try {
            this.connection = BrokerConnectionController.getConnection(mainView, gameState);
        } catch (ConnectionException e) {
            mainView.getSwapAreaView().disable();
        }
    }

    /**
     * Creates a swap request using the information from the MainView and delegates
     * to the BrokerConnection to send it
     */
    public void requestSwap() {
        if(connection == null) { return; }
        mainView.getSwapAreaView().swapPending();
        ArrayList<String> giveTypes = new ArrayList<String>();
        ArrayList<String> giveWords = new ArrayList<String>();
        ArrayList<String> getTypes = new ArrayList<String>();
        ArrayList<String> getWords = new ArrayList<String>();

        ArrayList<ArrayList<JComponent>> inputElements = mainView.getSwapAreaView().getInputElements();

        for(ArrayList<JComponent> swapGroup : inputElements) {
            JCheckBox checkBox = (JCheckBox) swapGroup.get(0);
            if(checkBox.isSelected()) {
                JTextField giveWordInput = (JTextField) swapGroup.get(1);
                JComboBox<WordType> giveTypeInput = (JComboBox<WordType>) swapGroup.get(2);
                JTextField getWordInput = (JTextField) swapGroup.get(3);
                JComboBox<WordType> getTypeInput = (JComboBox<WordType>) swapGroup.get(4);

                String giveWord = giveWordInput.getText();
                String getWord = getWordInput.getText();
                String giveType = giveTypeInput.getSelectedItem().toString();
                String getType = getTypeInput.getSelectedItem().toString();
                giveWords.add(giveWord);
                giveTypes.add(giveType);
                getWords.add(getWord);
                getTypes.add(getType);
            }
        }

        try {
            Swap swap = new Swap(gameState, giveTypes, giveWords, getTypes, getWords, true, connection.getSessionID());
            gameState.getPendingSwaps().add(swap);
            removeSwapWords(swap);
            connection.sendSwapRequest(swap);
        } catch (InvalidSwapException e) {
            mainView.getSwapAreaView().swapInvalid();
        }
    }

    /**
     * Executes the Swap
     * @param swap The swap to execute
     */
    public void executeSwap(Swap swap) {
        List<String> newWords = swap.getTheirWords();
        List<WordType> newTypes = swap.getTheirTypes();

        for(int i = 0; i < newWords.size(); i++) {
            Word word = new Word(newWords.get(i), newTypes.get(i));
            gameState.getUnprotectedArea().addAbstractWord(word);
            Random random = new Random();
            // Randomly determine the position of the word in the unprotected area
            int x = random.nextInt(Constants.AREA_WIDTH - 100);
            int y = random.nextInt(Constants.AREA_HEIGHT
                    - Constants.PROTECTED_AREA_HEIGHT - 20)
                    + Constants.PROTECTED_AREA_HEIGHT;
            WordView wordView = new WordView(word, new Position(x, y));
            mainView.addLabelOf(wordView);
            mainView.addUnprotectedAbstractWordView(wordView);
        }
        mainView.refresh();
        mainView.getExploreArea().updateTable();
    }

    /**
     * Hides the words in the swap by removing them from the mainView and gameState
     */
    void removeSwapWords(Swap swap) {
        List<Word> oldWords = swap.getMyWords();
        for(Word myWord : oldWords) {
            gameState.getUnprotectedArea().removeAbstractWord(myWord);
            WordView wordView = (WordView)mainView.getUnprotectedAbstractWordById(myWord.getId());
            mainView.removeLabelOf(wordView);
            mainView.removeUnprotectedAbstractWordView(wordView);
        }
    }

    /**
     * Cancels the current swap and puts the words back in the mainView and gameState. Opposite of hideWords
     */
    public void cancelSwap(Swap swap) {
        if(swap != null && !swap.getIsCancelled()) {
            List<Word> myWords = swap.getMyWords();
            for(Word myWord : myWords) {
                gameState.getUnprotectedArea().addAbstractWord(myWord);
                Random random = new Random();
                // Randomly determine the position of the word in the unprotected area
                int x = random.nextInt(Constants.AREA_WIDTH - 100);
                int y = random.nextInt(Constants.AREA_HEIGHT
                        - Constants.PROTECTED_AREA_HEIGHT - 20)
                        + Constants.PROTECTED_AREA_HEIGHT;
                WordView wordView = new WordView(myWord, new Position(x, y));
                mainView.addLabelOf(wordView);
                mainView.addUnprotectedAbstractWordView(wordView);
            }
            swap.setIsCancelled(true);
            gameState.getPendingSwaps().remove(swap);
            mainView.refresh();
            mainView.getExploreArea().updateTable();
        }
    }

    /**
     * Cancels all the pending swaps
     */
    public void cancelPendingSwaps() {
        for(Swap swap: gameState.getPendingSwaps()) {
            cancelSwap(swap);
        }
    }
}
