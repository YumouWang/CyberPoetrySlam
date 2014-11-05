package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import views.MainView;

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
		if (clickedButton.equals(mainView.btnSwap)) {
			// Handle Swap
			System.out.println("Swap...");
		}
	}
}
