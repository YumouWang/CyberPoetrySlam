package controllers;

import views.AbstractWordView;
import views.MainView;
import views.PoemView;
import views.RowView;
import models.GameState;
import models.Position;

public class UndoShiftRow extends UndoMove{
	
	MainView mainView;
	GameState gameState;
	ShiftRowController shiftRowController;
	PoemView selectedPoem;
	AbstractWordView selectedRow;
	Position positionFrom;
	Position positionTo;
	
	public UndoShiftRow(MainView mainView, GameState gameState,PoemView selectedPoem, 
			AbstractWordView selectedRow,Position positionFrom, Position positionTo) {
		this.mainView = mainView;
		this.gameState = gameState;
		shiftRowController = new ShiftRowController(mainView, gameState);
		this.selectedPoem = selectedPoem;
		this.selectedRow = selectedRow;
		this.positionFrom = positionFrom;
		this.positionTo = positionTo;
	}

	@Override
	public boolean execute() {
		if (selectedRow == null || selectedRow instanceof PoemView) {
			return false;
		}

		int shiftAmount = positionFrom.getX() - positionTo.getX();
		RowView theRow = null;

		for (RowView row : selectedPoem.getRowViews()) {
			if (row.contains(selectedRow)) {
				shiftRowTemp(selectedPoem, row, -shiftAmount);
				theRow = row;
			}
		}

		boolean isPoemDisConnected = false;
		int length = selectedPoem.getRowViews().size();
		int index = selectedPoem.getRowViews().indexOf(theRow);
		if (index == 0) {
			if (!theRow.isOverlapping(selectedPoem.getRowViews().get(1)))
				isPoemDisConnected = true;
			else
				isPoemDisConnected = false;
		} else if (index == selectedPoem.getRowViews().size() - 1) {
			if (!theRow.isOverlapping(selectedPoem.getRowViews()
					.get(length - 2)))
				isPoemDisConnected = true;
			else
				isPoemDisConnected = false;
		} else {
			if (!theRow
					.isOverlapping(selectedPoem.getRowViews().get(index - 1))
					|| !theRow.isOverlapping(selectedPoem.getRowViews().get(
							index + 1)))
				isPoemDisConnected = true;
			else
				isPoemDisConnected = false;
		}

		if (isPoemDisConnected) {
			shiftRowTemp(selectedPoem, theRow, shiftAmount);
		}

		boolean isOverlappingOtherWord = false;
		for (AbstractWordView otherView : mainView.getProtectedAreaWords()) {
			if (!theRow.equals(otherView) && !selectedPoem.contains(otherView)
					&& theRow.isOverlapping(otherView)) {
				isOverlappingOtherWord = true;
			}
		}
		System.out.println(isOverlappingOtherWord);
		if (isOverlappingOtherWord) {
			shiftRowTemp(selectedPoem, theRow, shiftAmount);
		}

		boolean isOutOfBounds = false;
		if (mainView.isMoveOutOfBounds(theRow, theRow.getPosition())) {
			isOutOfBounds = true;
		}

		if (isOutOfBounds) {
			shiftRowTemp(selectedPoem, theRow, shiftAmount);
		}
		return true;
	}

	@Override
	public boolean undo() {
		System.out.println(selectedPoem.getWord().getValue());
		System.out.println(selectedRow.getWord().getValue());
		System.out.println(selectedRow.getPosition().getX());
		if (selectedRow == null || selectedRow instanceof PoemView) {
			return false;
		}

		int shiftAmount = positionFrom.getX() - positionTo.getX();
		RowView theRow = null;

		for (RowView row : selectedPoem.getRowViews()) {
			if (row.contains(selectedRow)) {

				shiftRowTemp(selectedPoem, row, shiftAmount);
				theRow = row;
			}
		}
		System.out.println(theRow.getWord().getValue());
		boolean isPoemDisConnected = false;
		int length = selectedPoem.getRowViews().size();
		int index = selectedPoem.getRowViews().indexOf(theRow);
		if (index == 0) {
			if (!theRow.isOverlapping(selectedPoem.getRowViews().get(1)))
				isPoemDisConnected = true;
			else
				isPoemDisConnected = false;
		} else if (index == selectedPoem.getRowViews().size() - 1) {
			if (!theRow.isOverlapping(selectedPoem.getRowViews()
					.get(length - 2)))
				isPoemDisConnected = true;
			else
				isPoemDisConnected = false;
		} else {
			if (!theRow
					.isOverlapping(selectedPoem.getRowViews().get(index - 1))
					|| !theRow.isOverlapping(selectedPoem.getRowViews().get(
							index + 1)))
				isPoemDisConnected = true;
			else
				isPoemDisConnected = false;
		}

		if (isPoemDisConnected) {
			shiftRowTemp(selectedPoem, theRow, -shiftAmount);
		}

		boolean isOverlappingOtherWord = false;
		for (AbstractWordView otherView : mainView.getProtectedAreaWords()) {
			if (!theRow.equals(otherView) && !selectedPoem.contains(otherView)
					&& theRow.isOverlapping(otherView)) {
				isOverlappingOtherWord = true;
			}
		}
		System.out.println(isOverlappingOtherWord);
		if (isOverlappingOtherWord) {
			shiftRowTemp(selectedPoem, theRow, -shiftAmount);
		}

		boolean isOutOfBounds = false;
		if (mainView.isMoveOutOfBounds(theRow, theRow.getPosition())) {
			isOutOfBounds = true;
		}

		if (isOutOfBounds) {
			shiftRowTemp(selectedPoem, theRow, -shiftAmount);
		}
		System.out.println(selectedRow.getPosition().getX());
		return true;
	}

	
	public void shiftRowTemp(PoemView selectedPoem,
			AbstractWordView selectedRow, int shiftAmount) {
		if (selectedRow == null || selectedRow instanceof PoemView) {
			return;
		}
		selectedPoem.shiftRow(selectedRow, shiftAmount);
	}
}
