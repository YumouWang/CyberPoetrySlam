package views;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import controllers.MouseInputController;
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
	AbstractWordView selectedWord;
	Position mouseDownPosition;
	JPanel panel = new JPanel();
	
	public ProtectedArea() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(null);

		panel.setBounds(0, 0, 450, 300);
		add(panel);
		panel.setLayout(null);
		
		words = new HashSet<AbstractWordView>();

        Hashtable<String,WordType> wordsList = new Hashtable<String,WordType>();
        wordsList.put("word1", WordType.ADJECTIVE);
        wordsList.put("word2", WordType.ADVERB);
        wordsList.put("word3", WordType.NOUN);
        wordsList.put("word4", WordType.VERB);

        Word[] wordList = new Word[wordsList.size()];
        AbstractWordView[] wordViewList = new AbstractWordView[wordsList.size()];

        int i = 0;
        for(Iterator it = wordsList.keySet().iterator(); it.hasNext();) {
        	String key = it.next().toString();
        	WordType value = wordsList.get(key);
        	wordList[i] = new Word(key,value);
        	Random random = new Random();
        	int x = random.nextInt(300);
        	int y = random.nextInt(100);
        	wordViewList[i] = new AbstractWordView(wordList[i], new Position(x, y));
        	words.add(wordViewList[i]);
        	panel.add(wordViewList[i].label);
        	i ++;
        }
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
    }

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
                        word.label.setBackground(Color.LIGHT_GRAY);
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


