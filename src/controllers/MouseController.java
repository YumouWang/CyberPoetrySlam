package controllers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;

import models.AbstractWord;
import models.Area;
import models.GameState;
import models.Position;
import views.AbstractWordView;
import views.AdjacencyType;
import views.MainGUI;

public class MouseController implements MouseListener, MouseMotionListener {

	AbstractWordView selectedWord;
	Position mouseDownPosition;
	MainGUI mainGUI;
	GameState gameState;
	Area unprotectedArea;
	Area protectedArea;

	public MouseController(MainGUI view, GameState gameState) {
		this.mainGUI = view;
		this.gameState = gameState;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDownPosition = new Position(e.getX(), e.getY());
		selectedWord = null;
		if (isInProtectArea(mouseDownPosition)) {
			Collection<AbstractWordView> protectedWords = mainGUI
					.getProtectedAreaWords();
			for (AbstractWordView word : protectedWords) {
				if (word.isClicked(mouseDownPosition)) {
					selectedWord = word;
					System.out.println("clickword:"
							+ selectedWord.getWord().getValue()
							+ ", this is a protected word");
					break;
				}
			}
		}

		else {
			Collection<AbstractWordView> unprotectedWords = mainGUI
					.getUnprotectedAreaWords();
			for (AbstractWordView word : unprotectedWords) {
				if (word.isClicked(mouseDownPosition)) {
					selectedWord = word;
					System.out.println("clickword:"
							+ selectedWord.getWord().getValue()
							+ ", this is a unprotected word");
					break;
				}
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (selectedWord != null) {
			Position originPosition = new Position(selectedWord.getPosition()
					.getX(), selectedWord.getPosition().getY());
			Position positionDiff = new Position(e.getX()
					- mouseDownPosition.getX(), e.getY()
					- mouseDownPosition.getY());
			Position newPosition = new Position(selectedWord.getPosition()
					.getX() + positionDiff.getX(), selectedWord.getPosition()
					.getY() + positionDiff.getY());
			selectedWord.moveTo(newPosition);

			if (isInProtectArea(originPosition) && isInProtectArea(newPosition)) { // word
																					// move
																					// from
																					// protect
																					// area
																					// to
																					// protect
																					// area;
				protectAreaWordMove();
			}

			if (!isInProtectArea(originPosition)
					&& isInProtectArea(newPosition)) { // word move from
														// unprotect area to
														// protect area;
				System.out.println("you are protecting word:"
						+ selectedWord.getWord().getValue());
				protectWord(selectedWord);
			}

			if (isInProtectArea(originPosition)
					&& !isInProtectArea(newPosition)) { // word move from
														// protect area to
														// unprotect area;
				System.out.println("you are unprotecting word:"
						+ selectedWord.getWord().getValue());
				unprotectWord(selectedWord);
			}

		}
		mouseDownPosition = new Position(e.getX(), e.getY());
	}

	public void protectAreaWordMove() {
		boolean isOverlapping = false;
		boolean isAdjacent = false;
		Collection<AbstractWordView> words = mainGUI.getProtectedAreaWords();
		for (AbstractWordView word : words) {
			if (!word.equals(selectedWord)) {
				if (word.isOverlapping(selectedWord)) {
					isOverlapping = true;
				}
				AdjacencyType adjacencyType = selectedWord.isAdjacentTo(word);
				if (adjacencyType != AdjacencyType.NOT_ADJACENT) {
					isAdjacent = true;
					word.setBackground(Color.GREEN);
				} else {
					word.setBackground(Color.LIGHT_GRAY);
				}
			}
		}
		if (isOverlapping) {
			selectedWord.setBackground(Color.RED);
		} else if (isAdjacent) {
			selectedWord.setBackground(Color.GREEN);
		} else {
			selectedWord.setBackground(Color.LIGHT_GRAY);
		}
	}

	public void protectWord(AbstractWordView wordView) { // protect word
		gameState.protect(wordView.getWord());	//add word to protect word list
		mainGUI.addProtectedWordView(wordView);	//add word view to protect word view list
		Collection<AbstractWord> protectedWords = gameState.getProtectedArea()
				.getAbstractWordCollection();
		System.out.print("protectWord list: ");
		for (AbstractWord word : protectedWords) {
			System.out.print(word.getValue() + ",");
		}
		System.out.println();
		Collection<AbstractWord> unprotectedWords = gameState.getUnprotectedArea()
				.getAbstractWordCollection();
		System.out.print("unprotectWord list: ");
		for (AbstractWord word : unprotectedWords) {
			System.out.print(word.getValue() + ",");
		}
		System.out.println();
	}

	public void unprotectWord(AbstractWordView wordView) { // unprotect word
		gameState.unprotect(wordView.getWord());	//add word to unprotect word list
		mainGUI.addUnprotectedWordView(wordView);	//add word view to unprotect word view list
		System.out.print("unprotectWord list: ");
		Collection<AbstractWord> unprotectedWords = gameState
				.getUnprotectedArea().getAbstractWordCollection();
		for (AbstractWord word : unprotectedWords) {
			System.out.print(word.getValue() + ",");
		}
		System.out.println();
		Collection<AbstractWord> protectedWords = gameState.getProtectedArea()
				.getAbstractWordCollection();
		System.out.print("protectWord list: ");
		for (AbstractWord word : protectedWords) {
			System.out.print(word.getValue() + ",");
		}
		System.out.println();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	public void removeProtectedWords(AbstractWordView word) {
		mainGUI.getProtectedAreaWords().remove(word);
	}

	public void removeUnprotectedWords(AbstractWordView word) {
		mainGUI.getUnprotectedAreaWords().remove(word);
	}

	public boolean isInProtectArea(Position position) {
		if (position.getY() < 237) {
			return true;
		}
		return false;
	}
}
