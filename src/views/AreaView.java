package views;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import models.GameState;

import java.awt.Color;

public class AreaView extends JFrame {

	private JPanel contentPane;
	UnprotectedArea upa;
	ProtectedArea pa;

	/**
	 * Create the frame.
	 */
	public AreaView(GameState gameState) {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		upa = new UnprotectedArea(this, gameState);
		upa.Unprotect(gameState);
		upa.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		pa = new ProtectedArea(gameState);
		pa.setBorder(new LineBorder(new Color(0, 0, 0)));

		contentPane.setLayout(null);
		
		upa.setBounds(0, 141, 433, 121);
		contentPane.add(upa);
		upa.setLayout(null);
		
		pa.setBounds(0, 0, 433, 141);
		contentPane.add(pa);
		pa.setLayout(null);
		
	}
	public void addAbstractWordView(AbstractWordView newWord) {
        //words.add(newWord);
        contentPane.add(newWord.label);
        
    }
	
    public void removeAbstractWordView(AbstractWordView oldWord) {
        //contentPane.remove(oldWord.label);
    	System.out.println(oldWord.getWord().getValue() + "---");
    	contentPane.remove(upa.panel);
    	contentPane.updateUI();
    	upa.panel.remove(oldWord.label);
    	oldWord.label.setVisible(false);
    	//contentPane
    	pa.updateUI();
    	contentPane.add(upa);
    	contentPane.updateUI();
    }

}
