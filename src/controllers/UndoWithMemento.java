package controllers;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import common.Constants;
import models.AbstractWord;
import models.Area;
import models.GameState;
import models.Poem;
import models.Position;
import models.ProtectedMemento;
import models.Row;
import models.UnprotectedMemento;
import models.Word;
import views.AbstractWordView;
import views.MainView;
import views.PoemView;
import views.RowView;
import views.WordView;

public class UndoWithMemento implements Serializable {

	ProtectedMemento protectedMemento;
	UnprotectedMemento unprotectedMemento;
	
	/**
	 * Constructor
	 * @param mainView
	 * @param gameState
	 */
	public UndoWithMemento(MainView mainView, GameState gameState) {
		this.protectedMemento = new ProtectedMemento(mainView.getProtectedAreaWords());
		this.unprotectedMemento = new UnprotectedMemento(mainView.getUnprotectedAreaWords());
	}

	public void loadState(MainView mainView, GameState gameState) {
		mainView.clearPanel();
		gameState.getProtectedArea().getAbstractWordCollection().clear();
		gameState.getUnprotectedArea().getAbstractWordCollection().clear();

		Collection<AbstractWord> protectedWords = new HashSet<AbstractWord>();
		Collection<AbstractWord> unprotectedWords = new HashSet<AbstractWord>();
		for (AbstractWordView abs : unprotectedMemento.getUnprotectedView()) {
			WordView wordView = (WordView) abs;
			Word w = wordView.getWord();
			unprotectedWords.add(w);
		}
		for (AbstractWordView abs : protectedMemento.getProtectedView()) {
			if (abs instanceof WordView) {
				WordView wordView = (WordView) abs;
				Word w = wordView.getWord();
				protectedWords.add(w);
			} else if (abs instanceof RowView) {
				Row r = (Row) abs.getWord();
				protectedWords.add(r);
			} else {
				// This is for poems
				Poem poem = (Poem) abs.getWord();
				protectedWords.add(poem);
			}
		}
		gameState.resetProtectedArea(protectedWords);
		gameState.resetUnprotectedArea(unprotectedWords);

		for (AbstractWordView abs : unprotectedMemento.getUnprotectedView()) {
			WordView wordView = (WordView) abs;
			mainView.addLabelOf(wordView);
			mainView.addUnprotectedAbstractWordView(wordView);
		}
		for (AbstractWordView abs : protectedMemento.getProtectedView()) {
			if (abs instanceof WordView) {
				WordView wordView = (WordView) abs;
				mainView.addLabelOf(wordView);
				mainView.addProtectedAbstractWordView(wordView);
			} else if (abs instanceof RowView) {
				List<WordView> list = ((RowView) abs).getWordViews();
				for (WordView w : list) {
					mainView.addLabelOf(w);
				}
				mainView.addProtectedAbstractWordView(abs);
			} else {
				// This is for PoemView
				PoemView poemView = (PoemView)abs;
				mainView.addProtectedAbstractWordView(poemView);
				for (RowView rowView : poemView.getRowViews()) {
					List<WordView> list = rowView.getWordViews();
					for (WordView w : list) {
						mainView.addLabelOf(w);
					}
				}
			}
		}
	}
}
