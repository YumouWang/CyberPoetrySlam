package controllers;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import javax.swing.JButton;

import models.Position;
import models.Word;
import models.WordType;
import views.AbstractWordView;
import views.MainView;
import views.PoemView;
import views.RowView;
import views.WordView;

/**
 * ButtonController handles all the actions for MainView panel buttons
 * 
 * Created by Yumou on 10/28/2014.
 */
public class ButtonController implements ActionListener {
	private MainView mainView;

	public ButtonController(MainView mainView) {
		this.mainView = mainView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		// click on Save button
		if (clickedButton.equals(mainView.btnSave)) {
			System.out.println("Save...");
		}
		// click on Redo button
		if (clickedButton.equals(mainView.btnRedo)) {
			// Handle redo
			System.out.println("Redo...");
		}
		// click on Undo button
		if (clickedButton.equals(mainView.btnUndo)) {
			// Handle Undo
			System.out.println("Undo...");
		}
	}
}
