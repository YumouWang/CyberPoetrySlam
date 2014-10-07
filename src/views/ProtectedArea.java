package views;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import controllers.MouseInputController;
import models.AbstractWord;
import models.GameState;
import models.Position;
import models.Word;
import models.WordType;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

public class ProtectedArea extends JPanel implements MouseListener, MouseMotionListener {

	/**
	 * Create the panel.
	 */
	Collection<AbstractWordView> words;
	public AbstractWordView selectedWord;
	Position mouseDownPosition;
	JPanel panel;
	
	public AbstractWordView clickedWord;
	public AbstractWordView currentWord;
	public Word[] wordList;
	public AbstractWordView[] wordViewList;
	
	public ProtectedArea(GameState gameState) {
		words = new HashSet<AbstractWordView>();
		
		Collection<AbstractWord> wordsList = new HashSet<AbstractWord>();
		
		wordsList = gameState.getProtectedArea().getAbstractWordCollection();
		
        wordList = new Word[wordsList.size()];
        wordViewList = new AbstractWordView[wordsList.size()];
        selectedWord = null;
        
        for(AbstractWord word: wordsList) {
        	
        	Random random = new Random();
        	int x = random.nextInt(300);
        	int y = random.nextInt(100);
        	AbstractWordView view = new AbstractWordView(word, new Position(x, y));
        	words.add(view);
        	add(view.label);
        }
        setLayout(null);
        
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel.setBounds(0, 0, 450, 300);
        add(panel);
        
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
    }
	
//	public void addWordLabel() {
//		System.out.println("adddd");
//		this.panel.add(new AbstractWordView(new Word("hehe",WordType.ANY),new Position(0,0)).label);
//		this.panel.updateUI();
//	}

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDownPosition = new Position(e.getX(), e.getY());
        selectedWord = null;
        for(AbstractWordView word: words) {
            if(word.isClicked(mouseDownPosition)) {
                selectedWord = word;
                break;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(selectedWord != null) {
            Position positionDiff = new Position(e.getX() - mouseDownPosition.getX(), e.getY() - mouseDownPosition.getY());
            Position originalPosition = new Position(selectedWord.getPosition().getX(),selectedWord.getPosition().getY());
            selectedWord.moveTo(new Position(selectedWord.getPosition().getX() + positionDiff.getX(), selectedWord.getPosition().getY() + positionDiff.getY()));
            boolean isOverlapping = false;
            boolean isAdjacent = false;
            if(selectedWord.getPosition().getX() < 0 || selectedWord.getPosition().getX() > 370 || selectedWord.getPosition().getY() < 0 || selectedWord.getPosition().getY() > 110) {
            	selectedWord.moveTo(originalPosition);
            }
            for (AbstractWordView word : words) {
                if(!word.equals(selectedWord)) {
                    if (word.isOverlapping(selectedWord)) {
                        isOverlapping = true;
                    }
                    AdjacencyType adjacencyType = selectedWord.isAdjacentTo(word);
                    if(adjacencyType != AdjacencyType.NOT_ADJACENT) {
                        isAdjacent = true;
                        word.label.setBackground(Color.GREEN);
                    } else {
                    	if(!word.label.getBackground().equals(Color.GREEN)) {
                    		word.label.setBackground(Color.LIGHT_GRAY);
                    	}                    		
                    }
                }
            }
            if(isOverlapping) {
                selectedWord.label.setBackground(Color.RED);
                //selectedWord.moveTo(originalPosition);
            } else if (isAdjacent) {
                selectedWord.label.setBackground(Color.GREEN);
            } else {
            	selectedWord.label.setBackground(Color.LIGHT_GRAY);
            }
        }
        mouseDownPosition = new Position(e.getX(), e.getY());
    }

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}


