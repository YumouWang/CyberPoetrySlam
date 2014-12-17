package controllers;

import models.*;
import views.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class UndoWithMemento implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2043773621354133363L;
    ProtectedMemento protectedMemento;
    UnprotectedMemento unprotectedMemento;

    /**
     * Constructor
     *
     * @param mainView The mainView to create the memento from
     */
    public UndoWithMemento(MainView mainView) {
        this.protectedMemento = new ProtectedMemento(mainView.getProtectedAreaWords());
        this.unprotectedMemento = new UnprotectedMemento(mainView.getUnprotectedAreaWords());
    }

    public void loadState(MainView mainView, GameState gameState) {
        mainView.clearPanel();
        gameState.getProtectedArea().getAbstractWordCollection().clear();
        gameState.getUnprotectedArea().getAbstractWordCollection().clear();

        Collection<AbstractWord> protectedWords = new HashSet<AbstractWord>();
        List<AbstractWord> unprotectedWords = new ArrayList<AbstractWord>();
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
                Row row = (Row) abs.getWord();
                protectedWords.add(row);
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
                PoemView poemView = (PoemView) abs;
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