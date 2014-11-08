package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;

import models.AbstractWord;
import models.GameState;
import models.Position;
import common.Constants;
import views.AbstractWordView;
import views.MainView;
import views.PoemView;
import views.RowView;
import views.WordView;

/**
 * ButtonController handles all the actions for MainView panel buttons
 * 
 * @author Yumou
 * @version 10/28/2014
 */
public class ButtonController implements ActionListener {
	private MainView mainView;
	private GameState gameState;
	AbstractWordView publishPoem;
	
	/**
	 * Constructor
	 * @param mainView
	 * @param gameState
	 */
	public ButtonController(MainView mainView, GameState gameState) {
		this.mainView = mainView;
		this.gameState = gameState;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		// click on Save button
		if (clickedButton.equals(mainView.getPublishButton())) {
			System.out.println("Publish...");
			publishPoem = mainView.getMouseInputController()
					.getLastSelectedWord();
			if (publishPoem instanceof PoemView
					&& mainView.getProtectedAreaWords().contains(publishPoem)) {
				System.out.println("select poem --> "
						+ publishPoem.getWord().getValue());
				publishPoem((PoemView) publishPoem);
			}
			publishPoem = null;
		}
		// click on Redo button
		if (clickedButton.equals(mainView.getRedoButton())) {
			// Handle redo
			System.out.println("Redo...");
		}
		// click on Undo button
		if (clickedButton.equals(mainView.getUndoButton())) {
			// Handle Undo
			System.out.println("Undo...");
		}
		if (clickedButton.equals(mainView.getSwapButton())) {
			// Handle Swap
			System.out.println("Swap...");
		}
	}
	
	/**
	 * publish a selected poem into wall.txt
	 * @param poemView
	 */
	public void publishPoem(PoemView poemView) {
		try {
			File file = new File("file/wall.txt");
			// writename.createNewFile();
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
		releasePoem((PoemView) poemView);
	}

	/**
	 * release the selected poem when it has been published
	 * @param poemView
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
		// print th result
		Collection<AbstractWord> protectedWords = gameState.getProtectedArea()
				.getAbstractWordCollection();
		System.out.print("protectWord list: ");
		for (AbstractWord word1 : protectedWords) {
			System.out.print(word1.getValue() + ",");
		}
		System.out.println();

		Collection<AbstractWord> unprotectedWords = gameState
				.getUnprotectedArea().getAbstractWordCollection();
		System.out.print("unprotectWord list: ");
		for (AbstractWord word2 : unprotectedWords) {
			System.out.print(word2.getValue() + ",");
		}
		System.out.println();

		Collection<AbstractWordView> protectedWordsView = mainView
				.getProtectedAreaWords();
		System.out.print("protectWordView list: ");
		for (AbstractWordView word : protectedWordsView) {
			System.out.print(word.getWord().getValue() + ",");
		}
		System.out.println();

		Collection<AbstractWordView> unprotectedWordsView = mainView
				.getUnprotectedAreaWords();
		System.out.print("unprotectWordView list: ");
		for (AbstractWordView word : unprotectedWordsView) {
			System.out.print(word.getWord().getValue() + ",");
		}
		System.out.println();
	}
}
