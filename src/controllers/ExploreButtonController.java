package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import views.ExploreArea;
import views.MainView;

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
