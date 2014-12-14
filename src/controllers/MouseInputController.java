package controllers;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.List;

import models.GameState;
import models.Position;
import views.*;


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

	UndoWithMemento undo;
	
    AbstractWordView selectedWord;
    AbstractWordView selectedWordToDisconnect;
    AbstractWordView selectedWordCopy;	
	AbstractWordView lastSelectedWord;
	AbstractWordView selectedRowToShift;
	static boolean isShift = false;
	boolean isShifting = false;
    
    Position mouseDownPosition;
    Position selectedWordPositionRelativeToMouse;
    Position disconnectTargetPosition;
    
    
    /** Original x,y where AbstractWordView was before moving and connecting. */
    int originalx;
    int originaly;
    long Id;
    
    MainView mainView;
    GameState gameState;
    
    /** isDisconnect and isConnect is used to represent whether disconnect/connect happens */
    boolean isDisconnect;
    boolean isConnect;
    boolean isReleaseOrProtect;
    boolean isMove;

    /**
     * Constructor
     * @param mainView The view to update when handling mouse events
     * @param gameState The gameState to enact changes on
     */
    public MouseInputController(MainView mainView, GameState gameState) {
        this.mainView = mainView;
        this.gameState = gameState;      
    }

	@Override
	public void mousePressed(MouseEvent e) {
		Position mousePosition = new Position(e.getX(), e.getY());
		isShift = e.isShiftDown();
        this.originalx = e.getX();
        this.originaly = e.getY();
		mousePressedHandler(mousePosition, isShift);
		mainView.refresh();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Position mousePosition = new Position(e.getX(), e.getY());
		isShift = e.isShiftDown();
		mouseDraggedHandler(mousePosition, isShift);
		mainView.refresh();
	}

    @Override
    public void mouseReleased(MouseEvent e) {
        Position mousePosition = new Position(e.getX(), e.getY());
        mouseReleasedHandler(mousePosition);
        mainView.refresh();
    }

    /**
     * determine what words to select
     * @param position
     * @param isShift
     */
    void mousePressedHandler(Position position, boolean isShift) {
    		undo = new UndoWithMemento(this.mainView, this.gameState);
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
				isShifting = true;
			}
			if (!isShift) {
				// Disconnect the word
				disconnectTargetPosition = selectedWord.getPosition();
				DisconnectController controller = new DisconnectController(
						mainView, gameState);
				if (controller.disconnect(selectedWordToDisconnect,
						selectedWord)) {					
					selectedWord.setBackground(Color.LIGHT_GRAY);
	                selectedWordCopy = (AbstractWordView) selectedWord.clone();                
	                selectedWord = selectedWordToDisconnect;
	                selectedWord.setBackground(Color.LIGHT_GRAY.brighter());
	                this.isDisconnect = true;
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

	/**
	 * Handles mouse dragged events and delegates to the appropriate controller
	 * @param mousePosition The position of the mouse when this event fires
	 * @param isShift Whether shift is pressed
	 */
	void mouseDraggedHandler(Position mousePosition, boolean isShift) {

		if (selectedWord != null) {
			MoveWordController moveController = new MoveWordController(
					mainView, gameState);

			if (isShift && isShifting) {
				ShiftRowController shiftController = new ShiftRowController(
						mainView, gameState);
				shiftController.shiftRow((PoemView) selectedWord,
						selectedRowToShift, mousePosition, mouseDownPosition);
			}

			else {
				moveController.moveWord(selectedWord, selectedWord
						.getPosition(), new Position(mousePosition.getX()
						+ selectedWordPositionRelativeToMouse.getX(),
						mousePosition.getY()
								+ selectedWordPositionRelativeToMouse.getY()));
				this.isMove = true;
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

	/**
	 * Handles mouse released events and delegates to the appropriate controller
	 * @param mousePosition The position of the mouse when this event fired
	 */
	void mouseReleasedHandler(Position mousePosition) {
		isShifting = false;
		if (selectedWord != null && mainView.isInProtectedArea(mousePosition)) {
			lastSelectedWord = selectedWord;
			if (lastSelectedWord instanceof PoemView || lastSelectedWord instanceof RowView) {
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
				ConnectController controller = new ConnectController(mainView,
						gameState);
				this.isConnect = true;
				controller.connect(selectedWord, connectTarget);// connectTarget


			}			
		}

		if(isMove || isDisconnect || isConnect || isShift){
			mainView.recordUndoMove(undo);
			mainView.refresh();
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
		this.isShift = false;
		this.isDisconnect = false;
		this.isConnect = false;
		this.isMove = false;
	}

	/**
	 * returns last selected word
	 * @return AbstractWordView
	 */
	public AbstractWordView getLastSelectedWord() {
		return lastSelectedWord;
	}
}
