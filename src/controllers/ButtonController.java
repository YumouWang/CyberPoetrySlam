package controllers;

import common.Constants;
import models.GameState;
import models.Position;
import views.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Random;

/**
 * ButtonController handles all the actions for MainView panel buttons
 * <p/>
 * Created by Yumou & Jian on 10/28/2014.
 *
 * @author Yumou
 * @author Nathan
 * @version 10/28/2014
 */
public class ButtonController implements ActionListener {
    AbstractWordView publishPoem;
    private MainView mainView;
    private GameState gameState;

    /**
     * Constructor
     *
     * @param mainView  The mainView
     * @param gameState The gameState
     */
    public ButtonController(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.gameState = gameState;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        // click on publish button
        if (clickedButton.equals(mainView.getPublishButton())) {
            mainView.getUndoMoves().clear();
            mainView.getRedoMoves().clear();
            mainView.getUndoButton().setEnabled(false);
            mainView.getRedoButton().setEnabled(false);
            publishPoem = mainView.getMouseInputController()
                    .getLastSelectedWord();
            if (publishPoem instanceof PoemView
                    && mainView.getProtectedAreaWords().contains(publishPoem)) {

                publishPoem((PoemView) publishPoem, Constants.WALL_FILENAME);
            } else if (publishPoem instanceof RowView && mainView.getProtectedAreaWords().contains(publishPoem)) {
                publishPoem((RowView) publishPoem, Constants.WALL_FILENAME);

            }

            mainView.getPublishButton().setEnabled(false);
            publishPoem = null;
        }
        // click on Redo button
        if (clickedButton.equals(mainView.getRedoButton())) {
            // Handle redo
            new RedoController(this.mainView, this.gameState).process();
        }
        // click on Undo button
        if (clickedButton.equals(mainView.getUndoButton())) {
            // Handle Undo
            new UndoController(this.mainView, this.gameState).process();
        }
    }

    /**
     * publish a selected poem into wall.txt
     *
     * @param poemView The poem to publish
     */
    public void publishPoem(PoemView poemView, String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(file, true)));
            List<RowView> rowsInPoem = poemView.getRowViews();
            for (RowView row : rowsInPoem) {
                List<WordView> wordsInRow = row.getWordViews();
                for (WordView word : wordsInRow) {
                    bufferedWriter.write(word.getWord().getValue() + " ");
                }
                bufferedWriter.write("\r\n");
            }
            bufferedWriter.write("\r\n");
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // After publish poem to wall, release this poem
        releasePoem(poemView);
    }

    /**
     * publish a selected poem which is a single row into wall.txt
     *
     * @param rowView The poem to publish
     */
    public void publishPoem(RowView rowView, String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(file, true)));
            List<WordView> wordsInPoem = rowView.getWordViews();
            for (WordView word : wordsInPoem) {
                bufferedWriter.write(word.getWord().getValue() + " ");
            }
            bufferedWriter.write("\r\n");
            bufferedWriter.write("\r\n");
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // After publish poem to wall, release this poem
        releaseRow(rowView);
    }

    /**
     * release the selected poem when it has been published
     *
     * @param poemView The poem to release
     */
    public void releasePoem(PoemView poemView) {
        // remove poem from protected abstractWord in GameState
        gameState.getProtectedArea().removeAbstractWord(poemView.getWord());
        // remove poem view from protected abstractWord view in MainView
        mainView.removeProtectedAbstractWordView(poemView);
        List<RowView> rows = poemView.getRowViews();
        for (RowView row : rows) {
            List<WordView> words = row.getWordViews();
            for (WordView word : words) {
                // for every word in this poem add it to the unprotected
                // abstractWord in GameState
                gameState.getUnprotectedArea().addAbstractWord(word.getWord());
                // for every word in this poem view add it to the unprotected
                // abstractWord view in MainView
                mainView.addUnprotectedAbstractWordView(word);
                Random random = new Random();
                int x = random.nextInt(Constants.AREA_WIDTH - 100);
                int y = random.nextInt(Constants.AREA_HEIGHT
                        - Constants.PROTECTED_AREA_HEIGHT - 20)
                        + Constants.PROTECTED_AREA_HEIGHT;
                word.moveTo(new Position(x, y));
            }
        }
        mainView.getExploreArea().updateTable();
    }

    /**
     * release the selected poem when it has been published
     *
     * @param rowView The poem to release
     */
    public void releaseRow(RowView rowView) {
        // remove poem from protected abstractWord in GameState
        gameState.getProtectedArea().removeAbstractWord(rowView.getWord());
        // remove poem view from protected abstractWord view in MainView
        mainView.removeProtectedAbstractWordView(rowView);
        List<WordView> words = rowView.getWordViews();

        for (WordView word : words) {
            // for every word in this poem add it to the unprotected
            // abstractWord in GameState
            gameState.getUnprotectedArea().addAbstractWord(word.getWord());
            // for every word in this poem view add it to the unprotected
            // abstractWord view in MainView
            mainView.addUnprotectedAbstractWordView(word);
            Random random = new Random();
            int x = random.nextInt(Constants.AREA_WIDTH - 100);
            int y = random.nextInt(Constants.AREA_HEIGHT
                    - Constants.PROTECTED_AREA_HEIGHT - 20)
                    + Constants.PROTECTED_AREA_HEIGHT;
            word.moveTo(new Position(x, y));
        }
        mainView.getExploreArea().updateTable();

    }
}
