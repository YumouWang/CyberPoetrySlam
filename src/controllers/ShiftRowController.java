package controllers;

import java.util.Collection;
import java.util.List;

import views.AbstractWordView;
import views.AdjacencyType;
import views.MainView;
import views.PoemView;
import views.RowView;
import views.WordView;
import common.Constants;
import controllers.MoveWordController;
import models.GameState;
import models.Position;

public class ShiftRowController {
	MainView mainView;
	GameState gameState;

	public ShiftRowController(MainView mainView, GameState gameState) {
		this.mainView = mainView;
		this.gameState = gameState;
	}

	public void shiftRow(PoemView selectedPoem, AbstractWordView selectedRow,
			Position positionFrom, Position positionTo) {
		if (selectedRow == null || selectedRow instanceof PoemView) {
			return;
		}

		int shiftAmount = positionFrom.getX() - positionTo.getX();
		RowView theRow = null;

		for (RowView row : selectedPoem.getRowViews()) {
			if (row.contains(selectedRow)) {

				shiftRowTemp(selectedPoem, row, shiftAmount);
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
			shiftRowTemp(selectedPoem, theRow, -shiftAmount);
		}

		boolean isOverlappingOtherWord = false;
		for (AbstractWordView otherView : mainView.getProtectedAreaWords()) {
			if (!theRow.equals(otherView) && !selectedPoem.contains(otherView)
					&& theRow.isOverlapping(otherView)) {
				isOverlappingOtherWord = true;
			}
		}

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

	}

	public void shiftRowTemp(PoemView selectedPoem,
			AbstractWordView selectedRow, int shiftAmount) {
		if (selectedRow == null || selectedRow instanceof PoemView) {
			return;
		}
		selectedPoem.shiftRow(selectedRow, shiftAmount);
	}

}
