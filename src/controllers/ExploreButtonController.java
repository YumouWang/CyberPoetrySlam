package controllers;

import views.ExploreArea;
import views.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExploreButtonController implements ActionListener {
	private MainView mainView;
	private ExploreArea exploreArea;

	public ExploreButtonController(MainView mainView, ExploreArea exploreArea) {
		this.mainView = mainView;
		this.exploreArea = exploreArea;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		// click on Search button
		
	}
}
