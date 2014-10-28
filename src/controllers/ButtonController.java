package controllers;

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

import views.AbstractWordView;
import views.MainView;
import views.PoemView;
import views.RowView;
import views.WordView;

public class ButtonController implements ActionListener {
	private MainView mainView;
	
	public ButtonController(MainView mainView) {
		this.mainView = mainView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton)e.getSource();
        if(clickedButton.equals(mainView.btnNewButton)){
        	Collection<AbstractWordView> protectedwordList = mainView
    				.getProtectedAreaWords();
    		Collection<AbstractWordView> unprotectedwordList = mainView
    				.getUnprotectedAreaWords();
    		Collection<AbstractWordView> wordList = new HashSet<AbstractWordView>();
    		for (AbstractWordView wordView : protectedwordList) {
    			wordList.add(wordView);
    			System.out.println(wordView.getWord().getValue() + ","
    					+ wordView.getPosition().getX() + ","
    					+ wordView.getPosition().getY());
    		}
    		for (AbstractWordView wordView : unprotectedwordList) {
    			wordList.add(wordView);
    			System.out.println(wordView.getWord().getValue() + ","
    					+ wordView.getPosition().getX() + ","
    					+ wordView.getPosition().getY() + "...");
    		}

    		try {
    			File csv = new File("log/log.csv");
    			BufferedWriter bw = new BufferedWriter(new FileWriter(csv, false));
    			for (AbstractWordView abstractWordView : wordList) {
    				if (abstractWordView instanceof WordView) {
    					WordView wordView = (WordView) abstractWordView;
    					bw.write(wordView.getWord().getValue() + ","
    							+ wordView.getWord().getType() + ","
    							+ wordView.getPosition().getX() + ","
    							+ wordView.getPosition().getY());
    					bw.newLine();
    				}
    				if (abstractWordView instanceof RowView) {
    					RowView rowView = (RowView) abstractWordView;
    					Collection<WordView> wordsInRow = rowView.getWordViews();
    					String rowType = "";
    					for (WordView word : wordsInRow) {
    						rowType += word.getWord().getType() + " ";
    					}
    					bw.write(rowView.getWord().getValue() + "," + rowType + ","
    							+ rowView.getPosition().getX() + ","
    							+ rowView.getPosition().getY());
    					bw.newLine();

    				}
    				if (abstractWordView instanceof PoemView) {
    					PoemView poemView = (PoemView) abstractWordView;
    					bw.write(poemView.getWord().getValue() + ","
    							+ poemView.getPosition().getX() + ","
    							+ poemView.getPosition().getY());
    					bw.newLine();
    				}
    			}
    			bw.close();
    		} catch (FileNotFoundException e1) {
    			e1.printStackTrace();
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
        }
        if(clickedButton.equals(mainView.btnUpdate)){
        	//Update View
        	System.out.println("back to saved status");
        }
	}
	
}
