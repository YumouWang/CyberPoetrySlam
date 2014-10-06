package views;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import models.GameState;
import models.Position;

import java.awt.Color;

public class AreaView extends JFrame {

	JSplitPane jsp;  
	JPanel p1,p2,p3;   
	JButton bt; 
	UnprotectedArea upa;
	ProtectedArea pa;

	public AreaView(GameState gameState)  { 
		
		upa = new UnprotectedArea(this, gameState);
		upa.Unprotect(gameState);
		upa.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		pa = new ProtectedArea(gameState);
		pa.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		p1=new JPanel();   
		p2=new JPanel();   
		p1 = pa;
		p2 = upa;
		       
		jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,p1,p2);         
		jsp.setDividerLocation(150);   
		add(jsp,BorderLayout.CENTER);     
		setDefaultCloseOperation(EXIT_ON_CLOSE);  
		setSize(400,300);   
		setResizable(false);   
		//setVisible(true);    
		setTitle("JSplitPane Demo");      
	}
	
	public void removeAbstractWordView(AbstractWordView oldWord) {
        //contentPane.remove(oldWord.label);
    	System.out.println(oldWord.getWord().getValue() + "---");
    	upa.remove(oldWord.label);
    	addAbstractWordView(oldWord);
    	//oldWord.label.setVisible(false);
    	//contentPane
    	jsp.updateUI();
        	
	}
  
	public void addAbstractWordView(AbstractWordView newWord) {
        int x = 0;
    	int y = 0;
    	AbstractWordView view = new AbstractWordView(newWord.getWord(), new Position(x, y));
    	//words.add(view);
    	pa.panel.add(view.label);
    	p1.updateUI();
        
    }

}
