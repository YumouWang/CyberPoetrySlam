package controllers;

import java.awt.Color;
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

public class UndoWithMemento extends UndoMove{

	final MainView mainView;
	final GameState gameState;
	ProtectedMemento protectedMemento;
	UnprotectedMemento unprotectedMemento;
	Collection<AbstractWord> protectedWords;
	Collection<AbstractWord> unprotectedWords;
	
	/**
	 * Constructor
	 * @param mainView
	 * @param gameState
	 */
	public UndoWithMemento(MainView mainView, GameState gameState) {
		this.mainView = mainView;
		this.gameState = gameState;
		this.protectedMemento = new ProtectedMemento(this.mainView.getProtectedAreaWords());
		this.unprotectedMemento = new UnprotectedMemento(this.mainView.getUnprotectedAreaWords());
		//this.protectedWordViews = protectedMemento.getProtectedView();
		//this.unprotectedWordViews = unprotectedMemento.getUnprotectedView();
	}
	
	@Override
	public boolean execute() {
		
		mainView.clearPanel();
		gameState.getProtectedArea().getAbstractWordCollection().clear();
		gameState.getUnprotectedArea().getAbstractWordCollection().clear();
		protectedWords = new HashSet<AbstractWord>();
		unprotectedWords = new HashSet<AbstractWord>();
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
		this.gameState.resetProtectedArea(protectedWords);
		this.gameState.resetUnprotectedArea(unprotectedWords);
		
		for (AbstractWordView abs : unprotectedMemento.getUnprotectedView()) {
			Position position = abs.getPosition();
			WordView wordView = (WordView) abs;
			Word w = wordView.getWord();
			WordView view = new WordView(w, position);
			this.mainView.addLabelOf(view);
			this.mainView.addUnprotectedAbstractWordView(view);
		}
		for (AbstractWordView abs : protectedMemento.getProtectedView()) {
			if (abs instanceof WordView) {
				Position position = abs.getPosition();
				WordView wordView = (WordView) abs;
				Word w = wordView.getWord();
				WordView view = new WordView(w, position);
				this.mainView.addLabelOf(view);
				this.mainView.addProtectedAbstractWordView(view);
			} else if (abs instanceof RowView) {
				Row r = (Row) abs.getWord();
				List<WordView> list = ((RowView) abs).getWordViews();
				for (WordView w : list) {
					this.mainView.addLabelOf(w);
					this.mainView.addProtectedAbstractWordView(w);
				}
				RowView rowView = new RowView(r, abs.getPosition(), this.mainView);
				this.mainView.addProtectedAbstractWordView(rowView);
				for (WordView w : list) {
					this.mainView.removeProtectedAbstractWordView(w);
				}
			} else {
				// This is for PoemView
				PoemView poemView = (PoemView)abs;
				mainView.addProtectedAbstractWordView(poemView);
				for (RowView rowView : poemView.getRowViews()) {
					List<WordView> list = rowView.getWordViews();
					for (WordView w : list) {
						this.mainView.addLabelOf(w);
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean undo() {
		mainView.clearPanel();
		gameState.getProtectedArea().getAbstractWordCollection().clear();
		gameState.getUnprotectedArea().getAbstractWordCollection().clear();
		protectedWords = new HashSet<AbstractWord>();
		unprotectedWords = new HashSet<AbstractWord>();
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
		this.gameState.resetProtectedArea(protectedWords);
		this.gameState.resetUnprotectedArea(unprotectedWords);
		
		for (AbstractWordView abs : unprotectedMemento.getUnprotectedView()) {
			Position position = abs.getPosition();
			WordView wordView = (WordView) abs;
			Word w = wordView.getWord();
			WordView view = new WordView(w, position);
			this.mainView.addLabelOf(view);
			this.mainView.addUnprotectedAbstractWordView(view);
		}
		for (AbstractWordView abs : protectedMemento.getProtectedView()) {
			if (abs instanceof WordView) {
				Position position = abs.getPosition();
				WordView wordView = (WordView) abs;
				Word w = wordView.getWord();
				WordView view = new WordView(w, position);
				this.mainView.addLabelOf(view);
				this.mainView.addProtectedAbstractWordView(view);
			} else if (abs instanceof RowView) {
				Row r = (Row) abs.getWord();
				List<WordView> list = ((RowView) abs).getWordViews();
				for (WordView w : list) {
					this.mainView.addLabelOf(w);
					this.mainView.addProtectedAbstractWordView(w);
				}
				RowView rowView = new RowView(r, abs.getPosition(), this.mainView);
				this.mainView.addProtectedAbstractWordView(rowView);
				for (WordView w : list) {
					this.mainView.removeProtectedAbstractWordView(w);
				}
			} else {
				// This is for PoemView
				PoemView poemView = (PoemView)abs;
				mainView.addProtectedAbstractWordView(poemView);
				for (RowView rowView : poemView.getRowViews()) {
					List<WordView> list = rowView.getWordViews();
					for (WordView w : list) {
						this.mainView.addLabelOf(w);
					}
				}
			}

		}		
		return true;
	}

}
