package views;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JPanel;

import controllers.MouseInputController;
import models.Position;
import models.Word;
import models.WordType;
import javax.swing.border.LineBorder;

public class UnprotectedArea extends JPanel implements MouseListener, MouseMotionListener {

	/**
	 * Create the panel.
	 */
	Collection<AbstractWordView> words;
	AbstractWordView selectedWord;
	Position mouseDownPosition;
	
	public UnprotectedArea() {
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
        	add(wordViewList[i].label);
        	i ++;
        }
        
        setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel.setBounds(0, 0, 450, 300);
        add(panel);
        
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
                Position originalPosition = new Position(selectedWord.getPosition().getX(), selectedWord.getPosition().getY());
                selectedWord.moveTo(new Position(selectedWord.getPosition().getX() + positionDiff.getX(), selectedWord.getPosition().getY() + positionDiff.getY()));
                if(selectedWord.getPosition().getX() < 0 || selectedWord.getPosition().getX() > 370 || selectedWord.getPosition().getY() < 0 || selectedWord.getPosition().getY() > 110) {
                	selectedWord.moveTo(originalPosition);
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
