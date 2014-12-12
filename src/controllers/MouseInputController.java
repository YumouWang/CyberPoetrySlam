package controllers;


import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.List;


import models.GameState;
import models.Position;
import views.AbstractWordView;
import views.AdjacencyType;
import views.MainView;
import views.PoemView;
import views.RowView;
import views.WordView;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

/**
 * A controller for handling mouse input. Delegates to other controllers. Unlike
 * other controllers, this controller exists for the duration of the program to
 * handle mouse events and maintain state between mouse events.
 * 
 * @author Nathan
 * @author YangWang
 * @author Jian
 * @version 10/4/2014
 */
public class MouseInputController extends MouseAdapter {

	AbstractWordView selectedWord;
	AbstractWordView selectedWordToDisconnect;
	Position mouseDownPosition;
	Position selectedWordPositionRelativeToMouse;
	MainView mainView;
	GameState gameState;
	AbstractWordView lastSelectedWord;
	AbstractWordView selectedRowToShift;

	/**
	 * Constructor
	 * 
	 * @param mainView
	 *            The view to update when handling mouse events
	 * @param gameState
	 *            The gameState to enact changes on
	 */
	public MouseInputController(MainView mainView, GameState gameState) {
		this.mainView = mainView;
		this.gameState = gameState;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Position mousePosition = new Position(e.getX(), e.getY());
		mousePressedHandler(mousePosition, e.isShiftDown());
		mainView.refresh();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Position mousePosition = new Position(e.getX(), e.getY());
		mouseDraggedHandler(mousePosition, e.isShiftDown());
		mainView.refresh();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Position mousePosition = new Position(e.getX(), e.getY());
		mouseReleasedHandler(mousePosition);
		mainView.refresh();
	}

	void mousePressedHandler(Position position, boolean isShift) {
		mouseDownPosition = position;

		Collection<AbstractWordView> words;
		// If it's in the protected area, select a word from the protectedArea
		if (mainView.isInProtectedArea(mouseDownPosition)) {
			words = mainView.getProtectedAreaWords();
		} else {
			// Otherwise, get a word from the unprotectedArea
			words = mainView.getUnprotectedAreaWords();
		}
		// Select the word from the selection
		for (AbstractWordView word : words) {
			if (word.isClicked(mouseDownPosition)) {
				selectedWord = word;
				selectedWord.setBackground(Color.LIGHT_GRAY.brighter());
				break;
			}
		}
		
		if (selectedWord != null
				&& selectedWord.contains(selectedWordToDisconnect)
				&& selectedWordToDisconnect.isClicked(mouseDownPosition)) {
			if (isShift) {
				selectedRowToShift = selectedWordToDisconnect;
			}
			if (!isShift) {
				// Disconnect the word
				DisconnectController controller = new DisconnectController(
						mainView, gameState);
				if (controller.disconnect(selectedWordToDisconnect,
						selectedWord)) {
					selectedWord.setBackground(Color.LIGHT_GRAY);
					selectedWord = selectedWordToDisconnect;
					selectedWord.setBackground(Color.LIGHT_GRAY.brighter());
				}
			}
		}

		if (selectedWord == null) {
			mainView.getSelectionBox().startNewSelection(mouseDownPosition);
			selectedWordPositionRelativeToMouse = null;
		} else {
			// Determine the location of the word relative to the mouse when the
			// word was selected
			selectedWordPositionRelativeToMouse = new Position(selectedWord
					.getPosition().getX() - position.getX(), selectedWord
					.getPosition().getY() - position.getY());
		}

		selectedWordToDisconnect = null;
	}

	void mouseDraggedHandler(Position mousePosition, boolean isShift) {
		if(selectedWord !=null && selectedWordToDisconnect == null 
				&& isShift) {
			return;
		}
		
		if (selectedWord != null) {
			MoveWordController moveController = new MoveWordController(
					mainView, gameState);

			if (isShift) {
				ShiftRowController shiftController = 
						new ShiftRowController(mainView, gameState);
				shiftController.shiftRow((PoemView) selectedWord,selectedRowToShift,
						mousePosition,mouseDownPosition);
				//((PoemView) selectedWord).shiftRow(selectedRowToShift,
						//mousePosition.getX() - mouseDownPosition.getX());
			}

			else {
				moveController.moveWord(selectedWord, selectedWord
						.getPosition(), new Position(mousePosition.getX()
						+ selectedWordPositionRelativeToMouse.getX(),
						mousePosition.getY()
								+ selectedWordPositionRelativeToMouse.getY()));
			}
		}
		mouseDownPosition = mousePosition;

		if (selectedWord == null) {
			mainView.getSelectionBox().moveSelection(mouseDownPosition);
			// Highlight selected item, un-highlight unselected items
			if (selectedWordToDisconnect != null) {
				for (AbstractWordView view : mainView.getProtectedAreaWords()) {
					view.setBackground(Color.LIGHT_GRAY);
				}
			}
			selectedWordToDisconnect = mainView.getSelectionBox()
					.getSelectedItem(mainView.getProtectedAreaWords());

			if (selectedWordToDisconnect != null) {
				selectedWordToDisconnect.setBackground(Color.LIGHT_GRAY
						.brighter());
			}
		}
	}

	void mouseReleasedHandler(Position mousePosition) {
		if (selectedWord != null && mainView.isInProtectedArea(mousePosition)) {
			lastSelectedWord = selectedWord;
			if (lastSelectedWord instanceof PoemView) {
				mainView.getPublishButton().setEnabled(true);
			} else {
				mainView.getPublishButton().setEnabled(false);
			}
			Collection<AbstractWordView> words = mainView
					.getProtectedAreaWords();
			AbstractWordView connectTarget = null;
			for (AbstractWordView word : words) {
				if (!word.equals(selectedWord)) {
					AdjacencyType adjacencyType = selectedWord
							.isAdjacentTo(word);
					if (adjacencyType != AdjacencyType.NOT_ADJACENT) {
						connectTarget = word;
						break;
					}
				}
			}
			if (connectTarget != null) {
				ConnectController controller = new ConnectController(mainView, gameState);
				controller.connect(selectedWord, connectTarget);
			}
		}
		if (selectedWord == null) {
			selectedWordToDisconnect = mainView.getSelectionBox()
					.getSelectedItem(mainView.getProtectedAreaWords());
			if (selectedWordToDisconnect != null) {
				selectedWordToDisconnect.setBackground(Color.LIGHT_GRAY
						.brighter());
			}
			mainView.getSelectionBox().clearBox();
		} else {
			selectedWord.setBackground(Color.LIGHT_GRAY);
			selectedWord = null;
			for (AbstractWordView view : mainView.getProtectedAreaWords()) {
				view.setBackground(Color.LIGHT_GRAY);
			}
		}
	}

	public AbstractWordView getLastSelectedWord() {
		return lastSelectedWord;
	}
}
